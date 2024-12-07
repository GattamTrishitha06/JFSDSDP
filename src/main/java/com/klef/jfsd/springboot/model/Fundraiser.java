package com.klef.jfsd.springboot.model;

import java.util.Optional;

import jakarta.persistence.*;

@Entity
@Table(name = "fundraisers")
public class Fundraiser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true) // Temporarily nullable for migration
    private User user; // The fundraiser creator

    private String name;
    private String location;
    private double amount; // The target amount to be raised
    private double raisedAmount = 0; // Default to 0
    private String reason;

    @Lob
    private byte[] image;

    // Default Constructor
    public Fundraiser() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRaisedAmount() {
        return raisedAmount;
    }

    public void setRaisedAmount(double raisedAmount) {
        this.raisedAmount = raisedAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setUser1(User user) {
        this.user = user;
    }

}
