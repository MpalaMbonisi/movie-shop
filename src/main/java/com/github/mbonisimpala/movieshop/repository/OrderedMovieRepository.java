package com.github.mbonisimpala.movieshop.repository;

import com.github.mbonisimpala.movieshop.entity.OrderedMovie;
import org.springframework.data.repository.CrudRepository;

public interface OrderedMovieRepository extends CrudRepository<OrderedMovie, Long> {
}
