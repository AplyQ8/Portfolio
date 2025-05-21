package SOA_Microservices.PaymentService.RabbitMQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String _exchange;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    private static RabbitTemplate _rabbitTemplate;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this._rabbitTemplate = rabbitTemplate;
    }

    public static void sendMessage(String message, String exchange, String routingKey){
        LOGGER.info(String.format(message));
        _rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
