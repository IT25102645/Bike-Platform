package com.bikerental.bike_rental.Rental;

public abstract class Rental {
    private String rentalId;
    private String userId;
    private String bikeId;
    private String startDate;
    private String endDate;
    private String status;

    public Rental(String rentalId, String userId, String bikeId,
                  String startDate, String endDate, String status) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.bikeId = bikeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public abstract double calculateFee();

    // Getters & Setters
    public String getRentalId() { return rentalId; }
    public void setRentalId(String rentalId) { this.rentalId = rentalId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getBikeId() { return bikeId; }
    public void setBikeId(String bikeId) { this.bikeId = bikeId; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return rentalId + "," + userId + "," + bikeId + "," +
                startDate + "," + endDate + "," + status;
    }
}