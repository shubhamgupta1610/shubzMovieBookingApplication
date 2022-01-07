package com.moviebooking.microservices.repository;

import com.moviebooking.microservices.model.SeatDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatDetailsRepository extends JpaRepository<SeatDetails, Long> {
     List<SeatDetails> findAllById(Long seatId);

}
