package com.klef.jfsd.springboot.model;

import java.util.Map;

public class DonationRequest {
    private double donationAmount;
    private String paymentMethod;  // e.g., "creditCard", "netBanking"
    private Map<String, String> paymentDetails;

    // Getters and Setters
    public double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(double donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Map<String, String> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(Map<String, String> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
