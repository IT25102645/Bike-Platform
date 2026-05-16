package com.bikerental.bike_rental.Rental;

public class HourlyRental extends Rental {

    private int hours;
    private double ratePerHour;

    public HourlyRental() {}

    public HourlyRental(String rentalId, String userId, String bikeId,
                        String startDate, String endDate, String status,
                        int hours, double ratePerHour) {
        super(rentalId, userId, bikeId, startDate, endDate, status);
        this.hours       = hours;
        this.ratePerHour = ratePerHour;
    }

    @Override
    public double calculateFee() {
        double total = hours * ratePerHour;
        return total;
    }

    @Override
    public String getRentalType() {
        return "HOURLY";
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        if (hours > 0) {
            this.hours = hours;
        }
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public String getRentalSummary() {
        return "Rental ID: " + getRentalId() + " | Hours: " + hours + " | Total: LKR " + calculateFee();
    }
}
