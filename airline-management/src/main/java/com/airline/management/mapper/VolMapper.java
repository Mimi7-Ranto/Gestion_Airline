package com.airline.management.mapper;

import com.airline.management.dto.request.VolRequest;
import com.airline.management.dto.response.VolResponse;
import com.airline.management.entity.Avion;
import com.airline.management.entity.Company;
import com.airline.management.entity.EtatVol;
import com.airline.management.entity.Vol;
import org.springframework.stereotype.Component;

@Component
public class VolMapper {
    public Vol toEntity(VolRequest req) {
        if (req == null) return null;
        Vol v = Vol.builder()
                .dateDepart(req.getDateDepart())
                .dateArrivee(req.getDateArrivee())
                .arriveeEffective(null)
                .build();
        if (req.getIdAvion() != null) v.setAvion(Avion.builder().idAvion(req.getIdAvion()).build());
        if (req.getIdCompany() != null) v.setCompany(Company.builder().idCompany(req.getIdCompany()).build());
        if (req.getEtatVol() != null) v.setEtatVol(EtatVol.builder().etatVol(req.getEtatVol()).build());
        return v;
    }

    public VolResponse toResponse(Vol v) {
        if (v == null) return null;
        return VolResponse.builder()
                .idVol(v.getIdVol())
                .idAeroportDepart(v.getAeroportDepart() != null ? v.getAeroportDepart().getIdAeroport() : null)
                .idAeroportDestination(v.getAeroportDestination() != null ? v.getAeroportDestination().getIdAeroport() : null)
                .idAvion(v.getAvion() != null ? v.getAvion().getIdAvion() : null)
                .dateDepart(v.getDateDepart())
                .dateArrivee(v.getDateArrivee())
                .idCompany(v.getCompany() != null ? v.getCompany().getIdCompany() : null)
                .etatVol(v.getEtatVol() != null ? v.getEtatVol().getEtatVol() : null)
                .arriveeEffective(v.getArriveeEffective())
                .dateCreation(v.getDateCreation())
                .dateModification(v.getDateModification())
                .build();
    }
}
