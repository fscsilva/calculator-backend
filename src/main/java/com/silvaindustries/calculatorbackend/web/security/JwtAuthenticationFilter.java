package com.silvaindustries.calculatorbackend.web.security;

import com.silvaindustries.calculatorbackend.service.v1.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

   private  final JwtService jwtUtilities ;
   private final UserService customerUserDetailsService ;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
                                    throws ServletException, IOException {

        String token = jwtUtilities.getToken(request) ;

        if (token != null && jwtUtilities.validateToken(token))
        {
            String username = jwtUtilities.extractUsername(token);

            UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
            Optional.ofNullable(userDetails)
                .filter(userDetails1 -> userDetails1.getUsername().equals(username))
                .ifPresent(userDetails1 ->  SecurityContextHolder
                    .getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(userDetails.getUsername() ,null , userDetails.getAuthorities())));
            filterChain.doFilter(request,response);
        }

    }

}
