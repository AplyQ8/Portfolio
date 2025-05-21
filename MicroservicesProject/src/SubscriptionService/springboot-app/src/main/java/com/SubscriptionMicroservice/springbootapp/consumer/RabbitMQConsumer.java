package com.SubscriptionMicroservice.springbootapp.consumer;

import com.SubscriptionMicroservice.springbootapp.OperationType.OperationType;
import com.SubscriptionMicroservice.springbootapp.PaymentRequests.PaymentSucceedRequest;
import com.SubscriptionMicroservice.springbootapp.Requests.BaseRequest;
import com.SubscriptionMicroservice.springbootapp.Requests.SubscribeRequest;
import com.SubscriptionMicroservice.springbootapp.Services.SubscriptionService;
import com.SubscriptionMicroservice.springbootapp.publisher.RabbitMQProducer;
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
    private final SubscriptionService subscriptionService;
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
            jsonMap = objectMapper.readValue(message, Map.class);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        if(jsonMap == null)
            return;
        OperationType operationType = OperationType.valueOf((String) jsonMap.get("OperationType"));
        switch (operationType){
            case Subscribe ->
                subscriptionService.Subscribe(objectMapper.convertValue(jsonMap, SubscribeRequest.class));
            case SubscriptionList ->
                subscriptionService.ShowSubscriptionList(objectMapper.convertValue(jsonMap, BaseRequest.class));
            case PaymentSucceeded ->
                subscriptionService.SubscriptionSucceeded(objectMapper.convertValue(jsonMap, PaymentSucceedRequest.class));
            default ->
                LOGGER.info(String.format("Invalid operation"));
        }
    }
}
