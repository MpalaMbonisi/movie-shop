package com.github.mbonisimpala.movieshop.exception;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException (long id){
        super("The account id '" + id + "' does not exist in our records.");
    }
}
