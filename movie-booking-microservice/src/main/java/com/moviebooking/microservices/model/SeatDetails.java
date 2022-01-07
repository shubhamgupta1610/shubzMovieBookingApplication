package com.moviebooking.microservices.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "seat_details")
public class SeatDetails  implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "movie_id")
    private Long movieId;
    @Column(name = "seat_name")
    private String seatName;
    @Column(name = "seat_status")
    private String seatStatus;

    public SeatDetails() {
    }

}
