package com.bikerental.bike_rental.Payment;

// Encapsulation + Abstraction
public abstract class Payment {

    private String paymentId;
    private String userId;
    private double amount;
    private String paymentDate;
    private String status;

    public Payment() {}

    public Payment(String paymentId, String userId, double amount,
                   String paymentDate, String status) {
        this.paymentId   = paymentId;
        this.userId      = userId;
        this.amount      = amount;
        this.paymentDate = paymentDate;
        this.status      = status;
    }

    // Abstract methods - Polymorphism
    public abstract double calculatePayment();
    public abstract String getPaymentType();

    // Getters
    public String getPaymentId()   { return paymentId; }
    public String getUserId()      { return userId; }
    public double getAmount()      { return amount; }
    public String getPaymentDate() { return paymentDate; }
    public String getStatus()      { return status; }

    // Setters
    public void setPaymentId(String paymentId)     { this.paymentId   = paymentId; }
    public void setUserId(String userId)           { this.userId      = userId; }
    public void setAmount(double amount)           { this.amount      = amount; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }
    public void setStatus(String status)           { this.status      = status; }

    // File Handling Helper 
    public String toFileString() {
        return paymentId + "|" + getPaymentType() + "|" + userId + "|" +
                amount + "|" + paymentDate + "|" + status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='"   + paymentId   + '\'' +
                ", userId='"    + userId      + '\'' +
                ", amount="     + amount      +
                ", date='"      + paymentDate + '\'' +
                ", status='"    + status      + '\'' +
                '}';
    }
}
