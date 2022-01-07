package com.moviebooking.microservices.service;

import com.moviebooking.microservices.adapter.PaymentServiceAdapter;
import com.moviebooking.microservices.model.BookingResponse;
import com.moviebooking.microservices.model.MovieDetail;
import com.moviebooking.microservices.model.PaymentGatewayResponse;
import com.moviebooking.microservices.model.SeatDetails;
import com.moviebooking.microservices.repository.MoviesBookingRepository;
import com.moviebooking.microservices.repository.SeatDetailsRepository;
import com.moviebooking.microservices.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
public class MovieBookingService {

    @Autowired
    private MoviesBookingRepository moviesBookingRepository;

    @Autowired
    private SeatDetailsRepository seatDetailsRepository ;

    @Autowired
    private PaymentServiceAdapter paymentServiceAdapter;

    public List<SeatDetails> showSeatDetails(Long id) {
        List<MovieDetail> moviesList = moviesBookingRepository.findAllById(id);

        MovieDetail md = null;
        List<SeatDetails> filteredSeatDetailsList=null;
        if (moviesList!=null && moviesList.size()>0) {
            md = moviesList.stream().findFirst().get();

            SeatDetails sd = new SeatDetails();
            sd.setMovieId(md.getId());
            Example<SeatDetails> seatDetailsExample = Example.of(sd);

            // fire query to get seats which have status as 'AVAILABLE'
            List<SeatDetails> seatDetailsList = seatDetailsRepository.findAll(seatDetailsExample);
            log.info("All available seats for movie id {} are {}", id, seatDetailsList);
            filteredSeatDetailsList = seatDetailsList.stream().filter(seatDetails ->
                    seatDetails.getSeatStatus().equalsIgnoreCase(Constants.SEAT_STATUS_AVAILABLE)).collect(Collectors.toList());
        } else {
            log.error("No movies present for the given movie id {}", id);
        }
        return filteredSeatDetailsList;
    }

    public  List<SeatDetails>  getSelectedSeatDetails(List<Long> seatIds) {
        List<SeatDetails> bookedSeatDetails = new ArrayList<>();
        for (Long seatId : seatIds) {
            bookedSeatDetails.addAll(seatDetailsRepository.findAllById(seatId));
        }
        return bookedSeatDetails;
    }

    public BookingResponse initiateBooking(MovieDetail movieDetail , List<SeatDetails> seatDetailsList , String userId) {
        String bookingStatus;

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setMovieDetails(movieDetail);
        bookingResponse.setSeatDetailsList( seatDetailsList);
        bookingResponse.setUserId(userId);

        BigDecimal finalPrice = movieDetail.getMoviePrice().multiply(BigDecimal.valueOf(seatDetailsList.size()));
        bookingResponse.setFinalPrice(finalPrice);
        bookingResponse.setBookingReferenceId(UUID.randomUUID().toString());
        //   perform payment processing
        PaymentGatewayResponse paymentGatewayResponse = paymentServiceAdapter.initiatePayment();
        bookingResponse.setPaymentStatus(paymentGatewayResponse.getPaymentStatus());
        if (paymentGatewayResponse.getPaymentStatus().equalsIgnoreCase(Constants.PAYMENT_STATUS_COMPLETED)) {
            // TODO: post payment: insert MAIN BOOKING RECORD TABLE WITH BOOKING STATUS AS SUCCESS

            bookingStatus = Constants.BOOKING_STATUS_SUCCESS;
            bookingResponse.setBookingStatus(bookingStatus);

            // update SEAT_DETAILS table and mark selected seats as BOOKED so that next time they are not shown to user
            seatDetailsList.stream().forEach(seatDetails -> {
                seatDetails.setSeatStatus(Constants.SEAT_STATUS_BOOKED);
                seatDetailsRepository.save(seatDetails);
            });
        } else {
            bookingStatus = Constants.BOOKING_STATUS_FAILURE;
            bookingResponse.setBookingStatus(bookingStatus);
        }
        return bookingResponse;
    }

}
