package com.bikerental.bike_rental.Payment;

// Inheritance
public class RidePayment extends Payment {

    private String rideId;
    private double distanceKm;
    private double ratePerKm;

    public RidePayment() {
        super();
    }

    public RidePayment(String paymentId, String userId, String rideId,
                       double distanceKm, double ratePerKm, String date, String status) {
        super(paymentId, userId, 0, date, status);
        this.rideId      = rideId;
        this.distanceKm  = distanceKm;
        this.ratePerKm   = ratePerKm;
        this.setAmount(calculatePayment());
    }

    // Polymorphism - Ride cost calculation
    @Override
    public double calculatePayment() {
        return distanceKm * ratePerKm;
    }

    @Override
    public String getPaymentType() {
        return "RIDE";
    }

    // Getters
    public String getRideId()       { return rideId; }
    public double getDistanceKm()   { return distanceKm; }
    public double getRatePerKm()    { return ratePerKm; }

    // Setters
    public void setRideId(String rideId)           { this.rideId     = rideId; }
    public void setDistanceKm(double distanceKm)   { this.distanceKm = distanceKm; }
    public void setRatePerKm(double ratePerKm)     { this.ratePerKm  = ratePerKm; }

    // File string - extends parent toFileString
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + rideId + "|" + distanceKm + "|" + ratePerKm;
    }
}
