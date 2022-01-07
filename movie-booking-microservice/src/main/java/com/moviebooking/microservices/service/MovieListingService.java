package com.moviebooking.microservices.service;

import com.moviebooking.microservices.model.MovieDetail;
import com.moviebooking.microservices.repository.MoviesListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieListingService {

    @Autowired
    private MoviesListingRepository moviesListingRepository;

    public List<MovieDetail> getAllMoviesList() {
        List<MovieDetail> moviesList = moviesListingRepository.findAll();
        return moviesList;
    }

    public  MovieDetail  getMovieById(Long id) {
        Optional<MovieDetail> movieDetails = moviesListingRepository.findById(id);
        MovieDetail md = null;
        if (movieDetails.isPresent()) {
            md = movieDetails.get();
        }
        return md;
    }
    public List<MovieDetail> getMoviesListBySpecificLocation(String location) {
        MovieDetail md = new MovieDetail();
        md.setTheaterLocation(location);
        Example<MovieDetail> mdExample = Example.of(md);
        List<MovieDetail> moviesList = moviesListingRepository.findAll(mdExample);
        return moviesList;
    }

    public List<MovieDetail> getMoviesListBySpecificDateAndLocation(String location, String date) {
        MovieDetail md = new MovieDetail();
        md.setTheaterLocation(location);
        md.setMovieDate(date);
        Example<MovieDetail> mdExample = Example.of(md);
        List<MovieDetail> moviesList = moviesListingRepository.findAll(mdExample);
        return moviesList;
    }
}
