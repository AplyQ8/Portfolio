package com.SubscriptionMicroservice.springbootapp.Services;

import com.SubscriptionMicroservice.springbootapp.PaymentRequests.PaymentRequestType;
import com.SubscriptionMicroservice.springbootapp.PaymentRequests.PaymentSucceedRequest;
import com.SubscriptionMicroservice.springbootapp.PaymentRequests.RequestToPaymentService;
import com.SubscriptionMicroservice.springbootapp.Requests.BaseRequest;
import com.SubscriptionMicroservice.springbootapp.Requests.SubscribeRequest;
import com.SubscriptionMicroservice.springbootapp.Responses.PaymentServiceInfoResponse;
import com.SubscriptionMicroservice.springbootapp.Responses.ResponseType;
import com.SubscriptionMicroservice.springbootapp.Responses.SubscribeResponse;
import com.SubscriptionMicroservice.springbootapp.Responses.SubscriptionListResponse;
import com.SubscriptionMicroservice.springbootapp.SubscriptionData.SubscriptionEntity;
import com.SubscriptionMicroservice.springbootapp.SubscriptionData.SubscriptionRepo;
import com.SubscriptionMicroservice.springbootapp.UserSubscriptionsData.UserSubscriptionEntity;
import com.SubscriptionMicroservice.springbootapp.UserSubscriptionsData.UserSubscriptionsRepo;
import com.SubscriptionMicroservice.springbootapp.publisher.RabbitMQProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

@Service
public class SubscriptionService {
    @Value("${isLoggedIn.uri}")
    private String _isLoggedInUri;
    @Value("${paymentService.uri}")
    private String _paymentServiceURL;

    @Value("${rabbitmq.exchange.name}")
    private String _exchangeNode;

    @Value("${rabbitmq.clientExchange.name}")
    private String _clientExchangeNode;

    @Value("${rabbitmq.paymentExchange.name}")
    private String _paymentExchangeNode;
    ObjectMapper objectMapper;
    SubscriptionRepo subscriptionRepo;
    UserSubscriptionsRepo userSubscriptionsRepo;
    private final WebClient.Builder webClientBuilder;
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionService.class);

    @Autowired
    public SubscriptionService(ObjectMapper objectMapper, SubscriptionRepo repo, WebClient.Builder webClientBuilder, UserSubscriptionsRepo userSubscriptionsRepo) {
        this.objectMapper = objectMapper;
        this.subscriptionRepo = repo;
        this.webClientBuilder = webClientBuilder;
        this.userSubscriptionsRepo = userSubscriptionsRepo;
    }

    private boolean isUserLoggedIn(String username) {
        // Make a synchronous request to UserManagement service
        return Boolean.TRUE.equals(webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/user/" + username)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
    }

    public void Subscribe(SubscribeRequest request){
        if(!isUserLoggedIn(request.Username)){
            LOGGER.info(String.format("Invalid operation"));
            sendSubscribeErrorMessage(request.Guid, "User is not logged in!");
            return;
        }
        if(hasUserSubscriptionByUsernameAndSubscriptionId(request.Username, request.subscriptionID)){
            LOGGER.info(String.format("User already has subscription"));
            sendSubscribeErrorMessage(request.Guid, "You already have this subscription!");
            return;
        }
        LOGGER.info(String.format("Valid operation"));
        //Отправить запрос в Payment Service с информацией о покупке
        SubscriptionEntity subscriptionEntity = subscriptionRepo.findById(request.subscriptionID).orElse(null);
        RequestToPaymentService requestToPaymentService =
                new RequestToPaymentService(
                        _exchangeNode,
                        PaymentRequestType.Payment.toString(),
                        request.Username,
                        subscriptionEntity.getName(),
                        subscriptionEntity.getId(),
                        subscriptionEntity.getPrice().floatValue()

                );
        String jsonString = "";
        try{
            jsonString = objectMapper.writeValueAsString(requestToPaymentService);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        RabbitMQProducer.sendMessage(jsonString, _paymentExchangeNode, "");
        //Отправить клиенту информацию с URL PaymentService для Get запроса
        PaymentServiceInfoResponse paymentServiceInfoResponse =
                new PaymentServiceInfoResponse(
                        _exchangeNode,
                        ResponseType.PaymentServiceInfo.toString(),
                        "Your payment was formed!",
                        _paymentServiceURL
                );
        String response = "";
        try{
            response = objectMapper.writeValueAsString(paymentServiceInfoResponse);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        RabbitMQProducer.sendMessage(response, _clientExchangeNode, request.Guid);

    }

    public void SubscriptionSucceeded(PaymentSucceedRequest paymentSucceedRequest){
        LOGGER.info(String.format("Subscription Succeeded!"));
        SubscriptionEntity subscription
                = subscriptionRepo.findById(paymentSucceedRequest.purchase_id).orElseThrow(() -> new RuntimeException("Подписка не найдена"));
        UserSubscriptionEntity userSubscription = new UserSubscriptionEntity();
        userSubscription.setUsername(paymentSucceedRequest.username);
        userSubscription.setSubscription(subscription);
        userSubscriptionsRepo.save(userSubscription);
        SubscribeResponse response = createSubscribeResponse("Subscription completed!", true);
        try{
            String json = objectMapper.writeValueAsString(response);
            RabbitMQProducer.sendMessage(json, _clientExchangeNode, paymentSucceedRequest.userGuid);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void ShowSubscriptionList(BaseRequest request){
        List<SubscriptionEntity> subscriptions = subscriptionRepo.findAll();
        try {
            String subscriptionsJson = objectMapper.writeValueAsString(subscriptions);
            SubscriptionListResponse response =
                    new SubscriptionListResponse(
                            _exchangeNode,
                            ResponseType.SubscriptionList.toString(),
                            "List of subscriptions",
                            subscriptionRepo.findAll());
            String jsonString = objectMapper.writeValueAsString(response);
            RabbitMQProducer.sendMessage(jsonString, _clientExchangeNode, request.Guid);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void sendSubscribeErrorMessage(String routingKey, String errorMessage){
        String message = "";
        try{
            message = objectMapper.
                    writeValueAsString(createSubscribeResponse(errorMessage, false));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        RabbitMQProducer.sendMessage(message, _clientExchangeNode, routingKey);
    }

    private SubscribeResponse createSubscribeResponse(String message, boolean isSubscribed){
        SubscribeResponse response =
                new SubscribeResponse(
                        _exchangeNode,
                        ResponseType.SubscribeOperation.toString(),
                        message,
                        isSubscribed
                );
        return response;
    }

    private boolean hasUserSubscriptionByUsernameAndSubscriptionId(String username, Long subscriptionId) {
        // Проверяем, есть ли запись в UserSubscriptionEntity для данного пользователя и подписки
        return userSubscriptionsRepo.existsByUsernameAndSubscription_Id(username, subscriptionId);
    }
}
