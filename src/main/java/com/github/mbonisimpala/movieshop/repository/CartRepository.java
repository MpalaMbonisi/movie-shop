package com.github.mbonisimpala.movieshop.repository;

import com.github.mbonisimpala.movieshop.entity.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
}
