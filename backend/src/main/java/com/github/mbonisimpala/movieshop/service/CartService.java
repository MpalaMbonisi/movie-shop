package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.CartItem;
import com.github.mbonisimpala.movieshop.entity.Movie;

import java.util.List;

public interface CartService {
    List<CartItem> getAllCartItems(long accountId);
    void deleteCartItem(long accountId, long movieId);
    CartItem saveCartItem(long accountId, long movieId);
}
