package com.github.mbonisimpala.movieshop.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "";
    public static final int TOKEN_EXPIRATION = 7_200_000; // 2 hours
    public static final String BEARER = "Bearer "; // Authorization : "Bear " + Token
    public static final String AUTHORIZATION = "Authorization"; // "Authorization": Bearer Token
    public static final String REGISTER_PATH = "/account/register"; // Public path that client can use to register
}
