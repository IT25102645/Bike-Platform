package com.bikerental.bike_rental.Rental;

public abstract class Rental {

    private String rentalId;
    private String userId;
    private String bikeId;
    private String startDate;
    private String endDate;
    private String status;

    public Rental() {}

    public Rental(String rentalId, String userId, String bikeId,
                  String startDate, String endDate, String status) {
        this.rentalId  = rentalId;
        this.userId    = userId;
        this.bikeId    = bikeId;
        this.startDate = startDate;
        this.endDate   = endDate;
        this.status    = status;
    }

    public abstract double calculateFee();
    public abstract String getRentalType();

    public String getRentalId()  { return rentalId; }
    public String getUserId()    { return userId; }
    public String getBikeId()    { return bikeId; }
    public String getStartDate() { return startDate; }
    public String getEndDate()   { return endDate; }
    public String getStatus()    { return status; }

    public void setRentalId(String rentalId)   { this.rentalId  = rentalId; }
    public void setUserId(String userId)       { this.userId    = userId; }
    public void setBikeId(String bikeId)       { this.bikeId    = bikeId; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate)     { this.endDate   = endDate; }
    public void setStatus(String status)       { this.status    = status; }

    public String toFileString() {
        return rentalId + "|" + getRentalType() + "|" + userId + "|" +
                bikeId + "|" + startDate + "|" + endDate + "|" +
                status + "|" + calculateFee();
    }

    @Override
    public String toString() {
        return "Rental{" +
                "rentalId='"  + rentalId  + '\'' +
                ", userId='"  + userId    + '\'' +
                ", bikeId='"  + bikeId    + '\'' +
                ", start='"   + startDate + '\'' +
                ", end='"     + endDate   + '\'' +
                ", status='"  + status    + '\'' +
                '}';
    }
}