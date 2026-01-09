package com.airline.management.config;

import com.airline.management.entity.Utilisateur;
import com.airline.management.repository.UtilisateurRepository;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@airline.com";
        if (utilisateurRepository.findByEmail(adminEmail).isEmpty()) {
            Utilisateur admin = Utilisateur.builder()
                .email(adminEmail)
                .motDePasse(passwordEncoder.encode("Admin123!"))
                .role("ADMIN")
                .statut("ACTIF")
                .dateCreation(LocalDateTime.now())
                .build();

            utilisateurRepository.save(admin);
            System.out.println("[DataInitializer] Admin créé: " + adminEmail + " / mot de passe: Admin123!");
        }
    }
}
