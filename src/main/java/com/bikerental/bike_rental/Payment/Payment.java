package com.bikerental.bike_rental.Payment;

public abstract class Payment {
    private String paymentId;
    private String userId;
    private double amount;
    private String paymentDate;
    private String status;

    public Payment(String paymentId, String userId, double amount, String paymentDate, String status) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    // Getters and Setters
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Polymorphism - abstract method
    public abstract double calculatePayment();

    @Override
    public String toString() {
        return paymentId + "," + userId + "," + amount + "," + paymentDate + "," + status;
    }
}