package com.bikerental.bike_rental.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

// Controller - handles all HTTP requests for reviews
@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // READ - show all reviews page
    @GetMapping
    public String listReviews(Model model) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        model.addAttribute("averageRating", reviewService.getAverageRating());
        return "review/review-list";
    }

    // Show the review submission form
    @GetMapping("/submit")
    public String showSubmitForm() {
        return "review/submit-review";
    }

    // CREATE - submit a new bike review
    @PostMapping("/submit/bike")
    public String submitBikeReview(
            @RequestParam String userId,
            @RequestParam String username,
            @RequestParam int rating,
            @RequestParam String comment,
            @RequestParam String bikeId,
            @RequestParam String bikeName,
            @RequestParam String bikeCondition,
            @RequestParam(defaultValue = "false") boolean wouldRecommend,
            RedirectAttributes redirectAttributes) {

        String reviewId = reviewService.generateReviewId();
        String date     = LocalDate.now().toString();

        BikeReview review = new BikeReview(
                reviewId, userId, username, rating, comment, date,
                bikeId, bikeName, bikeCondition, wouldRecommend
        );
        reviewService.saveReview(review);
        redirectAttributes.addFlashAttribute("successMessage", "Bike review submitted successfully!");
        return "redirect:/reviews";
    }

    // CREATE - submit a new ride review
    @PostMapping("/submit/ride")
    public String submitRideReview(
            @RequestParam String userId,
            @RequestParam String username,
            @RequestParam int rating,
            @RequestParam String comment,
            @RequestParam String rideId,
            @RequestParam double rideDistanceKm,
            @RequestParam int rideDurationMinutes,
            @RequestParam String routeExperience,
            @RequestParam int safetyRating,
            RedirectAttributes redirectAttributes) {

        String reviewId = reviewService.generateReviewId();
        String date     = LocalDate.now().toString();

        RideReview review = new RideReview(
                reviewId, userId, username, rating, comment, date,
                rideId, rideDistanceKm, rideDurationMinutes, routeExperience, safetyRating
        );
        reviewService.saveReview(review);
        redirectAttributes.addFlashAttribute("successMessage", "Ride review submitted successfully!");
        return "redirect:/reviews";
    }

    // Show edit form for a specific review
    @GetMapping("/edit/{reviewId}")
    public String showEditForm(@PathVariable String reviewId, Model model,
                               RedirectAttributes redirectAttributes) {
        List<String[]> reviews = reviewService.getAllReviews();
        for (String[] review : reviews) {
            if (review[0].equals(reviewId)) {
                model.addAttribute("review", review);
                return "review/submit-review";
            }
        }
        redirectAttributes.addFlashAttribute("errorMessage", "Review not found.");
        return "redirect:/reviews";
    }

    // UPDATE - update an existing review
    @PostMapping("/update/{reviewId}")
    public String updateReview(
            @PathVariable String reviewId,
            @RequestParam int rating,
            @RequestParam String comment,
            RedirectAttributes redirectAttributes) {

        if (reviewService.isReviewExists(reviewId)) {
            reviewService.updateReview(reviewId, rating, comment);
            redirectAttributes.addFlashAttribute("successMessage", "Review updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Review not found.");
        }
        return "redirect:/reviews";
    }

    // DELETE - delete a review
    @PostMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable String reviewId,
                               RedirectAttributes redirectAttributes) {
        if (reviewService.isReviewExists(reviewId)) {
            reviewService.deleteReview(reviewId);
            redirectAttributes.addFlashAttribute("successMessage", "Review deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Review not found.");
        }
        return "redirect:/reviews/admin";
    }

    // Show admin page with all reviews
    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        model.addAttribute("averageRating", reviewService.getAverageRating());
        return "review/admin-review";
    }
}
