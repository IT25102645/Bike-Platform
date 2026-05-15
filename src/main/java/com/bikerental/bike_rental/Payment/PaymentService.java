package com.bikerental.bike_rental.Payment;

import org.springframework.stereotype.Service;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    private static final String FILE_PATH = "payments.txt";

    // CREATE - Save payment to file
    public void savePayment(Payment payment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(payment.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // READ - Get all payments
    public List<String> getAllPayments() {
        List<String> payments = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return payments;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    payments.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return payments;
    }

    // READ - Get payment by ID
    public String getPaymentById(String paymentId) {
        for (String line : getAllPayments()) {
            if (line.startsWith(paymentId + ",") || line.contains("," + paymentId + ",")) {
                return line;
            }
        }
        return null;
    }

    // UPDATE - Update payment status
    public boolean updatePaymentStatus(String paymentId, String newStatus) {
        List<String> payments = getAllPayments();
        boolean found = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (String line : payments) {
                String[] parts = line.split(",");
                if (parts[1].equals(paymentId)) {
                    parts[parts.length - 1] = newStatus;
                    writer.write(String.join(",", parts));
                    found = true;
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return found;
    }

    // DELETE - Remove payment
    public boolean deletePayment(String paymentId) {
        List<String> payments = getAllPayments();
        boolean found = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (String line : payments) {
                if (!line.contains(paymentId)) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return found;
    }


    public String generatePaymentId() {
        return "PAY-" + System.currentTimeMillis();
    }


    public String getTodayDate() {
        return LocalDate.now().toString();
    }
}