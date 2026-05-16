package com.bikerental.bike_rental.Bike;

// Inheritance
public class ElectricBike extends Bike {

    private int batteryRangeKm;
    private double chargingFeePerHour;

    // Default constructor
    public ElectricBike() {
        super();
    }

    // Parameterized constructor
    public ElectricBike(String bikeId, String model, String brand,
                        double pricePerHour, boolean available,
                        String ownerUsername, String description,
                        int batteryRangeKm, double chargingFeePerHour) {
        super(bikeId, model, brand, pricePerHour, available, ownerUsername, description);
        this.batteryRangeKm     = batteryRangeKm;
        this.chargingFeePerHour = chargingFeePerHour;
    }

    // Polymorphism: Electric bikes charge a 10% eco-surcharge
    // + charging fee for rentals longer than 4 hours
    @Override
    public double calculateRentalPrice(int hours) {
        double base     = getPricePerHour() * hours;
        double ecoFee   = base * 0.10;
        double charging = (hours > 4) ? chargingFeePerHour * hours : 0;
        return base + ecoFee + charging;
    }

    @Override
    public String getBikeType() {
        return "ELECTRIC";
    }

    // Getters
    public int    getBatteryRangeKm()    { return batteryRangeKm; }
    public double getChargingFeePerHour(){ return chargingFeePerHour; }

    // Setters
    public void setBatteryRangeKm(int b)          { this.batteryRangeKm     = b; }
    public void setChargingFeePerHour(double c)   { this.chargingFeePerHour = c; }

    // File Handling Helper
    // Extends parent toFileString() to include electric-specific fields
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + batteryRangeKm + "|" + chargingFeePerHour;
    }

    @Override
    public String toString() {
        return "ElectricBike{" +
                "bikeId='"          + getBikeId()         + '\'' +
                ", model='"         + getModel()           + '\'' +
                ", brand='"         + getBrand()           + '\'' +
                ", price="          + getPricePerHour()    +
                ", available="      + isAvailable()        +
                ", owner='"         + getOwnerUsername()   + '\'' +
                ", batteryRangeKm=" + batteryRangeKm       +
                ", chargingFee="    + chargingFeePerHour   +
                '}';
    }
}