package com.airline.management.service.impl;

import com.airline.management.dto.request.AeroportRequest;
import com.airline.management.dto.response.AeroportResponse;
import com.airline.management.entity.Aeroport;
import com.airline.management.mapper.AeroportMapper;
import com.airline.management.repository.AeroportRepository;
import com.airline.management.service.AeroportService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AeroportServiceImpl implements AeroportService {
    private final AeroportRepository repo;
    private final AeroportMapper mapper;

    public AeroportServiceImpl(AeroportRepository repo, AeroportMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public AeroportResponse create(AeroportRequest req) {
        Aeroport e = mapper.toEntity(req);
        e = repo.save(e);
        return mapper.toResponse(e);
    }

    @Override
    public AeroportResponse update(String id, AeroportRequest req) {
        Aeroport e = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Aeroport not found"));
        e.setCodeIata(req.getCodeIata());
        e.setCodeIcao(req.getCodeIcao());
        e.setNomAeroport(req.getNomAeroport());
        e.setVille(req.getVille());
        e.setPays(req.getPays());
        e.setFuseauHoraire(req.getFuseauHoraire());
        e = repo.save(e);
        return mapper.toResponse(e);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }

    @Override
    public AeroportResponse getById(String id) {
        return repo.findById(id).map(mapper::toResponse).orElse(null);
    }

    @Override
    public List<AeroportResponse> listAll() {
        return repo.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }
}
