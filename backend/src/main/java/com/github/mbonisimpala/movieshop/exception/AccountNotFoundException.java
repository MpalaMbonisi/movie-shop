package com.github.mbonisimpala.movieshop.exception;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException (String email){
        super("The account with email or id '" + email + "' does not exist in our records.");
    }
}
