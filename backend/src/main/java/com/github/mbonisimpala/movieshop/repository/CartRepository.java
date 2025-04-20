package com.github.mbonisimpala.movieshop.repository;

import com.github.mbonisimpala.movieshop.entity.CartItem;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CartRepository extends CrudRepository<CartItem, Long> {
    List<CartItem> findByAccountId(Long accountId);
    @Transactional
    void deleteByAccountIdAndMovieId(Long accountId, Long movieId);
}
