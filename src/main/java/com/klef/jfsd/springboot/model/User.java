package com.klef.jfsd.springboot.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String role; // "FUNDRAISER" or "DONOR", for example

    @OneToMany(mappedBy = "user")
    private List<Fundraiser> fundraisers;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Fundraiser> getFundraisers() {
        return fundraisers;
    }

    public void setFundraisers(List<Fundraiser> fundraisers) {
        this.fundraisers = fundraisers;
    }
}
