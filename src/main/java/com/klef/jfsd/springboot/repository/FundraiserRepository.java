package com.klef.jfsd.springboot.repository;

import com.klef.jfsd.springboot.model.Fundraiser;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FundraiserRepository extends JpaRepository<Fundraiser, Integer> {
	 @Query("SELECT f FROM Fundraiser f WHERE f.raisedAmount < f.amount")
	    List<Fundraiser> findFundraisersNeedingDonations();

	 List<Fundraiser> findByUserId(int userId);
	 List<Fundraiser> findAll();

	    // Fetch fundraisers for a specific user (optional)
	    List<Fundraiser> findByUserEmail(String userEmail);
}
