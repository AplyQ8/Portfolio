package SOA_Microservices.PaymentService.Controllers;

import SOA_Microservices.PaymentService.Requests.UserPaymentConfirmation;
import SOA_Microservices.PaymentService.Services.PaymentInfo;
import SOA_Microservices.PaymentService.Services.PaymentService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;


    @GetMapping("/{username}")
    public ResponseEntity<?> getPaymentInfo(@PathVariable String username){
        PaymentInfo paymentInfo = paymentService.getPaymentInfoByUsername(username);
        if (paymentInfo != null) {
            LOGGER.info(String.format("Operation proceeded"));
            return ResponseEntity.ok(paymentInfo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment info not found for username: " + username);
        }

    }



}
