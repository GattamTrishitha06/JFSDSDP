package com.klef.jfsd.springboot.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.jfsd.springboot.model.Donor;
import com.klef.jfsd.springboot.repository.DonorRepository;

@RestController
@RequestMapping("/donor/api")
public class DonorController {

    @Autowired
    private DonorRepository donorRepository;

    // Register a new donor
    @PostMapping("/donorregister")
    public ResponseEntity<String> registerDonor(@RequestBody Donor donor) {
        try {
            // Log the incoming donor object
            System.out.println("Received donor: " + donor);

            // Check if donor with the same email already exists
            if (donorRepository.existsById(donor.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                     .body("Donor with this email already exists.");
            }

            // Save the new donor
            donorRepository.save(donor);
            return ResponseEntity.ok("Donor registered successfully");

        } catch (Exception e) {
            // Log the error for better debugging
            System.err.println("Error during registration: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error occurred: " + e.getMessage());
        }
    }
    @GetMapping("/alldonors")
    public ResponseEntity<List<Donor>> getAllDonors() {
        try {
            List<Donor> donors = donorRepository.findAll();
            return ResponseEntity.ok(donors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Collections.emptyList());
        }
    }


    // Authenticate user (for login)
    @PostMapping("/donorlogin")
    public ResponseEntity<String> loginUser(@RequestBody Donor donor) {
        Donor existingDonor = donorRepository.findByEmail(donor.getEmail());

        // Validate donor existence and password
        if (existingDonor != null && existingDonor.getPassword().equals(donor.getPassword())) {
            // Return email, name, and role as JSON
            return ResponseEntity.ok("{\"email\": \"" + existingDonor.getEmail() + 
                                      "\", \"name\": \"" + existingDonor.getName() + 
                                      "\", \"role\": \"" + existingDonor.getRole() + "\"}");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }


}
