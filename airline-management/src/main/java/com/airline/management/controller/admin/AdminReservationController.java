package com.airline.management.controller.admin;

import com.airline.management.dto.request.AdminReservationCreateRequest;
import com.airline.management.dto.response.ReservationResponse;
import com.airline.management.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/reservations")
@PreAuthorize("hasRole('ADMIN')")
public class AdminReservationController {

    private final ReservationService reservationService;

    public AdminReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createForClient(@Valid @RequestBody AdminReservationCreateRequest req) {
        ReservationResponse res = reservationService.createReservationForClient(req);
        return ResponseEntity.ok(res);
    }
}
