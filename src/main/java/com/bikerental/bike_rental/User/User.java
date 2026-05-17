package com.bikerental.bike_rental.User;


// Encapsulation
public class User {


    private String userId;
    private String name;
    private String email;
    private String password;
    private String phone;

    //Constructors
    public User() {}

    public User(String userId, String name, String email, String password, String phone) {
        this.userId   = userId;
        this.name     = name;
        this.email    = email;
        this.password = password;
        this.phone    = phone;
    }

    // Getters
    public String getUserId()   { return userId; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public String getPhone()    { return phone; }

    // Setters
    public void setUserId(String userId)       { this.userId   = userId; }
    public void setName(String name)           { this.name     = name; }
    public void setEmail(String email)         { this.email    = email; }
    public void setPassword(String password)   { this.password = password; }
    public void setPhone(String phone)         { this.phone    = phone; }

    // Polymorphism
    public String getRole() {
        return "USER";
    }

    //File Handling Helper
    public String toFileString() {
        return userId + "," + name + "," + email + "," + password + "," + phone + "," + getRole();
    }

    // toString

    @Override
    public String toString() {
        return "User{" +
                "userId='"   + userId   + '\'' +
                ", name='"   + name     + '\'' +
                ", email='"  + email    + '\'' +
                ", phone='"  + phone    + '\'' +
                ", role='"   + getRole()+ '\'' +
                '}';
    }
}

