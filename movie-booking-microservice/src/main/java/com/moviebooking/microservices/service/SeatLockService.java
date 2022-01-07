package com.moviebooking.microservices.service;

import com.moviebooking.microservices.model.BookingRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeatLockService {
    //  in memory seat lock save
    private Map<Long, List<Long>> seatLockMap = new HashMap<>();

    public  void lockSeat(BookingRequest bookingRequest) {
        seatLockMap.put(bookingRequest.getMovieId(), bookingRequest.getSeatIds());
    }
    public boolean hasAnyOtherUserLockedSeats(BookingRequest bookingRequest) {
        return seatLockMap.containsKey(bookingRequest.getMovieId()) ? true : false;
    }
}
