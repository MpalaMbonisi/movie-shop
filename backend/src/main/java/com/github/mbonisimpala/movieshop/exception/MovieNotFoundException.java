package com.github.mbonisimpala.movieshop.exception;

public class MovieNotFoundException extends RuntimeException{

    public MovieNotFoundException (long id){
        super("The movie id '" + id + "' does not exist in our records.");
    }

}
