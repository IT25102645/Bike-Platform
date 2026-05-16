package com.bikerental.bike_rental.Review;

public class BikeReview extends Review {

    private String bikeId;
    private String bikeName;
    private String bikeCondition;
    private boolean wouldRecommend;

    public BikeReview() {
        super();
    }

    public BikeReview(String reviewId, String userId, String username,
                      int rating, String comment,
                      String bikeId, String bikeName,
                      String bikeCondition, boolean wouldRecommend) {
        super(reviewId, userId, username, rating, comment);
        this.bikeId = bikeId;
        this.bikeName = bikeName;
        this.bikeCondition = bikeCondition;
        this.wouldRecommend = wouldRecommend;
    }

    @Override
    public String getReviewType() {
        return "Bike Review";
    }

    @Override
    public String getDisplaySummary() {
        return "[Bike: " + bikeName + "] " + getUsername() + " rated " + getRating() + "/5 | "
                + "Condition: " + bikeCondition + " | "
                + "Recommend: " + (wouldRecommend ? "Yes" : "No") + " | "
                + getComment();
    }

    @Override
    public String toCsvLine() {
        return super.toCsvLine() + "," + bikeId + "," + bikeName + "," + bikeCondition + "," + wouldRecommend;
    }

    public String getBikeId() { return bikeId; }
    public void setBikeId(String bikeId) { this.bikeId = bikeId; }

    public String getBikeName() { return bikeName; }
    public void setBikeName(String bikeName) { this.bikeName = bikeName; }

    public String getBikeCondition() { return bikeCondition; }
    public void setBikeCondition(String bikeCondition) { this.bikeCondition = bikeCondition; }

    public boolean isWouldRecommend() { return wouldRecommend; }
    public void setWouldRecommend(boolean wouldRecommend) { this.wouldRecommend = wouldRecommend; }
}