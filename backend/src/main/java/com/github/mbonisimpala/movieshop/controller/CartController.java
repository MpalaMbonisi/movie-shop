package com.github.mbonisimpala.movieshop.controller;

import com.github.mbonisimpala.movieshop.entity.CartItem;
import com.github.mbonisimpala.movieshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<CartItem>> getAllCartItems(@PathVariable Long accountId){
        return new ResponseEntity<>(cartService.getAllCartItems(accountId), HttpStatus.OK);
    }

    @PostMapping("/account/{accountId}/movie/{movieId}")
    public ResponseEntity<CartItem> saveCartItem(@PathVariable Long accountId, @PathVariable Long movieId){
        return new ResponseEntity<>(cartService.saveCartItem(accountId, movieId), HttpStatus.CREATED);
    }

    @DeleteMapping("/account/{accountId}/movie/{movieId}")
    public ResponseEntity<HttpStatus> deleteCartItem(@PathVariable Long accountId, @PathVariable Long movieId){
        cartService.deleteCartItem(accountId, movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/account/{accountId}/clear")
    public ResponseEntity<HttpStatus> deleteAllCartItems(@PathVariable Long accountId){
        cartService.deleteAllCartItems(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
