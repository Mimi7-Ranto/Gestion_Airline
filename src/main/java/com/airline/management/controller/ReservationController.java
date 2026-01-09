package com.airline.management.controller;

import com.airline.management.dto.request.ReservationCreateRequest;
import com.airline.management.dto.response.ReservationResponse;
import com.airline.management.service.ReservationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> create(@Valid @RequestBody ReservationCreateRequest req, Authentication auth) {
        ReservationResponse res = reservationService.createReservation(req, auth);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/users/me/reservations")
    public ResponseEntity<List<ReservationResponse>> myReservations(Authentication auth) {
        return ResponseEntity.ok(reservationService.listReservationsForUser(auth));
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<ReservationResponse> get(@PathVariable String id, Authentication auth) {
        return ResponseEntity.ok(reservationService.getReservation(id, auth));
    }

    @PatchMapping("/reservations/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancel(@PathVariable String id, Authentication auth) {
        return ResponseEntity.ok(reservationService.cancelReservation(id, auth));
    }
}
