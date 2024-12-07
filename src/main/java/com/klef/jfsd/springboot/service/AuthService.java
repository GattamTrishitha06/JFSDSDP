package com.klef.jfsd.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.jfsd.springboot.model.Donor;
import com.klef.jfsd.springboot.repository.DonorRepository;

@Service
public class AuthService {

    @Autowired
    private DonorRepository donorRepository;

    /**
     * Authenticates a user (donor) based on their email and password.
     *
     * @param email    the email of the donor.
     * @param password the password of the donor.
     * @return the role of the donor if authentication is successful, or null otherwise.
     */
    public String authenticateUser(String email, String password) {
        try {
            // Retrieve the donor by email
            Donor donor = donorRepository.findByEmail(email);

            // Validate donor existence and password match
            if (donor != null && donor.getPassword().equals(password)) {
                return donor.getRole(); // Return role (e.g., "Organizer", "Fundraiser")
            } else {
                return null; // Authentication failed
            }
        } catch (Exception e) {
            // Log the error (can be enhanced with a proper logging framework like Log4j)
            System.err.println("Error during authentication: " + e.getMessage());
            return null;
        }
    }
}
