package com.silvaindustries.calculatorbackend.web.api;

import org.springframework.web.bind.annotation.RestController;

@RestController
public interface Version {

    String BASE_PATH = "/v1";
    String BASE_PATH_V2 = "/v2";

    String USER_ROLE_PATH = BASE_PATH + "/user";
    String USER_ROLE_PATH_V2 = BASE_PATH_V2 + "/user";
    String ADMIN_ROLE_PATH = BASE_PATH + "/admin";
    String ADMIN_ROLE_PATH_V2 = BASE_PATH_V2 + "/admin";
}
