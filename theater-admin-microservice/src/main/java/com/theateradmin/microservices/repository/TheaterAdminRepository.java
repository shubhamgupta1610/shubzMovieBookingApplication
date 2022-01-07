package com.theateradmin.microservices.repository;

import com.theateradmin.microservices.model.TheaterDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterAdminRepository extends JpaRepository<TheaterDetails, Long> {

    // this method declaration is specific so that jpa picks up
    List<TheaterDetails> findAllById(Long id);


}
