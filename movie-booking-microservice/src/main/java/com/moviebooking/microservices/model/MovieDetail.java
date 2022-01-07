package com.moviebooking.microservices.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
@Data
@Entity
@Table(name = "movie_detail")
public class MovieDetail implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "theater_location")
    private String theaterLocation;
    @Column(name = "theater_name")
    private String theaterName;
    @Column(name = "movie_name")
    private String movieName;
    @Column(name = "movie_date")
    private String movieDate;
    @Column(name = "movie_time")
    private String movieTime;
    @Column(name = "movie_price")
    private BigDecimal moviePrice;

    public MovieDetail() {
    }

}
