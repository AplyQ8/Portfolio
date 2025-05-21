package com.UserManagementService.springbootapp.consumer;

import com.UserManagementService.springbootapp.Functionality.LoginService;
import com.UserManagementService.springbootapp.Functionality.RegisterService;
import com.UserManagementService.springbootapp.Operations.OperationType;
import com.UserManagementService.springbootapp.Requests.BaseRequest;
import com.UserManagementService.springbootapp.Requests.LoginRequest;
import com.UserManagementService.springbootapp.Requests.LogoutRequest;
import com.UserManagementService.springbootapp.Requests.RegistrationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final RegisterService registerService;
    private final  LoginService loginService;
    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message){
        //LOGGER.info(String.format(message));
        HandleMessage(message);
    }

    public void HandleMessage(String message){
        ObjectMapper objectMapper = new ObjectMapper();
        BaseRequest request = null;
        Map<String, Object> jsonMap = null;
        try{
            //request = objectMapper.readValue(message, BaseRequest.class);
            jsonMap = objectMapper.readValue(message, Map.class);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        if(jsonMap == null)
            return;
        //OperationType operation = OperationType.valueOf(request.OperationType);
        OperationType operationType = OperationType.valueOf((String) jsonMap.get("OperationType"));
        switch (operationType){
            case Login ->
                    loginService.LogIn(objectMapper.convertValue(jsonMap, LoginRequest.class));
            case Registration ->
                    registerService.RegisterUser(objectMapper.convertValue(jsonMap, RegistrationRequest.class));
            case Logout ->
                loginService.LogOut(objectMapper.convertValue(jsonMap, LogoutRequest.class));
            default -> LOGGER.info(String.format("Invalid operation"));
        }
    }
}
