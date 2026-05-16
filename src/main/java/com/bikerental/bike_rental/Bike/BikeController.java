package com.bikerental.bike_rental.Bike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bikes")
public class BikeController {

    // Inject BikeService
    @Autowired
    private BikeService bikeService;

    //  Show Bike List Page
    @GetMapping
    public String listBikes(Model model) {
        model.addAttribute("bikes", bikeService.getAllBikes());
        model.addAttribute("pageTitle", "All Bikes");
        return "bike/bike-list";
    }

    // Show only available bikes
    @GetMapping("/available")
    public String availableBikes(Model model) {
        model.addAttribute("bikes", bikeService.getAvailableBikes());
        model.addAttribute("pageTitle", "Available Bikes");
        return "bike/bike-list";
    }

    //  READ - Search Bikes Page
    @GetMapping("/search")
    public String searchBikesPage(
            @RequestParam(required = false) String keyword,
            Model model) {

        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("bikes", bikeService.searchBikes(keyword));
            model.addAttribute("keyword", keyword);
        }
        return "bike/bike-search";
    }

    //  CREATE - Show Add Bike Form
    @GetMapping("/add")
    public String showAddBikeForm() {
        return "bike/add-bike";
    }

    //  CREATE - Handle Add Bike Form Submission
    @PostMapping("/add")
    public String addBike(
            @RequestParam String bikeType,
            @RequestParam String model,
            @RequestParam String brand,
            @RequestParam double pricePerHour,
            @RequestParam String ownerUsername,
            @RequestParam String description,
            @RequestParam(required = false, defaultValue = "0") int batteryRangeKm,
            @RequestParam(required = false, defaultValue = "0") double chargingFeePerHour,
            @RequestParam(required = false, defaultValue = "0") int gearCount,
            @RequestParam(required = false, defaultValue = "0") double longRentalDiscountRate,
            RedirectAttributes redirectAttributes) {

        try {
            // Generate unique bike ID
            String bikeId = "BIKE-" + System.currentTimeMillis();

            Bike bike;

            // Create correct subclass based on type - Polymorphism
            if ("ELECTRIC".equalsIgnoreCase(bikeType)) {
                bike = new ElectricBike(bikeId, model, brand, pricePerHour,
                        true, ownerUsername, description,
                        batteryRangeKm, chargingFeePerHour);
            } else {
                bike = new ManualBike(bikeId, model, brand, pricePerHour,
                        true, ownerUsername, description,
                        gearCount, longRentalDiscountRate);
            }

            // Save to bikes.txt via service
            boolean success = bikeService.addBike(bike);

            if (success) {
                redirectAttributes.addFlashAttribute("success",
                        "Bike added successfully! ID: " + bikeId);
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Failed to add bike. Please try again.");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "System error: " + e.getMessage());
        }

        return "redirect:/bikes";
    }

    //  UPDATE - Show Edit Form
    @GetMapping("/edit/{bikeId}")
    public String showEditForm(@PathVariable String bikeId, Model model) {

        try {
            Bike bike = bikeService.findBikeById(bikeId);

            if (bike == null) {
                model.addAttribute("error", "Bike not found.");
                return "redirect:/bikes";
            }

            model.addAttribute("bike", bike);
            return "bike/edit-bike";

        } catch (Exception e) {
            model.addAttribute("error", "System error: " + e.getMessage());
            return "redirect:/bikes";
        }
    }

    //  UPDATE - Handle Edit Form Submission
    @PostMapping("/edit/{bikeId}")
    public String editBike(
            @PathVariable String bikeId,
            @RequestParam String model,
            @RequestParam String brand,
            @RequestParam double pricePerHour,
            @RequestParam boolean available,
            @RequestParam String description,
            @RequestParam(required = false, defaultValue = "0") int batteryRangeKm,
            @RequestParam(required = false, defaultValue = "0") double chargingFeePerHour,
            @RequestParam(required = false, defaultValue = "0") int gearCount,
            @RequestParam(required = false, defaultValue = "0") double longRentalDiscountRate,
            RedirectAttributes redirectAttributes) {

        try {
            Bike existing = bikeService.findBikeById(bikeId);

            if (existing == null) {
                redirectAttributes.addFlashAttribute("error", "Bike not found.");
                return "redirect:/bikes";
            }

            // Update common fields
            existing.setModel(model);
            existing.setBrand(brand);
            existing.setPricePerHour(pricePerHour);
            existing.setAvailable(available);
            existing.setDescription(description);

            // Update type-specific fields - Polymorphism
            if (existing instanceof ElectricBike eb) {
                eb.setBatteryRangeKm(batteryRangeKm);
                eb.setChargingFeePerHour(chargingFeePerHour);
            } else if (existing instanceof ManualBike mb) {
                mb.setGearCount(gearCount);
                mb.setLongRentalDiscountRate(longRentalDiscountRate);
            }

            boolean updated = bikeService.updateBike(existing);

            if (updated) {
                redirectAttributes.addFlashAttribute("success", "Bike updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Update failed.");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "System error: " + e.getMessage());
        }

        return "redirect:/bikes";
    }

    //  DELETE - Remove a Bike
    @PostMapping("/delete/{bikeId}")
    public String deleteBike(@PathVariable String bikeId,
                             RedirectAttributes redirectAttributes) {

        try {
            boolean deleted = bikeService.deleteBike(bikeId);

            if (deleted) {
                redirectAttributes.addFlashAttribute("success",
                        "Bike deleted successfully.");
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Bike not found or could not be deleted.");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "System error: " + e.getMessage());
        }

        return "redirect:/bikes";
    }

    @GetMapping("/price-preview")
    @ResponseBody
    public String pricePreview(@RequestParam String bikeId,
                               @RequestParam int hours) {
        Bike bike = bikeService.findBikeById(bikeId);
        if (bike == null) return "Bike not found";
        double price = bike.calculateRentalPrice(hours);    // Polymorphism in action
        return String.format("%.2f", price);
    }
}