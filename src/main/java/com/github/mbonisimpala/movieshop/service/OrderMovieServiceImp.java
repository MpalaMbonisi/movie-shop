package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.Account;
import com.github.mbonisimpala.movieshop.entity.Movie;
import com.github.mbonisimpala.movieshop.entity.OrderedMovie;
import com.github.mbonisimpala.movieshop.repository.OrderedMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMovieServiceImp implements OrderedMovieService {

    @Autowired
    OrderedMovieRepository orderedMovieRepository;

    @Autowired
    AccountServiceImp accountService;

    @Autowired
    MovieServiceImp movieService;

    @Override
    public OrderedMovie saveOrderedMovie(OrderedMovie orderedMovie, long accountId, long movieId) {
        Account account = accountService.getAccount(accountId);
        Movie movie = movieService.getMovie(movieId);
        orderedMovie.setAccount(account);
        orderedMovie.setMovie(movie);
        return orderedMovieRepository.save(orderedMovie);
    }

    @Override
    public List<OrderedMovie> getAllOrderedMovies(long accountId) {
        return (List<OrderedMovie>) orderedMovieRepository.findAll();
    }
}
