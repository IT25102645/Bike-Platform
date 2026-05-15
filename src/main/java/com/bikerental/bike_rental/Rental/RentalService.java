package com.bikerental.bike_rental.Rental;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentalService {
    private static final String FILE_PATH = "src/main/resources/data/rentals.txt";

    public void saveRental(HourlyRental rental) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(rental.toString() + "," + rental.getHours() + "," + rental.getRatePerHour());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> getAllRentals() {
        List<String[]> rentals = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rentals.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rentals;
    }

    public void deleteRental(String rentalId) {
        List<String[]> rentals = getAllRentals();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] rental : rentals) {
                if (!rental[0].equals(rentalId)) {
                    writer.write(String.join(",", rental));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateRental(String rentalId, String endDate, String status) {
        List<String[]> rentals = getAllRentals();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] rental : rentals) {
                if (rental[0].equals(rentalId)) {
                    rental[4] = endDate;
                    rental[5] = status;
                }
                writer.write(String.join(",", rental));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generateRentalId() {
        return "RENT" + System.currentTimeMillis();
    }
}