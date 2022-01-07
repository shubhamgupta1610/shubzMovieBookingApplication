package com.moviebooking.microservices.controller;


import com.moviebooking.microservices.model.BookingResponse;
import com.moviebooking.microservices.model.BookingRequest;
import com.moviebooking.microservices.model.MovieDetail;
import com.moviebooking.microservices.model.SeatDetails;
import com.moviebooking.microservices.service.MovieBookingService;
import com.moviebooking.microservices.service.MovieListingService;
import com.moviebooking.microservices.service.SeatLockService;
import com.moviebooking.microservices.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class MovieBookingController {

    @Autowired
    private Environment environment;
    @Autowired
    private MovieBookingService movieBookingService;
    @Autowired
    private MovieListingService movieListingService;
    @Autowired
    private SeatLockService seatLockService;

    // Get preferred seats for the day
    @GetMapping(path = "/movie-booking-microservice/showAvailableSeats/{id}")
    public List<SeatDetails> showSeats(@PathVariable Long id) {
        List<SeatDetails> seatDetailsList = movieBookingService.showSeatDetails(id);
        return seatDetailsList;
    }

    // Book movie tickets by selecting a theatre, timing, and preferred seats for the day
    //@CrossOrigin(origins = "http://localhost:8000")
    @PostMapping(path = "/movie-booking-microservice/bookMovie")
    public BookingResponse initiateBooking(@RequestBody BookingRequest bookingRequest) {
        BookingResponse bookingResponse;
        if (seatLockService.hasAnyOtherUserLockedSeats(bookingRequest)) {
            bookingResponse = new BookingResponse();
            bookingResponse.setBookingStatus(Constants.SEAT_AVAILABLE_BUT_LOCKED_BY_ANOTHER_USER_TRY_LATER);
            log.error("Seats are already locked by another user");
            // TODO: throw new Exception();
        } else {
            // lock the seats for the current user and proceed with booking initiation
            seatLockService.lockSeat(bookingRequest);
            List<SeatDetails> seatDetailsList = movieBookingService.getSelectedSeatDetails(bookingRequest.getSeatIds());
            MovieDetail movieDetails = movieListingService.getMovieById(bookingRequest.getMovieId());
            String userId = bookingRequest.getUserId();
            bookingResponse = movieBookingService.initiateBooking(movieDetails , seatDetailsList , userId);
        }

        return bookingResponse;
    }
}
