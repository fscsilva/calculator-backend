package com.silvaindustries.calculatorbackend.web.api;

import com.silvaindustries.calculatorbackend.service.dto.business.UserDTO;
import com.silvaindustries.calculatorbackend.web.dto.request.AuthRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface LoginAPI extends Version{

    @Operation(summary = "Login with username and password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logged in", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to login"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<UserDTO> login(@RequestBody AuthRequest authRequest);

    @Operation(summary = "Logout with username and password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logged out", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to logout"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<String> logout() ;

}
