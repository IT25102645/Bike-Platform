package com.bikerental.bike_rental.Ride;

// Inheritance - DriverRide extends Ride
public class DriverRide extends Ride {

    private double pricePerSeat;

    public DriverRide() {}

    public DriverRide(String rideId, String userId, String origin, String destination,
                      String rideDate, String rideTime, int availableSeats,
                      String status, double pricePerSeat) {
        super(rideId, userId, origin, destination, rideDate, rideTime, availableSeats, status);
        this.pricePerSeat = pricePerSeat;
    }

    public double getPricePerSeat() { return pricePerSeat; }
    public void setPricePerSeat(double pricePerSeat) { this.pricePerSeat = pricePerSeat; }

    // Polymorphism - Driver earns based on seats offered
    @Override
    public double calculateCost() {
        return pricePerSeat * getAvailableSeats();
    }

    @Override
    public String getRideType() {
        return "DRIVER";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + pricePerSeat;
    }
}
