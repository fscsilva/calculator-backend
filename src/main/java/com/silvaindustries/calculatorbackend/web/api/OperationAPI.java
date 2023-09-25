package com.silvaindustries.calculatorbackend.web.api;

import com.silvaindustries.calculatorbackend.service.dto.business.OperationDTO;
import com.silvaindustries.calculatorbackend.service.dto.business.RecordDTO;
import com.silvaindustries.calculatorbackend.service.dto.business.UserDTO;
import com.silvaindustries.calculatorbackend.web.dto.RandomStrFormat;
import com.silvaindustries.calculatorbackend.web.dto.RandomStrRND;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public interface OperationAPI extends Version {
    String OPERATION_PATH_V1 = USER_ROLE_PATH + "/operation";
    String OPERATION_PATH_V2 = USER_ROLE_PATH_V2 + "/operation";

    @Operation(summary = "Create an Operation via addition")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operation created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OperationDTO.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error creating the Operation"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @PostMapping(value = OPERATION_PATH_V1 + "/addition/{quantity}", headers = HttpHeaders.AUTHORIZATION)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<RecordDTO> addition( @PathVariable @Min(value = 0 ,message = "Minimum value of quantity is 0") String quantity);

    @Operation(summary = "Create an Operation via subtraction")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operation created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OperationDTO.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error creating the Operation"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @PostMapping(value = OPERATION_PATH_V1 +"/subtraction/{quantity}", headers = HttpHeaders.AUTHORIZATION)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<RecordDTO> subtraction( @PathVariable @Min(value = 0 ,message = "Minimum value of quantity is 0") String quantity);

    @Operation(summary = "Create an Operation via multiplication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operation created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OperationDTO.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error creating the Operation"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @PostMapping(value = OPERATION_PATH_V1 +"/multiplication/{quantity}", headers = HttpHeaders.AUTHORIZATION)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<RecordDTO> multiplication( @PathVariable @Min(value = 0 ,message = "Minimum value of quantity is 0") String quantity);

    @Operation(summary = "Create an Operation via division")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operation created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OperationDTO.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error creating the Operation"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @PostMapping(value = OPERATION_PATH_V1 +"/division/{quantity}", headers = HttpHeaders.AUTHORIZATION)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<RecordDTO> division( @PathVariable @Min(value = 0 ,message = "Minimum value of quantity is 0") String quantity);

    @Operation(summary = "Create an Operation via square root")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operation created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OperationDTO.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error creating the Operation"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @PostMapping(value = OPERATION_PATH_V1 +"/square-root", headers = HttpHeaders.AUTHORIZATION)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<RecordDTO> squareRoot();

    @Operation(summary = "Create an Operation via random string")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operation created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OperationDTO.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error creating the Operation"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @GetMapping(value = OPERATION_PATH_V1 +"/random-string/num={num}&len={len}&digits={digits}&upperAlpha={upperAlpha}&lowerAlpha={lowerAlpha}&" +
        "unique={unique}&format={format}&rnd={rnd}", headers = HttpHeaders.AUTHORIZATION)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<RecordDTO> randomString(
        @PathVariable @Size(min = 1, max = 10000, message = "The num value must be an integer in the [1,10000] interval") Integer num,
        @PathVariable @Size(min = 1, max = 32, message = "The len value must be an integer in the [1,32] interval") Integer len,
        @PathVariable Boolean digits,
        @PathVariable Boolean upperAlpha,
        @PathVariable Boolean lowerAlpha,
        @PathVariable Boolean unique,
        @PathVariable RandomStrFormat format,
        @PathVariable RandomStrRND rnd);

    @Operation(summary = "Create an Operation via random string")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operation created", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OperationDTO.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error creating the Operation"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @GetMapping(value = OPERATION_PATH_V2 +"/random-string/num={num}&len={len}&format={format}", headers = HttpHeaders.AUTHORIZATION)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<RecordDTO> randomStringV2(
        @PathVariable @Size(min = 1, max = 10000, message = "The num value must be an integer in the [1,10000] interval") Integer num,
        @PathVariable @Size(min = 1, max = 32, message = "The len value must be an integer in the [1,32] interval") Integer len,
        @RequestParam(required = false) Boolean digits,
        @RequestParam (required = false) Boolean upperAlpha,
        @RequestParam (required = false) Boolean lowerAlpha,
        @RequestParam (required = false) Boolean unique,
        @PathVariable RandomStrFormat format,
        @RequestParam (required = false) RandomStrRND rnd);
}
