package com.UserManagementService.springbootapp.Functionality;

import com.UserManagementService.springbootapp.Entities.UserEntity;
import com.UserManagementService.springbootapp.Producer.RabbitMQProducer;
import com.UserManagementService.springbootapp.Repositories.UserRepo;
import com.UserManagementService.springbootapp.Requests.LoginRequest;
import com.UserManagementService.springbootapp.Requests.RegistrationRequest;
import com.UserManagementService.springbootapp.Responses.RegistrationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RegisterService {
    UserRepo repo;
    ObjectMapper objectMapper;
    @Autowired
    public RegisterService(UserRepo repo, ObjectMapper mapper) {
        this.repo = repo;
        objectMapper = mapper;
    }
    public RegisterService() {
    }

    @Value("${rabbitmq.clientExchange.name}")
    private String _clientExchangeNode;
    @Value("${rabbitmq.exchange.name}")
    private String _exchangeNode;

    public boolean UserExists(RegistrationRequest request){
        return repo.existsByUsername(request.Login);

    }
    public void RegisterUser(RegistrationRequest registrationRequest){

        if(UserExists(registrationRequest))
        {
            RegistrationResponse response =
                    new RegistrationResponse(
                            _exchangeNode,
                            ResponseType.Registration.toString(),
                            false,
                            "User already exists");
            String message = "";
            try{
                message = objectMapper.writeValueAsString(response);
            }
            catch(IOException e){
                e.printStackTrace();
            }
            RabbitMQProducer.
                    sendMessage(message, _clientExchangeNode, registrationRequest.Guid);
            return;
        }
        UserEntity newUser =
                new UserEntity(
                        registrationRequest.Login,
                        registrationRequest.Password,
                        registrationRequest.Email);
        repo.save(newUser);
        RegistrationResponse response =
                new RegistrationResponse(
                  _exchangeNode,
                  ResponseType.Registration.toString(),
                  true,
                  "Successful registration!"
                );
        String message = "";
        try{
            message = objectMapper.writeValueAsString(response);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        RabbitMQProducer.
                sendMessage(message, _clientExchangeNode, registrationRequest.Guid);
    }
}
