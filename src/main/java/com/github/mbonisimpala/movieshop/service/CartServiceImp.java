package com.github.mbonisimpala.movieshop.service;

import com.github.mbonisimpala.movieshop.entity.Account;
import com.github.mbonisimpala.movieshop.entity.CartItem;
import com.github.mbonisimpala.movieshop.entity.Movie;
import com.github.mbonisimpala.movieshop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    AccountServiceImp accountService;

    @Autowired
    MovieServiceImp movieService;

    @Autowired
    CartRepository cartRepository;

    @Override
    public List<CartItem> getAllCartItems(long accountId) {
        return (List<CartItem>) cartRepository.findAll();
    }

    @Override
    public CartItem saveCartItem(long accountId, long movieId) {
        Account account =  accountService.getAccount(accountId);
        Movie movie = movieService.getMovie(movieId);
        CartItem cartItem = new CartItem(account, movie);
        return cartRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(long accountId, long movieId) {
        cartRepository.deleteByAccountIdAndMovieId(accountId, movieId);
    }
}
