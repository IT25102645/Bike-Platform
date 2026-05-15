package com.bikerental.bike_rental.Rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public String listRentals(Model model) {
        model.addAttribute("rentals", rentalService.getAllRentals());
        return "Rental/rental-history";
    }

    @GetMapping("/rent")
    public String rentBikeForm() {
        return "Rental/rent-bike";
    }

    @PostMapping("/rent")
    public String rentBike(@RequestParam String userId,
                           @RequestParam String bikeId,
                           @RequestParam String startDate,
                           @RequestParam String endDate,
                           @RequestParam int hours,
                           @RequestParam double ratePerHour) {
        HourlyRental rental = new HourlyRental(
                rentalService.generateRentalId(), userId, bikeId,
                startDate, endDate, "ACTIVE", hours, ratePerHour
        );
        rentalService.saveRental(rental);
        return "redirect:/rentals";
    }

    @GetMapping("/return/{rentalId}")
    public String returnBikeForm(@PathVariable String rentalId, Model model) {
        model.addAttribute("rentalId", rentalId);
        return "Rental/return-bike";
    }

    @PostMapping("/return/{rentalId}")
    public String returnBike(@PathVariable String rentalId,
                             @RequestParam String endDate) {
        rentalService.updateRental(rentalId, endDate, "COMPLETED");
        return "redirect:/rentals";
    }

    @GetMapping("/delete/{rentalId}")
    public String deleteRental(@PathVariable String rentalId) {
        rentalService.deleteRental(rentalId);
        return "redirect:/rentals";
    }
}