package com.airline.management.controller.web;

import com.airline.management.entity.Utilisateur;
import com.airline.management.repository.UtilisateurRepository;
import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

@Controller
@RequestMapping
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("utilisateur") @Valid Utilisateur utilisateur, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            model.addAttribute("error", "Email déjà utilisé");
            return "register";
        }

        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        utilisateur.setRole("PASSAGER");
        utilisateur.setStatut("ACTIF");
        utilisateur.setDateCreation(LocalDateTime.now());
        utilisateurRepository.save(utilisateur);

        return "redirect:/login?registered";
    }
}
