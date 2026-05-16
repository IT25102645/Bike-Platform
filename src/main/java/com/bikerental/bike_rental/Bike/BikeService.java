package com.bikerental.bike_rental.Bike;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BikeService {

    //  File Path
    private static final String FILE_PATH = "bikes.txt";

    //  CREATE - Add a new bike and save to bikes.txt
    public boolean addBike(Bike bike) {

        // Check duplicate ID before saving
        if (findBikeById(bike.getBikeId()) != null) {
            return false; // already exists
        }

        // Append new bike as a line in bikes.txt
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, true))) {
            writer.write(bike.toFileString());
            writer.newLine();
            System.out.println("Bike added: " + bike.getBikeId());
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //  READ - Get all bikes from bikes.txt
    public List<Bike> getAllBikes() {

        List<Bike> bikes = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Return empty list if file doesn't exist yet
        if (!file.exists()) return bikes;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Bike bike = parseBike(line);    // convert line → Bike object
                    if (bike != null) bikes.add(bike);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bikes;
    }

    //  READ - Find bike by ID
    public Bike findBikeById(String bikeId) {
        for (Bike bike : getAllBikes()) {
            if (bike.getBikeId().equalsIgnoreCase(bikeId)) {
                return bike;
            }
        }
        return null;    // not found
    }

    //  READ - Search bikes by keyword (model, brand, type, description)
    public List<Bike> searchBikes(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllBikes();
        }
        String kw = keyword.toLowerCase().trim();
        return getAllBikes().stream()
                .filter(b -> b.getModel().toLowerCase().contains(kw)
                        || b.getBrand().toLowerCase().contains(kw)
                        || b.getBikeType().toLowerCase().contains(kw)
                        || b.getDescription().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    //  READ - Get only available bikes
    public List<Bike> getAvailableBikes() {
        return getAllBikes().stream()
                .filter(Bike::isAvailable)
                .collect(Collectors.toList());
    }

    //  UPDATE - Modify bike details in bikes.txt
    public boolean updateBike(Bike updatedBike) {

        List<Bike> bikes = getAllBikes();
        boolean updated  = false;

        for (int i = 0; i < bikes.size(); i++) {
            if (bikes.get(i).getBikeId().equalsIgnoreCase(updatedBike.getBikeId())) {
                bikes.set(i, updatedBike);
                updated = true;
                break;
            }
        }

        // Rewrite the whole file with updated list
        if (updated) {
            writeAllBikes(bikes);
            System.out.println("Bike updated: " + updatedBike.getBikeId());
        }

        return updated;
    }

    //  UPDATE - Update only availability
    public boolean updateAvailability(String bikeId, boolean available) {
        Bike bike = findBikeById(bikeId);
        if (bike == null) return false;
        bike.setAvailable(available);
        return updateBike(bike);
    }

    //  DELETE - Remove a bike from bikes.txt
    public boolean deleteBike(String bikeId) {

        List<Bike> bikes   = getAllBikes();
        boolean    removed = bikes.removeIf(
                b -> b.getBikeId().equalsIgnoreCase(bikeId));

        if (removed) {
            writeAllBikes(bikes);
            System.out.println("Bike deleted: " + bikeId);
        }

        return removed;
    }


    // Write full list back to bikes.txt
    private void writeAllBikes(List<Bike> bikes) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, false))) {   // false = overwrite
            for (Bike bike : bikes) {
                writer.write(bike.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bike parseBike(String line) {

        String[] parts = line.split("\\|");

        // Minimum fields check
        if (parts.length < 9) {
            System.out.println("Skipping invalid line: " + line);
            return null;
        }

        String  bikeId      = parts[0].trim();
        String  type        = parts[1].trim();
        String  model       = parts[2].trim();
        String  brand       = parts[3].trim();
        double  price       = Double.parseDouble(parts[4].trim());
        boolean available   = Boolean.parseBoolean(parts[5].trim());
        String  owner       = parts[6].trim();
        String  description = parts[7].trim();

        switch (type) {

            case "ELECTRIC" -> {
                ElectricBike eb = new ElectricBike();
                eb.setBikeId(bikeId);
                eb.setModel(model);
                eb.setBrand(brand);
                eb.setPricePerHour(price);
                eb.setAvailable(available);
                eb.setOwnerUsername(owner);
                eb.setDescription(description);
                if (parts.length > 8) eb.setBatteryRangeKm(Integer.parseInt(parts[8].trim()));
                if (parts.length > 9) eb.setChargingFeePerHour(Double.parseDouble(parts[9].trim()));
                return eb;
            }

            case "MANUAL" -> {
                ManualBike mb = new ManualBike();
                mb.setBikeId(bikeId);
                mb.setModel(model);
                mb.setBrand(brand);
                mb.setPricePerHour(price);
                mb.setAvailable(available);
                mb.setOwnerUsername(owner);
                mb.setDescription(description);
                if (parts.length > 8) mb.setGearCount(Integer.parseInt(parts[8].trim()));
                if (parts.length > 9) mb.setLongRentalDiscountRate(Double.parseDouble(parts[9].trim()));
                return mb;
            }

            default -> {
                System.out.println("Unknown bike type: " + type);
                return null;
            }
        }
    }
}
