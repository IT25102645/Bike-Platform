package com.bikerental.bike_rental.User;


import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    //  File Path

    private static final String FILE_PATH = "src/main/resources/static/user.txt";


    //  CREATE - Register a new user and save to users.txt

    public void registerUser(User user) throws IOException {

        // Check duplicate email before saving
        if (findUserByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("A user with this email already exists.");
        }

        // Append new user as a line in users.txt
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, true))) {
            writer.write(user.toFileString());
            writer.newLine();
            System.out.println("User registered: " + user.getName());
        }
    }



    //  READ - Get all users from users.txt

    public List<User> getAllUsers() throws IOException {

        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Return empty list if file doesn't exist yet
        if (!file.exists()) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    User user = parseUser(line);    // convert line → User object
                    if (user != null) {
                        users.add(user);
                    }
                }
            }
        }
        return users;
    }

    //  READ - Find user by Email

    public User findUserByEmail(String email) throws IOException {
        for (User user : getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;    // not found
    }

    //  READ - Find user by ID

    public User findUserById(String userId) throws IOException {
        for (User user : getAllUsers()) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;    // not found
    }



    //  UPDATE - Update phone number or password by userId

    public boolean updateUser(String userId, String newPhone,
                              String newPassword) throws IOException {

        List<User> users = getAllUsers();
        boolean updated  = false;

        for (User user : users) {
            if (user.getUserId().equals(userId)) {

                // Only update if a new value is provided
                if (newPhone != null && !newPhone.isEmpty()) {
                    user.setPhone(newPhone);
                }
                if (newPassword != null && !newPassword.isEmpty()) {
                    user.setPassword(newPassword);
                }

                updated = true;
                break;
            }
        }

        // Rewrite the whole file with updated list
        if (updated) {
            writeAllUsers(users);
            System.out.println("User updated: " + userId);
        }

        return updated;
    }

    // UPDATE - Update extra fields for RiderUser

    public boolean updateRiderLicense(String userId,
                                      String newLicense) throws IOException {

        List<User> users = getAllUsers();
        boolean updated  = false;

        for (User user : users) {
            if (user.getUserId().equals(userId) && user instanceof RiderUser) {
                RiderUser rider = (RiderUser) user;
                rider.setLicenseNumber(newLicense);
                updated = true;
                break;
            }
        }

        if (updated) writeAllUsers(users);
        return updated;
    }

    //  UPDATE - Update extra fields for BikeOwner

    public boolean updateOwnerBankAccount(String userId,
                                          String newBankAccount) throws IOException {

        List<User> users = getAllUsers();
        boolean updated  = false;

        for (User user : users) {
            if (user.getUserId().equals(userId) && user instanceof BikeOwner) {
                BikeOwner owner = (BikeOwner) user;
                owner.setBankAccount(newBankAccount);
                updated = true;
                break;
            }
        }

        if (updated) writeAllUsers(users);
        return updated;
    }



    //  DELETE - Remove a user by userId

    public boolean deleteUser(String userId) throws IOException {

        List<User> users   = getAllUsers();
        boolean    removed = users.removeIf(u -> u.getUserId().equals(userId));

        if (removed) {
            writeAllUsers(users);
            System.out.println("User deleted: " + userId);
        }

        return removed;
    }


    //  LOGIN - Validate user credentials

    public User login(String email, String password) throws IOException {

        User user = findUserByEmail(email);

        //Return null is user not found
        if (user == null) {
            System.out.println("Login failed: email not found.");
            return null;
        }

        // Polymorphism

        if (user instanceof RiderUser rider) {
            return rider.validateLogin(email, password) ? rider : null;

        } else if (user instanceof BikeOwner owner) {
            return owner.validateLogin(email, password) ? owner : null;

        } else {
            // Plain User login
            return (user.getEmail().equals(email)
                    && user.getPassword().equals(password)) ? user : null;
        }
    }


    //  HELPERS
    // Write full list back to users.txt
    private void writeAllUsers(List<User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, false))) {   // false = overwrite
            for (User user : users) {
                writer.write(user.toFileString());
                writer.newLine();
            }
        }
    }

    // Parse one CSV line from users.txt to User object

    private User parseUser(String line) {

        String[] parts = line.split(",");

        // Minimum fields: userId, name, email, password, phone, role = 6
        if (parts.length < 6) {
            System.out.println("Skipping invalid line: " + line);
            return null;
        }

        String userId   = parts[0].trim();
        String name     = parts[1].trim();
        String email    = parts[2].trim();
        String password = parts[3].trim();
        String phone    = parts[4].trim();
        String role     = parts[5].trim();

        switch (role) {

            case "RIDER" -> {
                RiderUser rider = new RiderUser();
                rider.setUserId(userId);
                rider.setName(name);
                rider.setEmail(email);
                rider.setPassword(password);
                rider.setPhone(phone);
                if (parts.length > 6) rider.setLicenseNumber(parts[6].trim());
                if (parts.length > 7) rider.setTotalRides(Integer.parseInt(parts[7].trim()));
                return rider;
            }

            case "OWNER" -> {
                BikeOwner owner = new BikeOwner();
                owner.setUserId(userId);
                owner.setName(name);
                owner.setEmail(email);
                owner.setPassword(password);
                owner.setPhone(phone);
                if (parts.length > 6) owner.setBankAccount(parts[6].trim());
                if (parts.length > 7) owner.setBusinessName(parts[7].trim());
                if (parts.length > 8) owner.setTotalBikesListed(Integer.parseInt(parts[8].trim()));
                return owner;
            }

            default -> {
                User user = new User();
                user.setUserId(userId);
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                user.setPhone(phone);
                return user;
            }
        }
    }
}
