package com.bikerental.bike_rental.Review;

// Base class for all reviews - Encapsulation + Abstraction
public abstract class Review {

    // Private fields - Encapsulation
    private String reviewId;
    private String userId;
    private String username;
    private int rating;
    private String comment;
    private String date;

    // Default constructor
    public Review() {}

    // Parameterized constructor
    public Review(String reviewId, String userId, String username,
                  int rating, String comment, String date) {
        this.reviewId = reviewId;
        this.userId   = userId;
        this.username = username;
        this.rating   = rating;
        this.comment  = comment;
        this.date     = date;
    }

    // Abstract methods - subclasses must implement these (Polymorphism)
    public abstract String getReviewType();
    public abstract String getDisplaySummary();

    // Getters
    public String getReviewId() { return reviewId; }
    public String getUserId()   { return userId; }
    public String getUsername() { return username; }
    public int    getRating()   { return rating; }
    public String getComment()  { return comment; }
    public String getDate()     { return date; }

    // Setters
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }
    public void setUserId(String userId)     { this.userId   = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setRating(int rating)        { this.rating   = rating; }
    public void setComment(String comment)   { this.comment  = comment; }
    public void setDate(String date)         { this.date     = date; }

    // File handling - saves review as a pipe-separated line
    public String toFileString() {
        return reviewId + "|" + getReviewType() + "|" + userId + "|" +
                username + "|" + rating + "|" + comment + "|" + date;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='"  + reviewId + '\'' +
                ", userId='"  + userId   + '\'' +
                ", username='"+ username + '\'' +
                ", rating="   + rating   +
                ", date='"    + date     + '\'' +
                '}';
    }
}