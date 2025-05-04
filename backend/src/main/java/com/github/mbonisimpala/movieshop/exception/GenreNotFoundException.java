package com.github.mbonisimpala.movieshop.exception;

public class GenreNotFoundException extends RuntimeException{

    public GenreNotFoundException(long id){
        super("The genre with id '" + id + "' does not exist in our records.");
    }
}
