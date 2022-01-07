package com.moviebooking.microservices.model;

import lombok.Data;

import java.util.List;
@Data
public class BookingRequest {

    private Long movieId;
    private List<Long> seatIds;
    private String userId;

}
