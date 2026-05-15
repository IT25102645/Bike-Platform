package com.bikerental.bike_rental.Payment;

public class RentalPayment extends Payment {
    private String rentalId;
    private int durationHours;
    private double pricePerHour;

    public RentalPayment(String paymentId, String userId, String rentalId,
                         int durationHours, double pricePerHour,
                         String paymentDate, String status) {
        super(paymentId, userId, 0, paymentDate, status);
        this.rentalId = rentalId;
        this.durationHours = durationHours;
        this.pricePerHour = pricePerHour;
        this.setAmount(calculatePayment());
    }

    // Polymorphism - Rental fee calculation
    @Override
    public double calculatePayment() {
        return durationHours * pricePerHour;
    }

    public String getRentalId() { return rentalId; }
    public void setRentalId(String rentalId) { this.rentalId = rentalId; }
    public int getDurationHours() { return durationHours; }
    public void setDurationHours(int durationHours) { this.durationHours = durationHours; }
    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }

    @Override
    public String toString() {
        return "RENTAL," + super.toString() + "," + rentalId + "," + durationHours + "," + pricePerHour;
    }
}