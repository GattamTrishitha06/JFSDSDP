package com.klef.jfsd.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klef.jfsd.springboot.model.Donor;

public interface DonorRepository extends JpaRepository<Donor, String> {
    Donor findByEmail(String email);  // Custom query method to find a donor by email
}
