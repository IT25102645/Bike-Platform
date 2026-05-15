package com.bikerental.bike_rental.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Payment History Page - READ
    @GetMapping("/history")
    public String paymentHistory(Model model) {
        List<String> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "payment/payment-history";
    }

    // Payment Page - CREATE Rental Payment
    @GetMapping("/rental")
    public String rentalPaymentPage() {
        return "payment/payment";
    }

    @PostMapping("/rental")
    public String processRentalPayment(@RequestParam String userId,
                                       @RequestParam String rentalId,
                                       @RequestParam int durationHours,
                                       @RequestParam double pricePerHour,
                                       Model model) {
        String paymentId = paymentService.generatePaymentId();
        String date = paymentService.getTodayDate();

        RentalPayment payment = new RentalPayment(
                paymentId, userId, rentalId, durationHours, pricePerHour, date, "COMPLETED"
        );
        paymentService.savePayment(payment);

        model.addAttribute("payment", payment);
        model.addAttribute("message", "Payment Successful!");
        return "payment/invoive";
    }

    // Ride Payment - CREATE
    @GetMapping("/ride")
    public String ridePaymentPage() {
        return "payment/payment";
    }

    @PostMapping("/ride")
    public String processRidePayment(@RequestParam String userId,
                                     @RequestParam String rideId,
                                     @RequestParam double distanceKm,
                                     @RequestParam double ratePerKm,
                                     Model model) {
        String paymentId = paymentService.generatePaymentId();
        String date = paymentService.getTodayDate();

        RidePayment payment = new RidePayment(
                paymentId, userId, rideId, distanceKm, ratePerKm, date, "COMPLETED"
        );
        paymentService.savePayment(payment);

        model.addAttribute("payment", payment);
        model.addAttribute("message", "Payment Successful!");
        return "payment/invoive";
    }

    // UPDATE - Payment status
    @PostMapping("/update/{paymentId}")
    public String updateStatus(@PathVariable String paymentId,
                               @RequestParam String status) {
        paymentService.updatePaymentStatus(paymentId, status);
        return "redirect:/payment/history";
    }

    // DELETE - Payment
    @PostMapping("/delete/{paymentId}")
    public String deletePayment(@PathVariable String paymentId) {
        paymentService.deletePayment(paymentId);
        return "redirect:/payment/history";
    }
}