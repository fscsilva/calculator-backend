package com.silvaindustries.calculatorbackend.service.v1;

import com.silvaindustries.calculatorbackend.persistence.model.Operation;
import com.silvaindustries.calculatorbackend.persistence.model.Record;
import com.silvaindustries.calculatorbackend.persistence.model.Type;
import com.silvaindustries.calculatorbackend.persistence.model.User;
import com.silvaindustries.calculatorbackend.persistence.repository.OperationRepository;
import com.silvaindustries.calculatorbackend.persistence.repository.RecordRepository;
import com.silvaindustries.calculatorbackend.persistence.repository.UserRepository;
import com.silvaindustries.calculatorbackend.service.dto.business.OperationDTO;
import com.silvaindustries.calculatorbackend.service.dto.business.RecordDTO;
import com.silvaindustries.calculatorbackend.service.dto.business.TypeEnum;
import com.silvaindustries.calculatorbackend.service.dto.business.UserDTO;
import com.silvaindustries.calculatorbackend.service.exception.NegativeOrNonBalanceException;
import com.silvaindustries.calculatorbackend.service.exception.UserNotFoundException;
import com.silvaindustries.calculatorbackend.web.dto.RandomStrFormat;
import com.silvaindustries.calculatorbackend.web.dto.RandomStrRND;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class OperationService {

    private final UserRepository userRepository;


    private final RecordRepository recordRepository;

    private final OperationRepository operationRepository;

    private final RestTemplate restTemplate;

    public CompletableFuture<RecordDTO> addition(String quantity) {
        var addition = Double.parseDouble(quantity);
        var username = UserService.getPrincipal();

        var userFuture = CompletableFuture.supplyAsync(() -> userRepository.findUsersByUsername(username)
                .orElseThrow( () -> new UserNotFoundException("User not found")))
            .thenApply(user -> new User(user.getUserId(), user.getUsername(), user.getPassword(), user.getStatus(),
                user.getBalance() + addition, user.getRoles()))
            .thenApply(userRepository::save);

        var userDto = userFuture.join();

        var operationFuture = userFuture.thenApply(user -> operationRepository.save(new Operation(null,
                new Type(BigInteger.valueOf(TypeEnum.ADDITION.getId()), TypeEnum.ADDITION.name()), addition)));

        return operationFuture.thenApply(operation -> recordRepository.save(
            new Record(null, userDto, operation, addition, userDto.getBalance(), "successful", LocalDateTime.now()))
        ).thenApply(record -> RecordDTO.builder()
            .amount(record.getAmount())
            .operation(OperationDTO.builder()
                .operationId(record.getOperation().getOperationId())
                .cost(record.getOperation().getCost())
                .type(TypeEnum.valueOf(record.getOperation().getType_id().getTypeName()))
                .build())
            .operationResponse(record.getOperationResponse())
            .date(record.getDate())
            .userBalance(record.getUserBalance())
            .user(UserDTO.builder()
                .userId(userDto.getUserId())
                .balance(userDto.getBalance())
                .statusActive(userDto.getStatus())
                .username(userDto.getUsername())
                .build())
            .recordId(record.getRecordId())
            .build());
    }

    public CompletableFuture<RecordDTO> subtraction(String quantity) {
        var subtraction = Double.parseDouble(quantity);
        var username = UserService.getPrincipal();

        var userFuture = CompletableFuture.supplyAsync(() -> userRepository.findUsersByUsername(username)
                .orElseThrow( () -> new UserNotFoundException("User not found")))
            .thenApply(user -> new User(user.getUserId(), user.getUsername(), user.getPassword(), user.getStatus(),
                validateNegativeBalance(user.getBalance(), subtraction),  user.getRoles()))
            .thenApply(userRepository::save);

        var userDto = userFuture.join();

        var operationFuture = userFuture.thenApply(user -> operationRepository.save(new Operation(null,
            new Type(BigInteger.valueOf(TypeEnum.SUBTRACTION.getId()), TypeEnum.SUBTRACTION.name()), subtraction)));

        return operationFuture.thenApply(operation -> recordRepository.save(
            new Record(null, userDto, operation, subtraction, userDto.getBalance(), "successful", LocalDateTime.now()))
        ).thenApply(record -> RecordDTO.builder()
            .amount(record.getAmount())
            .operation(OperationDTO.builder()
                .operationId(record.getOperation().getOperationId())
                .cost(record.getOperation().getCost())
                .type(TypeEnum.valueOf(record.getOperation().getType_id().getTypeName()))
                .build())
            .operationResponse(record.getOperationResponse())
            .date(record.getDate())
            .userBalance(record.getUserBalance())
            .user(UserDTO.builder()
                .userId(userDto.getUserId())
                .balance(userDto.getBalance())
                .statusActive(userDto.getStatus())
                .username(userDto.getUsername())
                .build())
            .recordId(record.getRecordId())
            .build());
    }
    public CompletableFuture<RecordDTO> division(String quantity) {
        var division = Double.parseDouble(quantity);
        var username = UserService.getPrincipal();

        var userFuture = CompletableFuture.supplyAsync(() -> userRepository.findUsersByUsername(username)
                .orElseThrow( () -> new UserNotFoundException("User not found")))
            .thenApply(user -> new User(user.getUserId(), user.getUsername(), user.getPassword(), user.getStatus(),
                user.getBalance() / division, user.getRoles()))
            .thenApply(userRepository::save);

        var userDto = userFuture.join();

        var operationFuture = userFuture.thenApply(user -> operationRepository.save(new Operation(null,
            new Type(BigInteger.valueOf(TypeEnum.DIVISION.getId()), TypeEnum.DIVISION.name()), division)));

        return operationFuture.thenApply(operation -> recordRepository.save(
            new Record(null, userDto, operation, division, userDto.getBalance(), "successful", LocalDateTime.now()))
        ).thenApply(record -> RecordDTO.builder()
            .amount(record.getAmount())
            .operation(OperationDTO.builder()
                .operationId(record.getOperation().getOperationId())
                .cost(record.getOperation().getCost())
                .type(TypeEnum.valueOf(record.getOperation().getType_id().getTypeName()))
                .build())
            .operationResponse(record.getOperationResponse())
            .date(record.getDate())
            .userBalance(record.getUserBalance())
            .user(UserDTO.builder()
                .userId(userDto.getUserId())
                .balance(userDto.getBalance())
                .statusActive(userDto.getStatus())
                .username(userDto.getUsername())
                .build())
            .recordId(record.getRecordId())
            .build());
    }
    public CompletableFuture<RecordDTO> multiplication(String quantity) {
        var multiplication = Double.parseDouble(quantity);
        var username = UserService.getPrincipal();

        var userFuture = CompletableFuture.supplyAsync(() -> userRepository.findUsersByUsername(username)
                .orElseThrow( () -> new UserNotFoundException("User not found")))
            .thenApply(user -> new User(user.getUserId(), user.getUsername(), user.getPassword(), user.getStatus(),
                user.getBalance() * multiplication, user.getRoles()))
            .thenApply(userRepository::save);

        var userDto = userFuture.join();

        var operationFuture = userFuture.thenApply(user -> operationRepository.save(new Operation(null,
            new Type(BigInteger.valueOf(TypeEnum.MULTIPLICATION.getId()), TypeEnum.MULTIPLICATION.name()), multiplication)));

        return operationFuture.thenApply(operation -> recordRepository.save(
            new Record(null, userDto, operation, multiplication, userDto.getBalance(), "successful", LocalDateTime.now()))
        ).thenApply(record -> RecordDTO.builder()
            .amount(record.getAmount())
            .operation(OperationDTO.builder()
                .operationId(record.getOperation().getOperationId())
                .cost(record.getOperation().getCost())
                .type(TypeEnum.valueOf(record.getOperation().getType_id().getTypeName()))
                .build())
            .operationResponse(record.getOperationResponse())
            .date(record.getDate())
            .userBalance(record.getUserBalance())
            .user(UserDTO.builder()
                .userId(userDto.getUserId())
                .balance(userDto.getBalance())
                .statusActive(userDto.getStatus())
                .username(userDto.getUsername())
                .build())
            .recordId(record.getRecordId())
            .build());
    }
    public CompletableFuture<RecordDTO> sqrt() {
        var username = UserService.getPrincipal();
        var userFuture = CompletableFuture.supplyAsync(() -> userRepository.findUsersByUsername(username)
                .orElseThrow( () -> new UserNotFoundException("User not found")))
            .thenApply(user -> new User(user.getUserId(), user.getUsername(), user.getPassword(), user.getStatus(),
                Math.sqrt(validateNonBalance(user.getBalance())), user.getRoles()))
            .thenApply(userRepository::save);

        var userDto = userFuture.join();

        var operationFuture = userFuture.thenApply(user -> operationRepository.save(new Operation(null,
            new Type(BigInteger.valueOf(TypeEnum.SQRT.getId()), TypeEnum.SQRT.name()), 2D)));

        return operationFuture.thenApply(operation -> recordRepository.save(
            new Record(null, userDto, operation, 2D, userDto.getBalance(), "successful", LocalDateTime.now()))
        ).thenApply(record -> RecordDTO.builder()
            .amount(record.getAmount())
            .operation(OperationDTO.builder()
                .operationId(record.getOperation().getOperationId())
                .cost(record.getOperation().getCost())
                .type(TypeEnum.valueOf(record.getOperation().getType_id().getTypeName()))
                .build())
            .operationResponse(record.getOperationResponse())
            .date(record.getDate())
            .userBalance(record.getUserBalance())
            .user(UserDTO.builder()
                .userId(userDto.getUserId())
                .balance(userDto.getBalance())
                .statusActive(userDto.getStatus())
                .username(userDto.getUsername())
                .build())
            .recordId(record.getRecordId())
            .build());
    }
    public CompletableFuture<RecordDTO> randomStr(Integer num, Integer len, Boolean digits,
        Boolean upperAlpha, Boolean lowerAlpha, Boolean unique, RandomStrFormat format, RandomStrRND rnd) {
        var username = UserService.getPrincipal();
        var userFuture = CompletableFuture.supplyAsync(() -> userRepository.findUsersByUsername(username)
                .orElseThrow( () -> new UserNotFoundException("User not found")))
            .thenApply(user -> new User(user.getUserId(), user.getUsername(), user.getPassword(), user.getStatus(),
                user.getBalance(), user.getRoles()))
            .thenApply(userRepository::save);

        var userDto = userFuture.join();

        var randomStrFuture = CompletableFuture.supplyAsync(() ->
                restTemplate.getForEntity("https://www.random.org/strings/?num=" + num +
                    "&len=" + len + "&digits=" + (digits?"on":"off") + "&upperalpha=" + (upperAlpha?"on":"off") +
                    "&loweralpha="+ (lowerAlpha?"on":"off") +"&unique="+ (unique?"on":"off") +"&format=" + format.getFormat() +
                    "&rnd=" + rnd.getRnd(), String.class))
            .thenApply(HttpEntity::getBody);

        var operationFuture = randomStrFuture.thenApply(stringResponseEntity -> operationRepository.save(new Operation(null,
                new Type(BigInteger.valueOf(TypeEnum.RANDOM_STR.getId()), TypeEnum.RANDOM_STR.name()), 0D)));

        return operationFuture.thenCompose(operation -> randomStrFuture.thenApply(stringFuture -> recordRepository.save(
            new Record(null, userDto, operation, 0D, userDto.getBalance(), stringFuture, LocalDateTime.now())))
        ).thenApply(record -> RecordDTO.builder()
            .amount(record.getAmount())
            .operation(OperationDTO.builder()
                .operationId(record.getOperation().getOperationId())
                .cost(record.getOperation().getCost())
                .type(TypeEnum.valueOf(record.getOperation().getType_id().getTypeName()))
                .build())
            .operationResponse(record.getOperationResponse())
            .date(record.getDate())
            .userBalance(record.getUserBalance())
            .user(UserDTO.builder()
                .userId(userDto.getUserId())
                .balance(userDto.getBalance())
                .statusActive(userDto.getStatus())
                .username(userDto.getUsername())
                .build())
            .recordId(record.getRecordId())
            .build());
    }
    private Double validateNegativeBalance(Double balance, Double subtraction) {

        return Optional.of(balance - subtraction)
            .filter(newBalance -> newBalance >= 0)
            .orElseThrow(() -> new NegativeOrNonBalanceException("The user doesn't have enough balance for this operation"));
    }
    private Double validateNonBalance(Double balance) {
        return Optional.of(balance)
            .filter(newBalance -> newBalance >= 0)
            .orElseThrow(() -> new NegativeOrNonBalanceException("The user doesn't have enough balance for this operation"));
    }

}