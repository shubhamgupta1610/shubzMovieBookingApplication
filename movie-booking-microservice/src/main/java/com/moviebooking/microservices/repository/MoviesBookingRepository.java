package com.moviebooking.microservices.repository;

import com.moviebooking.microservices.model.MovieDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesBookingRepository extends JpaRepository<MovieDetail, Long> {

    // this method declaration is specific so that jpa picks up
    List<MovieDetail> findAllById(Long id);


}
