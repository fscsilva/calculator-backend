package com.silvaindustries.calculatorbackend.persistence.repository.handler;

import com.silvaindustries.calculatorbackend.persistence.model.User;
import com.silvaindustries.calculatorbackend.persistence.repository.UserRepository;
import com.silvaindustries.calculatorbackend.service.exception.UserException;
import com.silvaindustries.calculatorbackend.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class EventHandler {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @HandleBeforeCreate
    public void handleUserCreate(User user) {
        if (userRepository.findUsersByUsername(user.getUsername()).isPresent()) {
            throw new UserException("Username already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @HandleBeforeSave
    public void handleUserUpdate(User user) {
        if (user.getPassword() == null || user.getPassword().equals("")) {
            User storedUser = userRepository.findUsersByUserId(user.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found " + user.getUsername()));
            user.setPassword(storedUser.getPassword());
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }
}
