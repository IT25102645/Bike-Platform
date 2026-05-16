package com.bikerental.bike_rental.Payment;

import org.springframework.stereotype.Service;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    // Same path pattern as RentalService and RideService
    private static final String FILE_PATH = "src/main/resources/data/payments.txt";

    // CREATE - Save payment to payments.txt
    public void savePayment(Payment payment) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, true))) {
            writer.write(payment.toFileString());
            writer.newLine();
            System.out.println("Payment saved: " + payment.getPaymentId());
        } catch (IOException e) {
            System.out.println("Error saving payment: " + e.getMessage());
        }
    }

    // READ - Get all payments from payments.txt
    public List<String[]> getAllPayments() {
        List<String[]> payments = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) return payments;

        try (BufferedReader reader = new BufferedReader(
                new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 6) {
                        payments.add(parts);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading payments: " + e.getMessage());
        }
        return payments;
    }

    // READ - Get payment by ID
    public String[] getPaymentById(String paymentId) {
        for (String[] payment : getAllPayments()) {
            if (payment[0].equals(paymentId)) {
                return payment;
            }
        }
        return null;
    }

    // READ - Get payments by userId
    public List<String[]> getPaymentsByUserId(String userId) {
        List<String[]> result = new ArrayList<>();
        for (String[] payment : getAllPayments()) {
            if (payment[2].equals(userId)) {
                result.add(payment);
            }
        }
        return result;
    }

    // UPDATE - Update payment status
    public boolean updatePaymentStatus(String paymentId, String newStatus) {
        List<String[]> payments = getAllPayments();
        boolean updated = false;

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, false))) {
            for (String[] payment : payments) {
                if (payment[0].equals(paymentId)) {
                    payment[5] = newStatus;
                    updated = true;
                }
                writer.write(String.join("|", payment));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating payment: " + e.getMessage());
        }
        return updated;
    }

    // DELETE - Remove payment from file
    public boolean deletePayment(String paymentId) {
        List<String[]> payments = getAllPayments();
        boolean removed = false;

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, false))) {
            for (String[] payment : payments) {
                if (!payment[0].equals(paymentId)) {
                    writer.write(String.join("|", payment));
                    writer.newLine();
                } else {
                    removed = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error deleting payment: " + e.getMessage());
        }
        return removed;
    }

    // Generate unique payment ID - same pattern as RentalService/RideService
    public String generatePaymentId() {
        List<String[]> payments = getAllPayments();
        int count = payments.size() + 1;
        return "PAY-" + count;
    }

    // Get today's date
    public String getTodayDate() {
        return LocalDate.now().toString();
    }
}
