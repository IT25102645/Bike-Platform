package com.bikerental.bike_rental.Payment;

// Inheritance
public class RentalPayment extends Payment {

    private String rentalId;
    private int rentalDuration; // hours or days
    private double rate;

    public RentalPayment() {
        super();
    }

    public RentalPayment(String paymentId, String userId, String rentalId,
                         int duration, double rate, String date, String status) {
        super(paymentId, userId, 0, date, status);
        this.rentalId       = rentalId;
        this.rentalDuration = duration;
        this.rate           = rate;
        this.setAmount(calculatePayment());
    }

    // Polymorphism - Rental cost calculation
    @Override
    public double calculatePayment() {
        return rentalDuration * rate;
    }

    @Override
    public String getPaymentType() {
        return "RENTAL";
    }

    // Getters
    public String getRentalId()       { return rentalId; }
    public int    getRentalDuration() { return rentalDuration; }
    public double getRate()           { return rate; }

    // Setters
    public void setRentalId(String rentalId)           { this.rentalId       = rentalId; }
    public void setRentalDuration(int rentalDuration)   { this.rentalDuration = rentalDuration; }
    public void setRate(double rate)                   { this.rate           = rate; }

    // File string - extends parent toFileString
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + rentalId + "|" + rentalDuration + "|" + rate;
    }
}
