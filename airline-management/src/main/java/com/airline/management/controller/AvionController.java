package com.airline.management.controller;

import com.airline.management.dto.response.AvionSimpleResponse;
import com.airline.management.repository.AvionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/avions")
public class AvionController {
    private final AvionRepository repo;

    public AvionController(AvionRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<AvionSimpleResponse> list() {
        return repo.findAll().stream()
                .map(a -> new AvionSimpleResponse(a.getIdAvion(), a.getModele()))
                .collect(Collectors.toList());
    }
}
