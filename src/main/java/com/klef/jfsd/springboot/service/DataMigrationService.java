package com.klef.jfsd.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.jfsd.springboot.model.Fundraiser;
import com.klef.jfsd.springboot.model.User;
import com.klef.jfsd.springboot.repository.FundraiserRepository;
import com.klef.jfsd.springboot.repository.UserRepository;

@Service
public class DataMigrationService {
    @Autowired
    private FundraiserRepository fundraiserRepository;

    @Autowired
    private UserRepository userRepository;

    public void assignDefaultUserToFundraisers() {
        Optional<User> defaultUserOptional = userRepository.findByEmail("admin@example.com");

        // Handle the case where the user doesn't exist
        if (defaultUserOptional.isEmpty()) {
            System.out.println("Default user not found. Please ensure 'admin@example.com' exists in the database.");
            return;
        }

        User defaultUser = defaultUserOptional.get(); // Unwrap the Optional to get the actual User object

        List<Fundraiser> fundraisers = fundraiserRepository.findAll();

        for (Fundraiser fundraiser : fundraisers) {
            if (fundraiser.getUser() == null) {
                fundraiser.setUser(defaultUser); // Pass the unwrapped User object
                fundraiserRepository.save(fundraiser); // Save the updated entity
            }
        }
    }

}
