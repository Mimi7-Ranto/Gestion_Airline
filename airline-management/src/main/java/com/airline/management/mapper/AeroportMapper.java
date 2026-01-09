package com.airline.management.mapper;

import com.airline.management.dto.request.AeroportRequest;
import com.airline.management.dto.response.AeroportResponse;
import com.airline.management.entity.Aeroport;
import org.springframework.stereotype.Component;

@Component
public class AeroportMapper {
    public Aeroport toEntity(AeroportRequest req) {
        if (req == null) return null;
        return Aeroport.builder()
                .codeIata(req.getCodeIata())
                .codeIcao(req.getCodeIcao())
                .nomAeroport(req.getNomAeroport())
                .ville(req.getVille())
                .pays(req.getPays())
                .fuseauHoraire(req.getFuseauHoraire())
                .build();
    }

    public AeroportResponse toResponse(Aeroport e) {
        if (e == null) return null;
        return AeroportResponse.builder()
                .idAeroport(e.getIdAeroport())
                .codeIata(e.getCodeIata())
                .codeIcao(e.getCodeIcao())
                .nomAeroport(e.getNomAeroport())
                .ville(e.getVille())
                .pays(e.getPays())
                .fuseauHoraire(e.getFuseauHoraire())
                .dateCreation(e.getDateCreation())
                .dateModification(e.getDateModification())
                .build();
    }
}
