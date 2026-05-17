package com.bikerental.bike_rental.Ride;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    // READ - List all rides
    @GetMapping
    public String listRides(Model model) {
        model.addAttribute("rides", rideService.getAllRides());
        model.addAttribute("totalRides", rideService.getAllRides().size());
        return "Ride/ride-list";
    }

    // CREATE - Show create ride form
    @GetMapping("/create")
    public String createRideForm(Model model) {
        model.addAttribute("minDate", java.time.LocalDate.now().toString());
        return "Ride/create-ride";
    }

    // CREATE - Handle create ride form submission
    @PostMapping("/create")
    public String createRide(@RequestParam String userId,
                             @RequestParam String origin,
                             @RequestParam String destination,
                             @RequestParam String rideDate,
                             @RequestParam String rideTime,
                             @RequestParam int availableSeats,
                             @RequestParam double pricePerSeat,
                             Model model) {

        if (userId.isEmpty() || origin.isEmpty() || destination.isEmpty()) {
            model.addAttribute("error", "All fields are required.");
            return "Ride/create-ride";
        }

        if (availableSeats <= 0) {
            model.addAttribute("error", "Available seats must be greater than zero.");
            return "Ride/create-ride";
        }

        String rideId = rideService.generateRideId();
        Ride ride = new DriverRide(rideId, userId, origin, destination,
                rideDate, rideTime, availableSeats, "ACTIVE", pricePerSeat);

        rideService.saveRide(ride);
        return "redirect:/rides";
    }

    // READ - Show available rides for passengers to join
    @GetMapping("/join")
    public String joinRidePage(Model model) {
        model.addAttribute("availableRides", rideService.getAvailableDriverRides());
        return "Ride/join-ride";
    }

    // CREATE - Passenger joins a ride
    @PostMapping("/join")
    public String joinRide(@RequestParam String userId,
                           @RequestParam String driverRideId,
                           @RequestParam String origin,
                           @RequestParam String destination,
                           @RequestParam String rideDate,
                           @RequestParam String rideTime,
                           @RequestParam double farePerSeat,
                           Model model) {

        if (userId.isEmpty() || driverRideId.isEmpty()) {
            model.addAttribute("error", "User ID and Ride ID cannot be empty.");
            model.addAttribute("availableRides", rideService.getAvailableDriverRides());
            return "Ride/join-ride";
        }

        String rideId = rideService.generateRideId();
        Ride passengerRide = new PassengerRide(rideId, userId, origin, destination,
                rideDate, rideTime, 1, "ACTIVE", driverRideId, farePerSeat);

        rideService.saveRide(passengerRide);
        return "redirect:/rides";
    }

    // UPDATE - Show edit ride form
    @GetMapping("/edit/{rideId}")
    public String editRideForm(@PathVariable String rideId, Model model) {
        List<String[]> rides = rideService.getAllRides();
        for (String[] ride : rides) {
            if (ride[0].equals(rideId)) {
                model.addAttribute("ride", ride);
                break;
            }
        }
        return "Ride/edit-ride";
    }

    // UPDATE - Handle edit ride form submission
    @PostMapping("/edit/{rideId}")
    public String editRide(@PathVariable String rideId,
                           @RequestParam String rideDate,
                           @RequestParam String rideTime,
                           @RequestParam String origin,
                           @RequestParam String destination,
                           Model model) {

        if (origin.isEmpty() || destination.isEmpty()) {
            model.addAttribute("error", "Origin and destination cannot be empty.");
            return "Ride/edit-ride";
        }

        rideService.updateRide(rideId, rideDate, rideTime, origin, destination, "ACTIVE");
        return "redirect:/rides";
    }

    // DELETE - Cancel/delete a ride
    @GetMapping("/delete/{rideId}")
    public String deleteRide(@PathVariable String rideId) {
        rideService.deleteRide(rideId);
        return "redirect:/rides";
    }
}
