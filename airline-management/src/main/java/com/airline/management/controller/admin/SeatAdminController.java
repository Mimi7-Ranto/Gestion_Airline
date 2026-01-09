package com.airline.management.controller.admin;

import com.airline.management.dto.request.SeatGenerateRequest;
import com.airline.management.dto.response.SeatResponse;
import com.airline.management.entity.Siege;
import com.airline.management.service.SeatService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class SeatAdminController {

    private final SeatService seatService;

    public SeatAdminController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping("/aircraft/{id}/seats/generate")
    public ResponseEntity<List<SeatResponse>> generateSeats(@PathVariable("id") String avionId,
                                                            @Valid @RequestBody SeatGenerateRequest req) {
        List<SeatResponse> created = seatService.generateSeats(avionId, req);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/aircraft/{id}/seats")
    public ResponseEntity<List<SeatResponse>> listSeats(@PathVariable("id") String avionId) {
        return ResponseEntity.ok(seatService.listSeatsByAvion(avionId));
    }

    @PutMapping("/seats/{id}")
    public ResponseEntity<SeatResponse> updateSeat(@PathVariable("id") String seatId, @RequestBody Siege seat) {
        return ResponseEntity.ok(seatService.updateSeat(seatId, seat));
    }
}
