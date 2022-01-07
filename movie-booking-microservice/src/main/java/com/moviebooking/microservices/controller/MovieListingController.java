package com.moviebooking.microservices.controller;


import com.moviebooking.microservices.model.MovieDetail;
import com.moviebooking.microservices.service.MovieListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieListingController {

    @Autowired
    private Environment environment;

    @Autowired
    private MovieListingService moviesListingService;

    @GetMapping(path = "/movie-booking-microservice/moviesListing")
    public List<MovieDetail> getMoviesListing() {
        List<MovieDetail> moviesList = moviesListingService.getAllMoviesList();
        return moviesList;

    }

    // Browse theatres currently running the show (movie selected) in the town, including show timing by a chosen date
    @GetMapping(path = "/movie-booking-microservice/moviesListingByCriteria/{location}/{date}")
    public List<MovieDetail> getMoviesListingByLocationAndDate(@PathVariable String location, @PathVariable String date) {
        List<MovieDetail> moviesList =
                moviesListingService.getMoviesListBySpecificDateAndLocation(location, date);
        return moviesList;
    }


}
