package com.silvaindustries.calculatorbackend.service.v2;

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
import com.silvaindustries.calculatorbackend.service.exception.UserNotFoundException;
import com.silvaindustries.calculatorbackend.service.v1.UserService;
import com.silvaindustries.calculatorbackend.web.dto.RandomStrFormat;
import com.silvaindustries.calculatorbackend.web.dto.RandomStrRND;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class OperationServiceV2 {

    private final UserRepository userRepository;

    private final RecordRepository recordRepository;

    private final OperationRepository operationRepository;

    private final RestTemplate restTemplate;

    public CompletableFuture<RecordDTO> randomStrV2(Integer num, Integer len, Boolean digits,
        Boolean upperAlpha, Boolean lowerAlpha, Boolean unique, RandomStrFormat format, RandomStrRND rnd) {
        var username = UserService.getPrincipal();
        var userFuture = CompletableFuture.supplyAsync(() -> userRepository.findUsersByUsername(username)
                .orElseThrow( () -> new UserNotFoundException("User not found")))
            .thenApply(user -> new User(user.getUserId(), user.getUsername(), user.getPassword(), user.getStatus(),
                user.getBalance(), user.getRoles()))
            .thenApply(userRepository::save);

        var userDto = userFuture.join();

        var digistsStr = Optional.ofNullable(digits).map(digitsBoolean -> digits?"on":"off").orElse("off");
        var upperStr = Optional.ofNullable(upperAlpha).map(digitsBoolean -> upperAlpha?"on":"off").orElse("off");
        var lowerStr = Optional.ofNullable(lowerAlpha).map(digitsBoolean -> lowerAlpha?"on":"off").orElse("on");
        var uniqueStr = Optional.ofNullable(unique).map(digitsBoolean -> unique?"on":"off").orElse("off");
        var rndStr = Optional.ofNullable(rnd).map(RandomStrRND::getRnd).orElse(RandomStrRND.NEW.getRnd());

        var uri = UriComponentsBuilder
            .fromHttpUrl("https://www.random.org/strings/?num={num}&len={len}" +
                "&digits={digistsStr}&upperalpha={upperStr}&loweralpha={lowerStr}&unique={uniqueStr}&format={format}&rnd={rndStr}")
            .buildAndExpand(num, len, digistsStr, upperStr, lowerStr, uniqueStr, format.getFormat(), rndStr)
            .toUriString();

        var randomStrFuture = CompletableFuture.supplyAsync(() ->
                restTemplate.getForEntity(uri, String.class))
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

}