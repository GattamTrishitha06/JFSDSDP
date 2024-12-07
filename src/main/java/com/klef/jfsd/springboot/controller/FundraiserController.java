package com.klef.jfsd.springboot.controller;

import com.klef.jfsd.springboot.model.DonationRequest;
import com.klef.jfsd.springboot.model.Fundraiser;
import com.klef.jfsd.springboot.repository.FundraiserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/fundraisers")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from your React frontend
public class FundraiserController {

    @Autowired
    private FundraiserRepository fundraiserRepository;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> createFundraiser(
            @RequestParam("name") String name,
            @RequestParam("location") String location,
            @RequestParam("amount") double amount,
            @RequestParam("reason") String reason,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            Fundraiser fundraiser = new Fundraiser();
            fundraiser.setName(name);
            fundraiser.setLocation(location);
            fundraiser.setAmount(amount);
            fundraiser.setReason(reason);
            fundraiser.setImage(imageFile.getBytes());

            fundraiserRepository.save(fundraiser);
            return ResponseEntity.ok("Fundraiser created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error processing image file.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("An unexpected error occurred.");
        }
    }


   

    // Fetch all fundraisers
    @GetMapping
    public ResponseEntity<List<Fundraiser>> getAllFundraisers() {
        try {
            List<Fundraiser> fundraisers = fundraiserRepository.findAll();
            return ResponseEntity.ok(fundraisers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<Fundraiser>> getFundraisersByUser(@RequestParam String email) {
        try {
            List<Fundraiser> fundraisers = fundraiserRepository.findByUserEmail(email);
            return ResponseEntity.ok(fundraisers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }







    @PostMapping("/donate/{id}")
    public ResponseEntity<String> donate(@PathVariable int id, @RequestBody DonationRequest donationRequest) {
        Fundraiser fundraiser = fundraiserRepository.findById(id).orElse(null);

        if (fundraiser == null) {
            return ResponseEntity.notFound().build();
        }

        double donationAmount = donationRequest.getDonationAmount();

        double newRaisedAmount = fundraiser.getRaisedAmount() + donationAmount;
        if (newRaisedAmount > fundraiser.getAmount()) {
            return ResponseEntity.badRequest().body("Donation exceeds target amount.");
        }

        fundraiser.setRaisedAmount(newRaisedAmount);
        fundraiserRepository.save(fundraiser);

        return ResponseEntity.ok("Donation successful");
    }
}
