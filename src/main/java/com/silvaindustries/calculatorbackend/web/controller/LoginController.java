package com.silvaindustries.calculatorbackend.web.controller;

import com.silvaindustries.calculatorbackend.service.dto.business.UserDTO;
import com.silvaindustries.calculatorbackend.service.v1.UserService;
import com.silvaindustries.calculatorbackend.web.api.LoginAPI;
import com.silvaindustries.calculatorbackend.web.dto.request.AuthRequest;
import com.silvaindustries.calculatorbackend.web.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController implements LoginAPI {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public ResponseEntity<UserDTO> login (@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            var user = (UserDTO) userService.loadUserByUsername(authRequest.getUsername());
            var authToken = jwtService.generateToken(user);
            return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .body(user);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @Override
    public ResponseEntity<String> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok("redirect:/home");
    }

}
