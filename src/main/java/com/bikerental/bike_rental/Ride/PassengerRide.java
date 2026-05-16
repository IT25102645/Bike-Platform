package com.bikerental.bike_rental.Ride;

// Inheritance - PassengerRide extends Ride
public class PassengerRide extends Ride {

    private String driverRideId;
    private double costPerSeat;

    public PassengerRide() {}

    public PassengerRide(String rideId, String userId, String origin, String destination,
                         String rideDate, String rideTime, int seatsToBook,
                         String status, String driverRideId, double costPerSeat) {
        super(rideId, userId, origin, destination, rideDate, rideTime, seatsToBook, status);
        this.driverRideId = driverRideId;
        this.costPerSeat  = costPerSeat;
    }

    public String getDriverRideId() { return driverRideId; }
    public double getCostPerSeat()  { return costPerSeat; }

    public void setDriverRideId(String driverRideId) { this.driverRideId = driverRideId; }
    public void setCostPerSeat(double costPerSeat)   { this.costPerSeat  = costPerSeat; }

    // Polymorphism - Passenger pays based on booked seats
    @Override
    public double calculateCost() {
        return costPerSeat * getAvailableSeats(); // here seats = seatsToBook
    }

    @Override
    public String getRideType() {
        return "PASSENGER";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + driverRideId + "|" + costPerSeat;
    }
}
