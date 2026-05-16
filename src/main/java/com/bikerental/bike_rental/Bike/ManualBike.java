package com.bikerental.bike_rental.Bike;

// Inheritance
public class ManualBike extends Bike {

    private int gearCount;
    private double longRentalDiscountRate;

    // Default constructor
    public ManualBike() {
        super();
    }

    // Parameterized constructor
    public ManualBike(String bikeId, String model, String brand,
                      double pricePerHour, boolean available,
                      String ownerUsername, String description,
                      int gearCount, double longRentalDiscountRate) {
        super(bikeId, model, brand, pricePerHour, available, ownerUsername, description);
        this.gearCount              = gearCount;
        this.longRentalDiscountRate = longRentalDiscountRate;
    }

    // Polymorphism: Manual bikes get a discount on long rentals (> 8 hours)
    @Override
    public double calculateRentalPrice(int hours) {
        double base = getPricePerHour() * hours;
        if (hours > 8) {
            double discount = base * longRentalDiscountRate;
            return base - discount;
        }
        return base;
    }

    @Override
    public String getBikeType() {
        return "MANUAL";
    }

    // Getters
    public int    getGearCount()              { return gearCount; }
    public double getLongRentalDiscountRate() { return longRentalDiscountRate; }

    // Setters
    public void setGearCount(int g)                   { this.gearCount              = g; }
    public void setLongRentalDiscountRate(double d)   { this.longRentalDiscountRate = d; }

    // File Handling Helper
    // Extends parent toFileString() to include manual-specific fields
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + gearCount + "|" + longRentalDiscountRate;
    }

    @Override
    public String toString() {
        return "ManualBike{" +
                "bikeId='"               + getBikeId()              + '\'' +
                ", model='"              + getModel()                + '\'' +
                ", brand='"              + getBrand()                + '\'' +
                ", price="               + getPricePerHour()         +
                ", available="           + isAvailable()             +
                ", owner='"              + getOwnerUsername()        + '\'' +
                ", gearCount="           + gearCount                 +
                ", discountRate="        + longRentalDiscountRate    +
                '}';
    }
}
