package com.airline.management.controller;

import com.airline.management.dto.request.AeroportRequest;
import com.airline.management.dto.response.AeroportResponse;
import com.airline.management.service.AeroportService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aeroports")
public class AeroportController {
    private final AeroportService service;

    public AeroportController(AeroportService service) {
        this.service = service;
    }

    @GetMapping
    public List<AeroportResponse> list() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AeroportResponse> get(@PathVariable String id) {
        AeroportResponse r = service.getById(id);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity<AeroportResponse> create(@Valid @RequestBody AeroportRequest req) {
        AeroportResponse r = service.create(req);
        return new ResponseEntity<>(r, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AeroportResponse> update(@PathVariable String id, @Valid @RequestBody AeroportRequest req) {
        AeroportResponse r = service.update(id, req);
        return ResponseEntity.ok(r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
