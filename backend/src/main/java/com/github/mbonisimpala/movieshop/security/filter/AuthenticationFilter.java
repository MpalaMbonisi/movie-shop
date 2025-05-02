package com.github.mbonisimpala.movieshop.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mbonisimpala.movieshop.entity.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override // request on '/account/authenticate'
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            Account account = new ObjectMapper().readValue(request.getInputStream(), Account.class);
            System.out.println(account.getEmail());
            System.out.println(account.getPassword());
        }catch (IOException e){
            throw new RuntimeException();
        }

        return super.attemptAuthentication(request, response);
    }

}
