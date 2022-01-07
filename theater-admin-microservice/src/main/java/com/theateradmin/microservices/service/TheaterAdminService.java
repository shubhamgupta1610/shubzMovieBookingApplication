package com.theateradmin.microservices.service;

import com.theateradmin.microservices.model.TheaterDetails;
import com.theateradmin.microservices.repository.TheaterAdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class TheaterAdminService {

    @Autowired
    private TheaterAdminRepository theaterAdminRepository;
    public List<TheaterDetails> getTheaters() {
        return theaterAdminRepository.findAll();
    }
    public  void  addTheaters(TheaterDetails theaterDetails) {
        theaterAdminRepository.save(theaterDetails);
    }
    public  void  deleteTheaters(TheaterDetails theaterDetails) {
        theaterAdminRepository.delete(theaterDetails);
    }

}
