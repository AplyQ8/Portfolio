package com.UserManagementService.springbootapp.Functionality;

import com.UserManagementService.springbootapp.Entities.LoggedInUserEntity;
import com.UserManagementService.springbootapp.Producer.RabbitMQProducer;
import com.UserManagementService.springbootapp.Repositories.LoggedUserRepo;
import com.UserManagementService.springbootapp.Repositories.UserRepo;
import com.UserManagementService.springbootapp.Requests.LoginRequest;
import com.UserManagementService.springbootapp.Requests.LogoutRequest;
import com.UserManagementService.springbootapp.Responses.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoginService {

    @Value("${rabbitmq.clientExchange.name}")
    private String _clientExchangeNode;
    @Value("${rabbitmq.exchange.name}")
    private String _exchangeNode;

    UserRepo repo;
    LoggedUserRepo logRepo;
    ObjectMapper objectMapper;
    @Autowired
    public LoginService(UserRepo repo, LoggedUserRepo lRepo,ObjectMapper objectMapper) {
        this.repo = repo;
        this.logRepo = lRepo;
        this.objectMapper = objectMapper;
    }

    public LoginService() {
    }

    public boolean UserExists(LoginRequest request){
        return repo.existsByUsername(request.Login);
    }

    public boolean UserLoggedIn(String username){
        return logRepo.existsByUsername(username);
    }

    public void LogIn(LoginRequest request){
        if(UserExists(request)) {
            if(!repo.findByUsername(request.Login).getPassword().matches(request.getPassword()))
            {
                SendErrorMessage(request.Guid, "Incorrect password!");
                return;
            }
            SendSuccessfulMessage(request.Guid, repo.findByUsername(request.Login).getUsername(), "Successful login!");
            if(!UserLoggedIn(request.Login))
            {
                LoggedInUserEntity newLogIn =
                        new LoggedInUserEntity(
                                request.Login
                        );
                logRepo.save(newLogIn);
            }
            return;
        }
        SendErrorMessage(request.Guid, "No such user!");
    }
    public void LogOut(LogoutRequest request){
        if(UserLoggedIn(request.Login))
            logRepo.deleteByUsername(request.Login);
    }

    private LoginResponse createLoginResponse(boolean loggedIn, String login, String Message){
        LoginResponse response =
                new LoginResponse(
                        _exchangeNode,
                        ResponseType.Login.toString(),
                        login,
                        loggedIn,
                        Message
                );

        return response;
    }
    private LoginResponse createLoginResponse(boolean loggedIn, String Message){
        LoginResponse response =
                new LoginResponse(
                        _exchangeNode,
                        ResponseType.Login.toString(),
                        loggedIn,
                        Message
                );

        return response;
    }
    private void SendErrorMessage(String routingKey, String errorMessage){
        String message = "";
        try{
            message = objectMapper.writeValueAsString(createLoginResponse(false, errorMessage));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        RabbitMQProducer.sendMessage(message, _clientExchangeNode, routingKey);
    }
    private void SendSuccessfulMessage(String routingKey,String login, String Message){
        String message = "";
        try{
            message = objectMapper.writeValueAsString(createLoginResponse(true, login, Message));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        RabbitMQProducer.sendMessage(message, _clientExchangeNode, routingKey);
    }
}
