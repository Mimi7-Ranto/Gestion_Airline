package com.airline.management.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservation")
public class ReservationWebController {

    @GetMapping("/my-reservations")
    public String myReservationsScoped(Model model) {
        model.addAttribute("pageTitle", "Mes réservations (reservation controller)");
        return "my-reservations";
    }
}
