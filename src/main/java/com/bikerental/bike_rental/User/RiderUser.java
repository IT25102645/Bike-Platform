package com.bikerental.bike_rental.User;


// Inheritance
public class RiderUser extends User {

    private String licenseNumber;
    private int totalRides;


    // Default constructor
    public RiderUser() {
        super();
    }

    // Parameterized constructor
    public RiderUser(String userId, String name, String email,
                     String password, String phone, String licenseNumber) {
        super(userId, name, email, password, phone); // calls User constructor
        this.licenseNumber = licenseNumber;
        this.totalRides    = 0;
    }

    // Getters

    public String getLicenseNumber() { return licenseNumber; }
    public int getTotalRides()       { return totalRides; }

    // Setters
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public void setTotalRides(int totalRides)          { this.totalRides    = totalRides; }


    // Polymorphism

    // Overrides User's getRole()
    @Override
    public String getRole() {
        return "RIDER";
    }

    // Different login validation logic compared to BikeOwner
    public boolean validateLogin(String email, String password) {
        return getEmail().equals(email)
                && getPassword().equals(password)
                && licenseNumber != null
                && !licenseNumber.isEmpty();
    }


    // Method Overloading

    public boolean validateLogin(String email, String password, String license) {
        return getEmail().equals(email)
                && getPassword().equals(password)
                && this.licenseNumber.equals(license);
    }

    // File Handling Helper

    // Extends parent toFileString() to include rider-specific fields
    @Override
    public String toFileString() {
        return super.toFileString() + "," + licenseNumber + "," + totalRides;
    }

    // toString

    @Override
    public String toString() {
        return "RiderUser{" +
                "userId='"        + getUserId()      + '\'' +
                ", name='"        + getName()        + '\'' +
                ", email='"       + getEmail()       + '\'' +
                ", phone='"       + getPhone()       + '\'' +
                ", role='"        + getRole()        + '\'' +
                ", licenseNumber='"+ licenseNumber   + '\'' +
                ", totalRides="   + totalRides       +
                '}';
    }
}

