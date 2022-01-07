package com.theateradmin.microservices.controller;


import com.theateradmin.microservices.model.TheaterDetails;
import com.theateradmin.microservices.service.TheaterAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//  Enable theatre partners to onboard their theatres over this
// platform and get access to a bigger customer base while going digital

@RestController
public class TheaterAdminController {

    @Autowired
    private Environment environment;

    @Autowired
    private TheaterAdminService theaterAdminService;

    // Get
    @GetMapping(path = "/theater-admin-microservice/getAllTheaters")
    public List<TheaterDetails> getAllTheaters() {
        return theaterAdminService.getTheaters();
    }

    // Add
    @PostMapping(path = "/theater-admin-microservice/addtheater")
    public void addTheater(@RequestBody TheaterDetails theaterDetails) {
        theaterAdminService.addTheaters(theaterDetails);
    }
    // Delete
    @PostMapping(path = "/theater-admin-microservice/deletetheater")
    public void deleteTheater(@RequestBody TheaterDetails theaterDetails) {
        theaterAdminService.deleteTheaters(theaterDetails);
    }

}
