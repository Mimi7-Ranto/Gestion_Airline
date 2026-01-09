package com.airline.management.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminReservationWebController {

    @GetMapping("/reservations/create")
    public String createReservationPage(Model model) {
        model.addAttribute("pageTitle", "Créer réservation (admin)");
        return "admin/create-reservation";
    }
}
