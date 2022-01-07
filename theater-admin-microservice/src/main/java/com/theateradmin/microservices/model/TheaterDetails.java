package com.theateradmin.microservices.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "theater_details")
public class TheaterDetails implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "theater_location")
    private String theaterLocation;
    @Column(name = "theater_name")
    private String theaterName;

}
