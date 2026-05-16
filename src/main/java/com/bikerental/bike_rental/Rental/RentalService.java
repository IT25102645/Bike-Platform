package com.bikerental.bike_rental.Rental;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentalService {

    private static final String FILE_PATH = "src/main/resources/data/rentals.txt";

    public void saveRental(Rental rental) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(rental.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving rental: " + e.getMessage());
        }
    }

    public List<String[]> getAllRentals() {
        List<String[]> rentals = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return rentals;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 7) {
                        rentals.add(parts);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading rentals: " + e.getMessage());
        }
        return rentals;
    }

    public void deleteRental(String rentalId) {
        List<String[]> rentals = getAllRentals();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] rental : rentals) {
                if (!rental[0].equals(rentalId)) {
                    writer.write(String.join("|", rental));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error deleting rental: " + e.getMessage());
        }
    }

    public void updateRental(String rentalId, String endDate, String status) {
        List<String[]> rentals = getAllRentals();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] rental : rentals) {
                if (rental[0].equals(rentalId)) {
                    rental[5] = endDate;
                    rental[6] = status;
                }
                writer.write(String.join("|", rental));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating rental: " + e.getMessage());
        }
    }

    public boolean isRentalExists(String rentalId) {
        List<String[]> rentals = getAllRentals();
        for (String[] rental : rentals) {
            if (rental[0].equals(rentalId)) {
                return true;
            }
        }
        return false;
    }

    public String generateRentalId() {
        List<String[]> rentals = getAllRentals();
        int count = rentals.size() + 1;
        return "RENT-" + count;
    }
}