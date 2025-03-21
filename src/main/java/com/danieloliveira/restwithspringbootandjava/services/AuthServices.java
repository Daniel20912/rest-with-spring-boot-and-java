package com.danieloliveira.restwithspringbootandjava.services;

import com.danieloliveira.restwithspringbootandjava.VO.v1.security.AccountCredentialsVO;
import com.danieloliveira.restwithspringbootandjava.VO.v1.security.TokenVO;
import com.danieloliveira.restwithspringbootandjava.repositories.UserRepository;
import com.danieloliveira.restwithspringbootandjava.security.Jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository repository;

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsVO data) {
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            var user = repository.findByUsername(username);
            var tokenResponse = new TokenVO();
            if (user != null) tokenResponse = jwtTokenProvider.createAccessToken(username, user.getRoles());
            else throw new UsernameNotFoundException("Username not found");

            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String username, String refreshToken) {
        var user = repository.findByUsername(username);
        var tokenResponse = new TokenVO();
        if (user != null) tokenResponse = jwtTokenProvider.refreshToken(refreshToken);
        else throw new UsernameNotFoundException("Username not found");

        return ResponseEntity.ok(tokenResponse);
    }
}
