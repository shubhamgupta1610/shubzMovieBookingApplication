package com.moviebooking.microservices.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class BookingResponse {

    private String userId;
    private MovieDetail movieDetails;
    private List<SeatDetails> seatDetailsList;
    private BigDecimal finalPrice;
    private String bookingStatus;
    private String bookingReferenceId;
    private String paymentStatus;
    public BookingResponse() {
    }

}
