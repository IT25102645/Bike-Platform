package com.bikerental.bike_rental.Review;

// Inheritance - BikeReview extends Review
public class BikeReview extends Review {

    // Bike-specific fields
    private String bikeId;
    private String bikeName;
    private String bikeCondition;
    private boolean wouldRecommend;

    // Default constructor
    public BikeReview() {
        super();
    }

    // Parameterized constructor
    public BikeReview(String reviewId, String userId, String username,
                      int rating, String comment, String date,
                      String bikeId, String bikeName,
                      String bikeCondition, boolean wouldRecommend) {
        super(reviewId, userId, username, rating, comment, date);
        this.bikeId         = bikeId;
        this.bikeName       = bikeName;
        this.bikeCondition  = bikeCondition;
        this.wouldRecommend = wouldRecommend;
    }

    // Polymorphism - returns review type
    @Override
    public String getReviewType() {
        return "BIKE";
    }

    // Polymorphism - different display format for bike review
    @Override
    public String getDisplaySummary() {
        return "[Bike: " + bikeName + "] " + getUsername() +
                " rated " + getRating() + "/5 | Condition: " + bikeCondition +
                " | Recommend: " + (wouldRecommend ? "Yes" : "No") +
                " | " + getComment();
    }

    // Getters
    public String  getBikeId()        { return bikeId; }
    public String  getBikeName()      { return bikeName; }
    public String  getBikeCondition() { return bikeCondition; }
    public boolean isWouldRecommend() { return wouldRecommend; }

    // Setters
    public void setBikeId(String bikeId)               { this.bikeId         = bikeId; }
    public void setBikeName(String bikeName)           { this.bikeName       = bikeName; }
    public void setBikeCondition(String bikeCondition) { this.bikeCondition  = bikeCondition; }
    public void setWouldRecommend(boolean w)           { this.wouldRecommend = w; }

    // File handling - adds bike fields to parent file string
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + bikeId + "|" + bikeName +
                "|" + bikeCondition + "|" + wouldRecommend;
    }

    @Override
    public String toString() {
        return "BikeReview{" +
                "reviewId='"    + getReviewId()  + '\'' +
                ", username='"  + getUsername()  + '\'' +
                ", rating="     + getRating()    +
                ", bikeId='"    + bikeId         + '\'' +
                ", bikeName='"  + bikeName       + '\'' +
                ", condition='" + bikeCondition  + '\'' +
                ", recommend="  + wouldRecommend +
                '}';
    }
}
