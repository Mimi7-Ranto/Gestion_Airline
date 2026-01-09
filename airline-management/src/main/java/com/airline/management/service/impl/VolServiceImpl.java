package com.airline.management.service.impl;

import com.airline.management.dto.request.VolRequest;
import com.airline.management.dto.response.VolResponse;
import com.airline.management.entity.Aeroport;
import com.airline.management.entity.Avion;
import com.airline.management.entity.Company;
import com.airline.management.entity.EtatVol;
import com.airline.management.entity.Vol;
import com.airline.management.mapper.VolMapper;
import com.airline.management.repository.AeroportRepository;
import com.airline.management.repository.AvionRepository;
import com.airline.management.repository.CompanyRepository;
import com.airline.management.repository.EtatVolRepository;
import com.airline.management.repository.VolRepository;
import com.airline.management.service.VolService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VolServiceImpl implements VolService {
    private final VolRepository volRepo;
    private final AeroportRepository aeroportRepo;
    private final AvionRepository avionRepo;
    private final CompanyRepository companyRepo;
    private final EtatVolRepository etatVolRepo;
    private final VolMapper mapper;

    public VolServiceImpl(VolRepository volRepo, AeroportRepository aeroportRepo, AvionRepository avionRepo, CompanyRepository companyRepo, EtatVolRepository etatVolRepo, VolMapper mapper) {
        this.volRepo = volRepo;
        this.aeroportRepo = aeroportRepo;
        this.avionRepo = avionRepo;
        this.companyRepo = companyRepo;
        this.etatVolRepo = etatVolRepo;
        this.mapper = mapper;
    }

    @Override
    public VolResponse create(VolRequest req) {
        Vol v = mapper.toEntity(req);
        if (req.getIdAeroportDepart() != null) v.setAeroportDepart(aeroportRepo.getReferenceById(req.getIdAeroportDepart()));
        if (req.getIdAeroportDestination() != null) v.setAeroportDestination(aeroportRepo.getReferenceById(req.getIdAeroportDestination()));
        if (req.getIdAvion() != null) v.setAvion(avionRepo.getReferenceById(req.getIdAvion()));
        if (req.getIdCompany() != null) v.setCompany(companyRepo.getReferenceById(req.getIdCompany()));
        if (req.getEtatVol() != null) v.setEtatVol(etatVolRepo.getReferenceById(req.getEtatVol()));
        v = volRepo.save(v);
        return mapper.toResponse(v);
    }

    @Override
    public VolResponse update(String id, VolRequest req) {
        Vol v = volRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Vol not found"));
        v.setDateDepart(req.getDateDepart());
        v.setDateArrivee(req.getDateArrivee());
        if (req.getIdAeroportDepart() != null) v.setAeroportDepart(aeroportRepo.getReferenceById(req.getIdAeroportDepart()));
        if (req.getIdAeroportDestination() != null) v.setAeroportDestination(aeroportRepo.getReferenceById(req.getIdAeroportDestination()));
        if (req.getIdAvion() != null) v.setAvion(avionRepo.getReferenceById(req.getIdAvion()));
        if (req.getIdCompany() != null) v.setCompany(companyRepo.getReferenceById(req.getIdCompany()));
        if (req.getEtatVol() != null) v.setEtatVol(etatVolRepo.getReferenceById(req.getEtatVol()));
        v = volRepo.save(v);
        return mapper.toResponse(v);
    }

    @Override
    public void delete(String id) {
        volRepo.deleteById(id);
    }

    @Override
    public VolResponse getById(String id) {
        return volRepo.findById(id).map(mapper::toResponse).orElse(null);
    }

    @Override
    public List<VolResponse> listAll() {
        return volRepo.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<VolResponse> searchByDepartAndDateRange(String idAeroportDepart, LocalDateTime from, LocalDateTime to) {
        return volRepo.findByAeroportDepartIdAeroportAndDateDepartBetween(idAeroportDepart, from, to)
                .stream().map(mapper::toResponse).collect(Collectors.toList());
    }
}
