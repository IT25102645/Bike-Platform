package com.bikerental.bike_rental.Review;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Service class - handles all file read/write operations for reviews
@Service
public class ReviewService {

    // File path for storing reviews
    private static final String FILE_PATH = "src/main/resources/data/reviews.txt";

    // CREATE - saves a new review to the file
    public void saveReview(Review review) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(review.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving review: " + e.getMessage());
        }
    }

    // READ - reads all reviews from the file and returns as list of arrays
    public List<String[]> getAllReviews() {
        List<String[]> reviews = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return reviews;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 7) {
                        reviews.add(parts);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading reviews: " + e.getMessage());
        }
        return reviews;
    }

    // DELETE - removes a review by ID and rewrites the file
    public void deleteReview(String reviewId) {
        List<String[]> reviews = getAllReviews();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] review : reviews) {
                if (!review[0].equals(reviewId)) {
                    writer.write(String.join("|", review));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error deleting review: " + e.getMessage());
        }
    }

    // UPDATE - updates rating and comment of a review by ID
    public void updateReview(String reviewId, int rating, String comment) {
        List<String[]> reviews = getAllReviews();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] review : reviews) {
                if (review[0].equals(reviewId)) {
                    review[4] = String.valueOf(rating);
                    review[5] = comment;
                }
                writer.write(String.join("|", review));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating review: " + e.getMessage());
        }
    }

    // Checks if a review exists by ID
    public boolean isReviewExists(String reviewId) {
        List<String[]> reviews = getAllReviews();
        for (String[] review : reviews) {
            if (review[0].equals(reviewId)) {
                return true;
            }
        }
        return false;
    }

    // Generates a new unique review ID
    public String generateReviewId() {
        List<String[]> reviews = getAllReviews();
        int count = reviews.size() + 1;
        return "REV-" + count;
    }

    // Calculates average rating from all reviews
    public double getAverageRating() {
        List<String[]> reviews = getAllReviews();
        if (reviews.isEmpty()) return 0.0;
        int total = 0;
        for (String[] review : reviews) {
            try {
                total += Integer.parseInt(review[4]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid rating value: " + review[4]);
            }
        }
        return (double) total / reviews.size();
    }
}