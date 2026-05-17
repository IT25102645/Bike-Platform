package com.bikerental.bike_rental.User;

// Inheritance
public class BikeOwner extends User {


    private String bankAccount;
    private String businessName;
    private int totalBikesListed;


    // Default constructor
    public BikeOwner() {
        super();
    }

    // Parameterized constructors
    public BikeOwner(String userId, String name, String email,
                     String password, String phone, String bankAccount) {
        super(userId, name, email, password, phone); // calls User constructor
        this.bankAccount      = bankAccount;
        this.businessName     = "";
        this.totalBikesListed = 0;
    }


    public BikeOwner(String userId, String name, String email, String password,
                     String phone, String bankAccount, String businessName) {
        super(userId, name, email, password, phone);
        this.bankAccount      = bankAccount;
        this.businessName     = businessName;
        this.totalBikesListed = 0;
    }

    // Getters

    public String getBankAccount()    { return bankAccount; }
    public String getBusinessName()   { return businessName; }
    public int getTotalBikesListed()  { return totalBikesListed; }

    // Setters

    public void setBankAccount(String bankAccount)       { this.bankAccount      = bankAccount; }
    public void setBusinessName(String businessName)     { this.businessName     = businessName; }
    public void setTotalBikesListed(int totalBikesListed){ this.totalBikesListed = totalBikesListed; }

    //Polymorphism

    // Overrides User's getRole()
    @Override
    public String getRole() {
        return "OWNER";
    }

    // Different login validation compared to RiderUser
    public boolean validateLogin(String email, String password) {
        return getEmail().equals(email)
                && getPassword().equals(password)
                && bankAccount != null
                && !bankAccount.isEmpty();
    }


    // Method Overloading

    // validates with bank account as extra check
    public boolean validateLogin(String email, String password, String bankAccount) {
        return getEmail().equals(email)
                && getPassword().equals(password)
                && this.bankAccount.equals(bankAccount);
    }


    // Business Methods

    // Increase bike count when owner adds a new bike
    public void addBike() {
        this.totalBikesListed++;
    }

    // Decrease bike count when owner removes a bike
    public void removeBike() {
        if (this.totalBikesListed > 0) {
            this.totalBikesListed--;
        }
    }

    // Check if owner has any bikes listed
    public boolean hasBikesListed() {
        return this.totalBikesListed > 0;
    }

    // File Handling Helper

    // Extends parent toFileString() to include owner-specific fields
    @Override
    public String toFileString() {
        return super.toFileString() + "," + bankAccount + ","
                + businessName + "," + totalBikesListed;
    }

    // toString

    @Override
    public String toString() {
        return "BikeOwner{" +
                "userId='"           + getUserId()       + '\'' +
                ", name='"           + getName()         + '\'' +
                ", email='"          + getEmail()        + '\'' +
                ", phone='"          + getPhone()        + '\'' +
                ", role='"           + getRole()         + '\'' +
                ", bankAccount='"    + bankAccount       + '\'' +
                ", businessName='"   + businessName      + '\'' +
                ", totalBikesListed="+ totalBikesListed  +
                '}';
    }
}
