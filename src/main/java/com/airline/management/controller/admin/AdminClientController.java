package com.airline.management.controller.admin;

import com.airline.management.entity.Client;
import com.airline.management.repository.ClientRepository;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/clients")
@PreAuthorize("hasRole('ADMIN')")
public class AdminClientController {

    private final ClientRepository clientRepository;

    public AdminClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public ResponseEntity<Client> findByEmail(@RequestParam("email") String email) {
        Optional<Client> c = clientRepository.findByEmail(email);
        return c.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody ClientPayload payload) {
        Client c = Client.builder()
            .nomClient(payload.nom)
            .prenomClient(payload.prenom)
            .email(payload.email)
            .telephone(payload.telephone)
            .dateCreation(java.time.LocalDateTime.now())
            .build();

        if (payload.dateNaissance != null && !payload.dateNaissance.isEmpty()) {
            try {
                c.setDateNaissance(LocalDate.parse(payload.dateNaissance));
            } catch (DateTimeParseException e) {
                // ignore parse error
            }
        }

        Client saved = clientRepository.save(c);
        return ResponseEntity.created(URI.create("/api/admin/clients?email=" + saved.getEmail())).body(saved);
    }

    public static class ClientPayload {
        public String prenom;
        public String nom;
        public String email;
        public String telephone;
        public String dateNaissance;
    }
}
