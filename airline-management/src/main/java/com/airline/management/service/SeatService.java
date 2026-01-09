package com.airline.management.service;

import com.airline.management.dto.request.SeatGenerateRequest;
import com.airline.management.dto.response.SeatResponse;
import com.airline.management.entity.Avion;
import com.airline.management.entity.Siege;
import com.airline.management.repository.AvionRepository;
import com.airline.management.repository.SiegeRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeatService {

    private final AvionRepository avionRepository;
    private final SiegeRepository siegeRepository;

    public SeatService(AvionRepository avionRepository, SiegeRepository siegeRepository) {
        this.avionRepository = avionRepository;
        this.siegeRepository = siegeRepository;
    }

    @Transactional
    public List<SeatResponse> generateSeats(String avionId, SeatGenerateRequest req) {
        Avion avion = avionRepository.findById(avionId).orElseThrow(() -> new IllegalArgumentException("Avion introuvable"));

        int rows = req.getRows();
        int seatsPerRow = req.getSeatsPerRow();
        String seatClass = req.getSeatClass() == null ? "Economie" : req.getSeatClass();

        List<Siege> created = new ArrayList<>();

        for (int r = 1; r <= rows; r++) {
            for (int s = 0; s < seatsPerRow; s++) {
                char letter = (char) ('A' + s);
                String numero = r + String.valueOf(letter);

                Siege siege = Siege.builder()
                    .avion(avion)
                    .numeroSiege(numero)
                    .rangee(r)
                    .lettre(String.valueOf(letter))
                    .classe(seatClass)
                    .estFenetre(s == 0 || s == seatsPerRow - 1)
                    .estCouloir(s == 0 || s == seatsPerRow - 1)
                    .estSortieSecours(false)
                    .dateCreation(LocalDateTime.now())
                    .build();

                created.add(siegeRepository.save(siege));
            }
        }

        return created.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<SeatResponse> listSeatsByAvion(String avionId) {
        List<Siege> seats = siegeRepository.findByAvionIdAvion(avionId);
        return seats.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public SeatResponse updateSeat(String seatId, Siege updated) {
        Siege existing = siegeRepository.findById(seatId).orElseThrow(() -> new IllegalArgumentException("Siège introuvable"));
        existing.setClasse(updated.getClasse());
        existing.setEstFenetre(updated.getEstFenetre());
        existing.setEstCouloir(updated.getEstCouloir());
        existing.setEstSortieSecours(updated.getEstSortieSecours());
        Siege saved = siegeRepository.save(existing);
        return toResponse(saved);
    }

    private SeatResponse toResponse(Siege s) {
        return SeatResponse.builder()
            .id(s.getIdSiege())
            .numeroSiege(s.getNumeroSiege())
            .rangee(s.getRangee())
            .lettre(s.getLettre())
            .classe(s.getClasse())
            .estFenetre(s.getEstFenetre())
            .estCouloir(s.getEstCouloir())
            .estSortieSecours(s.getEstSortieSecours())
            .build();
    }
}
