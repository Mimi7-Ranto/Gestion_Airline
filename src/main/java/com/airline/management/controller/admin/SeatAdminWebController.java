package com.airline.management.controller.admin;

import com.airline.management.entity.Avion;
import com.airline.management.repository.AvionRepository;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class SeatAdminWebController {

    private final AvionRepository avionRepository;

    public SeatAdminWebController(AvionRepository avionRepository) {
        this.avionRepository = avionRepository;
    }

    @GetMapping("/aircraft/{id}/seats")
    public String manageSeats(@PathVariable("id") String avionId, Model model) {
        Optional<Avion> avion = avionRepository.findById(avionId);
        if (avion.isEmpty()) {
            model.addAttribute("error", "Avion introuvable");
            return "admin/seats";
        }

        model.addAttribute("avionId", avionId);
        model.addAttribute("avionModele", avion.get().getModele());
        return "admin/seats";
    }
}
