package com.bikerental.bike_rental.Review;

import java.time.LocalDateTime;

public class Review {

    private String reviewId;
    private String userId;
    private String username;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean approved;

    public Review() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.approved = true;
    }

    public Review(String reviewId, String userId, String username, int rating, String comment) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.username = username;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.approved = true;
    }

    public String getReviewType() {
        return "General Review";
    }

    public String getDisplaySummary() {
        return "[" + getReviewType() + "] " + username + " rated " + rating + "/5: " + comment;
    }

    public String toCsvLine() {
        return reviewId + "," + userId + "," + username + "," + rating + ","
                + comment.replace(",", ";") + "," + createdAt + "," + updatedAt + "," + approved + "," + getReviewType();
    }

    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getRating() { return rating; }
    public void setRating(int rating) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be between 1 and 5");
        this.rating = rating;
    }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    @Override
    public String toString() {
        return getDisplaySummary();
    }
}