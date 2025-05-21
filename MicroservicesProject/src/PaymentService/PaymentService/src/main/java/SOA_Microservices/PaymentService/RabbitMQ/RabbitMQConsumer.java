package SOA_Microservices.PaymentService.RabbitMQ;

import SOA_Microservices.PaymentService.Requests.BaseRequest;
import SOA_Microservices.PaymentService.Requests.PaymentRequest;
import SOA_Microservices.PaymentService.Requests.PaymentRequestType;
import SOA_Microservices.PaymentService.Services.PaymentService;
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
    private final PaymentService paymentService;
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
        PaymentRequestType operationType = PaymentRequestType.valueOf((String) jsonMap.get("paymentRequestType"));
        switch (operationType){
            case Payment ->
                    paymentService.SavePaymentInfo(objectMapper.convertValue(jsonMap, PaymentRequest.class));
            default ->
                    LOGGER.info(String.format("Invalid operation"));
        }
    }
}
