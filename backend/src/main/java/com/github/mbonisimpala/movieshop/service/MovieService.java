package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.Movie;

import java.util.List;

public interface MovieService {

    Movie getMovie(long id);
    List<Movie> getAllMovies();
}
