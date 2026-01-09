package com.airline.management.service;

import com.airline.management.dto.request.PassengerRequest;
import com.airline.management.dto.request.ReservationCreateRequest;
import com.airline.management.dto.request.AdminReservationCreateRequest;
import com.airline.management.dto.response.ReservationResponse;
import com.airline.management.entity.Client;
import com.airline.management.entity.Passager;
import com.airline.management.entity.Reservation;
import com.airline.management.entity.Utilisateur;
import com.airline.management.entity.Vol;
import com.airline.management.repository.ClientRepository;
import com.airline.management.repository.PassagerRepository;
import com.airline.management.repository.ReservationRepository;
import com.airline.management.repository.UtilisateurRepository;
import com.airline.management.repository.VolRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final VolRepository volRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ClientRepository clientRepository;
    private final ReservationRepository reservationRepository;
    private final PassagerRepository passagerRepository;
    private final EntityManager entityManager;

    public ReservationService(VolRepository volRepository, UtilisateurRepository utilisateurRepository,
                              ClientRepository clientRepository, ReservationRepository reservationRepository,
                              PassagerRepository passagerRepository, EntityManager entityManager) {
        this.volRepository = volRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
        this.passagerRepository = passagerRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public ReservationResponse createReservation(ReservationCreateRequest req, Authentication auth) {
        String email = auth.getName();
        Utilisateur u = utilisateurRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
        Client client = clientRepository.findByUtilisateurIdUtilisateur(u.getIdUtilisateur()).orElseThrow(() -> new IllegalArgumentException("Client non rattaché à l'utilisateur"));

        Vol vol = volRepository.findById(req.getVolId()).orElseThrow(() -> new IllegalArgumentException("Vol introuvable"));

        // Basic price calculation: prix_base * nombre_passagers
        BigDecimal total = vol.getPrixBase().multiply(new BigDecimal(req.getNombrePassagers()));

        Reservation res = Reservation.builder()
            .client(client)
            .vol(vol)
            .dateReservation(LocalDateTime.now())
            .nombrePassagers(req.getNombrePassagers())
            .montantTotal(total)
            .statut("CONFIRMEE")
            .dateCreation(LocalDateTime.now())
            .build();

        // Save and flush to ensure the ID is available before creating passengers
        Reservation saved = reservationRepository.saveAndFlush(res);

        // Create passagers
        if (req.getPassagers() != null) {
            for (PassengerRequest p : req.getPassagers()) {
                Passager passager = Passager.builder()
                    .reservation(saved)
                    .nom(p.getNom())
                    .prenom(p.getPrenom())
                    .dateNaissance(p.getDateNaissance())
                    .numeroPasseport(p.getNumeroPasseport())
                    .nationalite(p.getNationalite())
                    .email(p.getEmail())
                    .telephone(p.getTelephone())
                    .dateCreation(LocalDateTime.now())
                    .build();

                passagerRepository.save(passager);
            }
        }

        return toResponse(saved);
    }

    @Transactional
    public ReservationResponse createReservationForClient(AdminReservationCreateRequest req) {
        Client client = clientRepository.findByEmail(req.getClientEmail()).orElseThrow(() -> new IllegalArgumentException("Client introuvable"));

        Vol vol = volRepository.findById(req.getVolId()).orElseThrow(() -> new IllegalArgumentException("Vol introuvable"));

        BigDecimal total = vol.getPrixBase().multiply(new BigDecimal(req.getNombrePassagers()));

        Reservation res = Reservation.builder()
            .client(client)
            .vol(vol)
            .dateReservation(LocalDateTime.now())
            .nombrePassagers(req.getNombrePassagers())
            .montantTotal(total)
            .statut("CONFIRMEE")
            .dateCreation(LocalDateTime.now())
            .build();

        // Save and flush to ensure the ID is generated by the database trigger
        Reservation saved = reservationRepository.saveAndFlush(res);

        if (req.getPassagers() != null) {
            for (PassengerRequest p : req.getPassagers()) {
                Passager passager = Passager.builder()
                    .reservation(saved)
                    .nom(p.getNom())
                    .prenom(p.getPrenom())
                    .dateNaissance(p.getDateNaissance())
                    .numeroPasseport(p.getNumeroPasseport())
                    .nationalite(p.getNationalite())
                    .email(p.getEmail())
                    .telephone(p.getTelephone())
                    .dateCreation(LocalDateTime.now())
                    .build();

                passagerRepository.save(passager);
            }
        }

        return toResponse(saved);
    }

    public List<ReservationResponse> listReservationsForUser(Authentication auth) {
        String email = auth.getName();
        Utilisateur u = utilisateurRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
        Client client = clientRepository.findByUtilisateurIdUtilisateur(u.getIdUtilisateur()).orElseThrow(() -> new IllegalArgumentException("Client non rattaché à l'utilisateur"));

        return reservationRepository.findByClientIdClient(client.getIdClient()).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ReservationResponse getReservation(String id, Authentication auth) {
        Reservation r = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Réservation introuvable"));
        // Optionally check ownership
        return toResponse(r);
    }

    @Transactional
    public ReservationResponse cancelReservation(String id, Authentication auth) {
        Reservation r = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Réservation introuvable"));
        Vol vol = r.getVol();
        LocalDateTime now = LocalDateTime.now();
        if (vol.getDateDepart().minus(24, ChronoUnit.HOURS).isBefore(now)) {
            throw new IllegalStateException("Annulation impossible moins de 24 heures avant le départ");
        }

        r.setStatut("ANNULEE");
        r.setDateModification(LocalDateTime.now());
        reservationRepository.save(r);
        return toResponse(r);
    }

    private ReservationResponse toResponse(Reservation r) {
        return ReservationResponse.builder()
            .id(r.getIdReservation())
            .volId(r.getVol() == null ? null : r.getVol().getIdVol())
            .nombrePassagers(r.getNombrePassagers())
            .montantTotal(r.getMontantTotal())
            .statut(r.getStatut())
            .dateReservation(r.getDateReservation())
            .build();
    }
}
