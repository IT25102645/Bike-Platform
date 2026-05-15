package com.bikerental.bike_rental.Bike;

public class ManualBike extends Bike {

    // Extra fields specific to manual bikes
    private int gearCount;
    private double longRentalDiscountRate;

    // Constructor
    public ManualBike(String bikeId, String model, String brand,
                      double pricePerHour, boolean available,
                      String ownerUsername, String description,
                      int gearCount, double longRentalDiscountRate) {
        super(bikeId, model, brand, pricePerHour, available, ownerUsername, description);
        this.gearCount              = gearCount;
        this.longRentalDiscountRate = longRentalDiscountRate;
    }

    public ManualBike() {
        super();
    }

    // Polymorphism: Manual bikes get a discount

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

    // Getters & Setters
    public int getGearCount()                          { return gearCount; }
    public void setGearCount(int g)                    { this.gearCount = g; }

    public double getLongRentalDiscountRate()          { return longRentalDiscountRate; }
    public void   setLongRentalDiscountRate(double d)  { this.longRentalDiscountRate = d; }

    // Override file string to include manual-specific fields
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + gearCount + "|" + longRentalDiscountRate;
    }
}