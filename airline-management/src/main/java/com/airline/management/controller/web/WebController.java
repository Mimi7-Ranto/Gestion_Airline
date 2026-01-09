package com.airline.management.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Accueil");
        return "index";
    }

    @GetMapping("/aeroports")
    public String aeroports(Model model) {
        model.addAttribute("pageTitle", "Gestion des Aéroports");
        return "aeroports";
    }

    @GetMapping("/vols")
    public String vols(Model model) {
        model.addAttribute("pageTitle", "Gestion des Vols");
        return "vols";
    }

    @GetMapping("/reservations")
    public String reservations(Model model) {
        model.addAttribute("pageTitle", "Réservations");
        return "reservations";
    }
}
