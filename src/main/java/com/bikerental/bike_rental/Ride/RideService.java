package com.bikerental.bike_rental.Ride;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RideService {

    private static final String FILE_PATH = "src/main/resources/data/rides.txt";

    // CREATE - Save a new ride to rides.txt
    public void saveRide(Ride ride) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(ride.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving ride: " + e.getMessage());
        }
    }

    // READ - Get all rides from rides.txt
    public List<String[]> getAllRides() {
        List<String[]> rides = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return rides;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 9) {
                        rides.add(parts);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading rides: " + e.getMessage());
        }
        return rides;
    }

    // READ - Get only DRIVER rides (available for passengers to join)
    public List<String[]> getAvailableDriverRides() {
        List<String[]> available = new ArrayList<>();
        for (String[] ride : getAllRides()) {
            if (ride[1].equals("DRIVER") && ride[8].equals("ACTIVE")) {
                available.add(ride);
            }
        }
        return available;
    }

    // UPDATE - Update ride details (time, location, status)
    public void updateRide(String rideId, String rideDate, String rideTime,
                           String origin, String destination, String status) {
        List<String[]> rides = getAllRides();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] ride : rides) {
                if (ride[0].equals(rideId)) {
                    ride[5] = rideDate;
                    ride[6] = rideTime;
                    ride[3] = origin;
                    ride[4] = destination;
                    ride[8] = status;
                }
                writer.write(String.join("|", ride));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating ride: " + e.getMessage());
        }
    }

    // DELETE - Remove a ride from rides.txt
    public void deleteRide(String rideId) {
        List<String[]> rides = getAllRides();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] ride : rides) {
                if (!ride[0].equals(rideId)) {
                    writer.write(String.join("|", ride));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error deleting ride: " + e.getMessage());
        }
    }

    // Check if ride exists
    public boolean isRideExists(String rideId) {
        for (String[] ride : getAllRides()) {
            if (ride[0].equals(rideId)) {
                return true;
            }
        }
        return false;
    }

    // Generate unique ride ID
    public String generateRideId() {
        List<String[]> rides = getAllRides();
        int count = rides.size() + 1;
        return "RIDE-" + count;
    }
}
