package com.bikerental.bike_rental.Rental;

public class DailyRental extends Rental {

    private int days;
    private double ratePerDay;

    public DailyRental() {}

    public DailyRental(String rentalId, String userId, String bikeId,
                       String startDate, String endDate, String status,
                       int days, double ratePerDay) {
        super(rentalId, userId, bikeId, startDate, endDate, status);
        this.days       = days;
        this.ratePerDay = ratePerDay;
    }

    @Override
    public double calculateFee() {
        double total = days * ratePerDay;
        return total;
    }

    @Override
    public String getRentalType() {
        return "DAILY";
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        if (days > 0) {
            this.days = days;
        }
    }

    public double getRatePerDay() {
        return ratePerDay;
    }

    public void setRatePerDay(double ratePerDay) {
        this.ratePerDay = ratePerDay;
    }

    public String getRentalSummary() {
        return "Rental ID: " + getRentalId() + " | Days: " + days + " | Total: LKR " + calculateFee();
    }
}
