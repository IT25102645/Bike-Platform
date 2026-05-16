package com.bikerental.bike_rental.Ride;

// Encapsulation + Abstraction
public abstract class Ride {

    private String rideId;
    private String userId;
    private String origin;
    private String destination;
    private String rideDate;
    private String rideTime;
    private int availableSeats;
    private String status;

    public Ride() {}

    public Ride(String rideId, String userId, String origin, String destination,
                String rideDate, String rideTime, int availableSeats, String status) {
        this.rideId         = rideId;
        this.userId         = userId;
        this.origin         = origin;
        this.destination    = destination;
        this.rideDate       = rideDate;
        this.rideTime       = rideTime;
        this.availableSeats = availableSeats;
        this.status         = status;
    }

    // Abstract methods - Polymorphism
    public abstract double calculateCost();
    public abstract String getRideType();

    // Getters
    public String getRideId()        { return rideId; }
    public String getUserId()        { return userId; }
    public String getOrigin()        { return origin; }
    public String getDestination()   { return destination; }
    public String getRideDate()      { return rideDate; }
    public String getRideTime()      { return rideTime; }
    public int getAvailableSeats()   { return availableSeats; }
    public String getStatus()        { return status; }

    // Setters
    public void setRideId(String rideId)               { this.rideId         = rideId; }
    public void setUserId(String userId)               { this.userId         = userId; }
    public void setOrigin(String origin)               { this.origin         = origin; }
    public void setDestination(String destination)     { this.destination    = destination; }
    public void setRideDate(String rideDate)           { this.rideDate       = rideDate; }
    public void setRideTime(String rideTime)           { this.rideTime       = rideTime; }
    public void setAvailableSeats(int availableSeats)  { this.availableSeats = availableSeats; }
    public void setStatus(String status)               { this.status         = status; }

    // File Handling Helper
    public String toFileString() {
        return rideId + "|" + getRideType() + "|" + userId + "|" +
                origin + "|" + destination + "|" + rideDate + "|" +
                rideTime + "|" + availableSeats + "|" + status + "|" + calculateCost();
    }

    @Override
    public String toString() {
        return "Ride{" +
                "rideId='"        + rideId         + '\'' +
                ", userId='"      + userId         + '\'' +
                ", origin='"      + origin         + '\'' +
                ", destination='" + destination    + '\'' +
                ", date='"        + rideDate       + '\'' +
                ", time='"        + rideTime       + '\'' +
                ", seats="        + availableSeats +
                ", status='"      + status         + '\'' +
                '}';
    }
}
