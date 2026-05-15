package com.bikerental.bike_rental.Payment;

public class RidePayment extends Payment {
    private String rideId;
    private double distanceKm;
    private double ratePerKm;

    public RidePayment(String paymentId, String userId, String rideId,
                       double distanceKm, double ratePerKm,
                       String paymentDate, String status) {
        super(paymentId, userId, 0, paymentDate, status);
        this.rideId = rideId;
        this.distanceKm = distanceKm;
        this.ratePerKm = ratePerKm;
        this.setAmount(calculatePayment());
    }

    // Polymorphism - Ride cost calculation
    @Override
    public double calculatePayment() {
        return distanceKm * ratePerKm;
    }

    public String getRideId() { return rideId; }
    public void setRideId(String rideId) { this.rideId = rideId; }
    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }
    public double getRatePerKm() { return ratePerKm; }
    public void setRatePerKm(double ratePerKm) { this.ratePerKm = ratePerKm; }

    @Override
    public String toString() {
        return "RIDE," + super.toString() + "," + rideId + "," + distanceKm + "," + ratePerKm;
    }
}