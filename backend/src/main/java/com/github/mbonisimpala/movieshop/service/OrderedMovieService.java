package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.OrderedMovie;

import java.util.List;

public interface OrderedMovieService {
    List<OrderedMovie> getAllOrderedMovies(long accountId);
    OrderedMovie saveOrderedMovie(long accountId, long movieId);
}
