package com.bikerental.bike_rental.Rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public String listRentals(Model model) {
        List<String[]> rentals = rentalService.getAllRentals();
        long active = rentals.stream().filter(r -> r[6].equals("ACTIVE")).count();
        long completed = rentals.stream().filter(r -> r[6].equals("COMPLETED")).count();
        model.addAttribute("rentals", rentals);
        model.addAttribute("totalRentals", rentals.size());
        model.addAttribute("activeRentals", active);
        model.addAttribute("completedRentals", completed);
        return "Rental/rental-history";
    }

    @GetMapping("/rent")
    public String rentBikeForm(@RequestParam(required = false) String bikeId, Model model) {
        model.addAttribute("minDate", java.time.LocalDate.now().toString());
        model.addAttribute("bikeId", bikeId);
        return "Rental/rent-bike";
    }

    @PostMapping("/rent")
    public String rentBike(@RequestParam String userId,
                           @RequestParam String bikeId,
                           @RequestParam String startDate,
                           @RequestParam String endDate,
                           @RequestParam String rentalType,
                           @RequestParam int duration,
                           @RequestParam double rate,
                           Model model) {

        if (userId.isEmpty() || bikeId.isEmpty()) {
            model.addAttribute("error", "User ID and Bike ID cannot be empty.");
            return "Rental/rent-bike";
        }

        if (duration <= 0) {
            model.addAttribute("error", "Duration must be greater than zero.");
            return "Rental/rent-bike";
        }

        String rentalId = rentalService.generateRentalId();
        Rental rental;

        if (rentalType.equals("HOURLY")) {
            rental = new HourlyRental(rentalId, userId, bikeId,
                    startDate, endDate, "ACTIVE", duration, rate);
        } else {
            rental = new DailyRental(rentalId, userId, bikeId,
                    startDate, endDate, "ACTIVE", duration, rate);
        }

        rentalService.saveRental(rental);
        return "redirect:/rentals";
    }

    @GetMapping("/return/{rentalId}")
    public String returnBikeForm(@PathVariable String rentalId, Model model) {
        model.addAttribute("rentalId", rentalId);
        model.addAttribute("returnDate", java.time.LocalDate.now().toString());
        return "Rental/return-bike";
    }

    @PostMapping("/return/{rentalId}")
    public String returnBike(@PathVariable String rentalId,
                             @RequestParam String endDate,
                             Model model) {
        if (endDate.isEmpty()) {
            model.addAttribute("error", "Return date cannot be empty.");
            model.addAttribute("rentalId", rentalId);
            return "Rental/return-bike";
        }

        rentalService.updateRental(rentalId, endDate, "COMPLETED");
        return "redirect:/rentals";
    }

    @GetMapping("/delete/{rentalId}")
    public String deleteRental(@PathVariable String rentalId) {
        rentalService.deleteRental(rentalId);
        return "redirect:/rentals";
    }
}