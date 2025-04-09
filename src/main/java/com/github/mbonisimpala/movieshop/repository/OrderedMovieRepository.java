package com.github.mbonisimpala.movieshop.repository;

import com.github.mbonisimpala.movieshop.entity.OrderedMovie;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderedMovieRepository extends CrudRepository<OrderedMovie, Long> {
    List<OrderedMovie> findByAccountId(Long accountId);
    @Transactional
    void deleteByAccountIdAndMovieId(Long accountId, Long movieId);
}
