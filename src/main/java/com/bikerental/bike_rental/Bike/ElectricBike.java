package com.bikerental.bike_rental.Bike;

public class ElectricBike extends Bike {

    // Extra fields specific to electric bikes
    private int batteryRangeKm;       // km range on full charge
    private double chargingFeePerHour; // extra charging fee

    // Constructor
    public ElectricBike(String bikeId, String model, String brand,
                        double pricePerHour, boolean available,
                        String ownerUsername, String description,
                        int batteryRangeKm, double chargingFeePerHour) {
        super(bikeId, model, brand, pricePerHour, available, ownerUsername, description);
        this.batteryRangeKm    = batteryRangeKm;
        this.chargingFeePerHour = chargingFeePerHour;
    }

    public ElectricBike() {
        super();
    }

    // Polymorphism: Electric bikes charge a 10%
    // eco-surcharge + charging fee for long rentals
    @Override
    public double calculateRentalPrice(int hours) {
        double base    = getPricePerHour() * hours;
        double ecoFee  = base * 0.10;                  // 10% eco-surcharge
        double charging = (hours > 4) ? chargingFeePerHour * hours : 0; // charging fee if > 4hrs
        return base + ecoFee + charging;
    }

    @Override
    public String getBikeType() {
        return "ELECTRIC";
    }

    // Getters & Setters
    public int getBatteryRangeKm()                     { return batteryRangeKm; }
    public void setBatteryRangeKm(int b)               { this.batteryRangeKm = b; }

    public double getChargingFeePerHour()              { return chargingFeePerHour; }
    public void   setChargingFeePerHour(double c)      { this.chargingFeePerHour = c; }

    // Override file string to include electric-specific fields
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + batteryRangeKm + "|" + chargingFeePerHour;
    }
}
