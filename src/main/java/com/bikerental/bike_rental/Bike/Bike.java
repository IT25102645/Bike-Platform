package com.bikerental.bike_rental.Bike;

public abstract class Bike {

    private String bikeId;
    private String model;
    private String brand;
    private double pricePerHour;
    private boolean available;
    private String ownerUsername;
    private String description;


    public Bike(String bikeId, String model, String brand,
                double pricePerHour, boolean available,
                String ownerUsername, String description) {
        this.bikeId      = bikeId;
        this.model       = model;
        this.brand       = brand;
        this.pricePerHour = pricePerHour;
        this.available   = available;
        this.ownerUsername = ownerUsername;
        this.description = description;
    }

    public Bike() {}

    public abstract double calculateRentalPrice(int hours);

    public abstract String getBikeType();

    public String getBikeId()                   { return bikeId; }
    public void   setBikeId(String bikeId)      { this.bikeId = bikeId; }

    public String getModel()                    { return model; }
    public void   setModel(String model)        { this.model = model; }

    public String getBrand()                    { return brand; }
    public void   setBrand(String brand)        { this.brand = brand; }

    public double getPricePerHour()             { return pricePerHour; }
    public void   setPricePerHour(double p)     { this.pricePerHour = p; }

    public boolean isAvailable()                { return available; }
    public void    setAvailable(boolean a)      { this.available = a; }

    public String getOwnerUsername()            { return ownerUsername; }
    public void   setOwnerUsername(String o)    { this.ownerUsername = o; }

    public String getDescription()              { return description; }
    public void   setDescription(String d)      { this.description = d; }

    public String toFileString() {
        return bikeId + "|" + getBikeType() + "|" + model + "|" + brand + "|"
                + pricePerHour + "|" + available + "|" + ownerUsername + "|" + description;
    }

    @Override
    public String toString() {
        return "Bike{id='" + bikeId + "', model='" + model + "', brand='" + brand +
                "', price=" + pricePerHour + ", available=" + available + "}";
    }
}
