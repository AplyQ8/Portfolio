package com.UserManagementService.springbootapp.Controllers;

import com.UserManagementService.springbootapp.Functionality.LoginService;
import com.UserManagementService.springbootapp.consumer.RabbitMQConsumer;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final LoginService loginService;
    @GetMapping("/{username}")
    public boolean getUserInfo(@PathVariable String username) {
        LOGGER.info(String.format("Request is completed"));
        return loginService.UserLoggedIn(username);
    }
}
