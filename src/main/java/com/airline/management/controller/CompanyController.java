package com.airline.management.controller;

import com.airline.management.dto.response.CompanySimpleResponse;
import com.airline.management.repository.CompanyRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyRepository repo;

    public CompanyController(CompanyRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<CompanySimpleResponse> list() {
        return repo.findAll().stream()
                .map(c -> new CompanySimpleResponse(c.getIdCompany(), c.getNomCompany()))
                .collect(Collectors.toList());
    }
}
