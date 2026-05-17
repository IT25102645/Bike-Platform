package com.bikerental.bike_rental.Payment;

// Inheritance - RentalPayment extends Payment
public class RentalPayment extends Payment {

    private String rentalId;      // matches RentalService → "RENT-1"
    private int    durationHours;
    private double pricePerHour;

    public RentalPayment() {}

    public RentalPayment(String paymentId, String userId, String rentalId,
                         int durationHours, double pricePerHour,
                         String paymentDate, String status) {
        super(paymentId, userId, 0, paymentDate, status);
        this.rentalId      = rentalId;
        this.durationHours = durationHours;
        this.pricePerHour  = pricePerHour;
        this.setAmount(calculatePayment());
    }

    // Polymorphism - Rental fee calculation
    @Override
    public double calculatePayment() {
        return durationHours * pricePerHour;
    }

    @Override
    public String getPaymentType() {
        return "RENTAL";
    }

    // Getters
    public String getRentalId()      { return rentalId; }
    public int    getDurationHours() { return durationHours; }
    public double getPricePerHour()  { return pricePerHour; }

    // Setters
    public void setRentalId(String rentalId)         { this.rentalId      = rentalId; }
    public void setDurationHours(int durationHours)  { this.durationHours = durationHours; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour  = pricePerHour; }

    // File string - extends parent toFileString
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + rentalId + "|" + durationHours + "|" + pricePerHour;
    }
}