package com.bikerental.bike_rental.Review;

// Inheritance - RideReview extends Review
public class RideReview extends Review {

    // Ride-specific fields
    private String rideId;
    private double rideDistanceKm;
    private int    rideDurationMinutes;
    private String routeExperience;
    private int    safetyRating;

    // Default constructor
    public RideReview() {
        super();
    }

    // Parameterized constructor
    public RideReview(String reviewId, String userId, String username,
                      int rating, String comment, String date,
                      String rideId, double rideDistanceKm,
                      int rideDurationMinutes, String routeExperience, int safetyRating) {
        super(reviewId, userId, username, rating, comment, date);
        this.rideId              = rideId;
        this.rideDistanceKm      = rideDistanceKm;
        this.rideDurationMinutes = rideDurationMinutes;
        this.routeExperience     = routeExperience;
        this.safetyRating        = safetyRating;
    }

    // Polymorphism - returns review type
    @Override
    public String getReviewType() {
        return "RIDE";
    }

    // Polymorphism - different display format for ride review
    @Override
    public String getDisplaySummary() {
        return "[Ride #" + rideId + "] " + getUsername() +
                " rated " + getRating() + "/5 | " +
                rideDistanceKm + " km in " + rideDurationMinutes + " min | " +
                "Route: " + routeExperience + " | Safety: " + safetyRating + "/5 | " +
                getComment();
    }

    // Getters
    public String getRideId()              { return rideId; }
    public double getRideDistanceKm()      { return rideDistanceKm; }
    public int    getRideDurationMinutes() { return rideDurationMinutes; }
    public String getRouteExperience()     { return routeExperience; }
    public int    getSafetyRating()        { return safetyRating; }

    // Setters
    public void setRideId(String rideId)                      { this.rideId              = rideId; }
    public void setRideDistanceKm(double rideDistanceKm)      { this.rideDistanceKm      = rideDistanceKm; }
    public void setRideDurationMinutes(int rideDurationMinutes){ this.rideDurationMinutes = rideDurationMinutes; }
    public void setRouteExperience(String routeExperience)    { this.routeExperience     = routeExperience; }
    public void setSafetyRating(int safetyRating)             { this.safetyRating        = safetyRating; }

    // File handling - adds ride fields to parent file string
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + rideId + "|" + rideDistanceKm +
                "|" + rideDurationMinutes + "|" + routeExperience + "|" + safetyRating;
    }

    @Override
    public String toString() {
        return "RideReview{" +
                "reviewId='"      + getReviewId()       + '\'' +
                ", username='"    + getUsername()       + '\'' +
                ", rating="       + getRating()         +
                ", rideId='"      + rideId              + '\'' +
                ", distance="     + rideDistanceKm      +
                ", duration="     + rideDurationMinutes +
                ", route='"       + routeExperience     + '\'' +
                ", safetyRating=" + safetyRating        +
                '}';
    }
}