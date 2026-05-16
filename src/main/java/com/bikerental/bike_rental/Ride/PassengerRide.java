package com.bikerental.bike_rental.Ride;

// Inheritance - PassengerRide extends Ride
public class PassengerRide extends Ride {

    private String driverRideId;
    private double farePerSeat;

    public PassengerRide() {}

    public PassengerRide(String rideId, String userId, String origin, String destination,
                         String rideDate, String rideTime, int availableSeats,
                         String status, String driverRideId, double farePerSeat) {
        super(rideId, userId, origin, destination, rideDate, rideTime, availableSeats, status);
        this.driverRideId = driverRideId;
        this.farePerSeat  = farePerSeat;
    }

    public String getDriverRideId() { return driverRideId; }
    public double getFarePerSeat()  { return farePerSeat; }

    public void setDriverRideId(String driverRideId) { this.driverRideId = driverRideId; }
    public void setFarePerSeat(double farePerSeat)   { this.farePerSeat  = farePerSeat; }

    // Polymorphism - Passenger pays fare per seat
    @Override
    public double calculateCost() {
        return farePerSeat;
    }

    @Override
    public String getRideType() {
        return "PASSENGER";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + driverRideId + "|" + farePerSeat;
    }
}
