package SOA_Microservices.PaymentService.Services;

import SOA_Microservices.PaymentService.Operations.OperationType;
import SOA_Microservices.PaymentService.RabbitMQ.RabbitMQProducer;
import SOA_Microservices.PaymentService.Requests.PaymentRequest;
import SOA_Microservices.PaymentService.Requests.UserPaymentConfirmation;
import SOA_Microservices.PaymentService.Responses.PaymentSucceededResponse;
import SOA_Microservices.PaymentService.TransactionData.TransactionEntity;
import SOA_Microservices.PaymentService.TransactionData.TransactionRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Value("${rabbitmq.exchange.name}")
    private String _exchangeNode;

    @Value("${rabbitmq.subscriptionExhcnage.name}")
    private String _subscriptionExchangeNode;
    private List<PaymentRequest> paymentInfoList;
    ObjectMapper objectMapper;
    TransactionRepo transactionRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    @Value("${paymentConfirm.uri}")
    private String _paymentConfirmationURL;
    @Autowired
    public PaymentService(ObjectMapper objectMapper, TransactionRepo repo, List<PaymentRequest> paymentInfoList) {
        this.objectMapper = objectMapper;
        this.transactionRepo = repo;
        this.paymentInfoList = paymentInfoList;
    }

    public void SavePaymentInfo(PaymentRequest request){
        paymentInfoList.add(request);
    }

    public PaymentInfo getPaymentInfoByUsername(String username){
        PaymentInfo paymentInfo = null;

        PaymentRequest paymentRequest = paymentInfoList.stream()
                .filter(p -> p.username.equals(username))
                .findFirst()
                .orElse(null);
        if(paymentRequest != null){
            paymentInfo = new PaymentInfo(
                    paymentRequest.username,
                    paymentRequest.price,
                    paymentRequest.purchase,
                    _paymentConfirmationURL
            );
        }

        return paymentInfo;
    }

    private void deletePaymentByUsername(String username){
        paymentInfoList.removeIf(p -> p.username.equals(username));
    }
    private boolean containsMatchingPaymentByUsername(String username){
        return paymentInfoList.stream().anyMatch(p -> p.username.equals(username));
    }
    public boolean handlePayment(UserPaymentConfirmation userPaymentConfirmation){
        if(!userPaymentConfirmation.confirm)
        {
            deletePaymentByUsername(userPaymentConfirmation.username);
            return false;
        }
        if(!containsMatchingPaymentByUsername(userPaymentConfirmation.username)){
            return false;
        }

        addTransaction(
                userPaymentConfirmation.username,
                paymentInfoList.stream()
                        .filter(p -> p.username.equals(userPaymentConfirmation.username))
                        .findFirst().get().purchase,
                paymentInfoList.stream()
                        .filter(p -> p.username.equals(userPaymentConfirmation.username))
                        .findFirst().get().price,
                LocalDateTime.now()
        );

        PaymentSucceededResponse paymentSucceededResponse =
                new PaymentSucceededResponse(
                        _exchangeNode,
                        OperationType.PaymentSucceeded.toString(),
                        paymentInfoList.stream()
                                .filter(p -> p.username.equals(userPaymentConfirmation.username))
                                .findFirst().get().username,

                        paymentInfoList.stream()
                                .filter(p -> p.username.equals(userPaymentConfirmation.username))
                                .findFirst().get().purchase_id,
                        userPaymentConfirmation.userGuid
                        );
        String jsonResponse = "";
        try{
            jsonResponse = objectMapper.writeValueAsString(paymentSucceededResponse);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        RabbitMQProducer.sendMessage(jsonResponse, _subscriptionExchangeNode, "");



        return true;
    }

    public void addTransaction(String username, String purchase, Float price, LocalDateTime purchaseDate) {
        // Создайте новый объект TransactionEntity
        TransactionEntity transaction = new TransactionEntity(username, purchase, price, purchaseDate);

        // Сохраните транзакцию
        transactionRepo.save(transaction);
    }
}

