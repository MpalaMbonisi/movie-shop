package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.Movie;
import com.github.mbonisimpala.movieshop.exception.MovieNotFoundException;
import com.github.mbonisimpala.movieshop.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImp implements MovieService{

    @Autowired
    MovieRepository movieRepository;

    @Override
    public Movie getMovie(long id) {
        return unwrapMovie(movieRepository.findById(id), id);
    }


    @Override
    public List<Movie> getAllMovies() {
        return (List<Movie>) movieRepository.findAll();
    }

    @Override
    public List<Movie> getAllMoviesByGenre(long genreId) {
        return movieRepository.findByGenreId(genreId);
    }

    static Movie unwrapMovie(Optional<Movie> entity, long id){
        if (entity.isPresent()) return entity.get();
        else throw new MovieNotFoundException(id);
    }
}
