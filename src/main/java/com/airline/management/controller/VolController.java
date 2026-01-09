package com.airline.management.controller;

import com.airline.management.dto.request.VolRequest;
import com.airline.management.dto.response.VolResponse;
import com.airline.management.service.VolService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vols")
public class VolController {
    private final VolService service;

    public VolController(VolService service) {
        this.service = service;
    }

    @GetMapping
    public List<VolResponse> list() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolResponse> get(@PathVariable String id) {
        VolResponse r = service.getById(id);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity<VolResponse> create(@Valid @RequestBody VolRequest req) {
        VolResponse r = service.create(req);
        return new ResponseEntity<>(r, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VolResponse> update(@PathVariable String id, @Valid @RequestBody VolRequest req) {
        VolResponse r = service.update(id, req);
        return ResponseEntity.ok(r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<VolResponse> search(
            @RequestParam String departId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return service.searchByDepartAndDateRange(departId, from, to);
    }
}
