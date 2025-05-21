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
@RequestMapping("/paymentConfirmation")
@AllArgsConstructor
public class PaymentConfirmationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;

//    @GetMapping("/{confirm}")
//    public boolean getPaymentConfirmation(@RequestBody UserPaymentConfirmation usrPaymentConfirm){
//        return paymentService.handlePayment(usrPaymentConfirm);
//    }

    @PostMapping("/{confirm}")
    public ResponseEntity<?> getPaymentConfirmation(@RequestBody UserPaymentConfirmation usrPaymentConfirm){

        boolean isProceeded = paymentService.handlePayment(usrPaymentConfirm);
        if (isProceeded) {
            LOGGER.info(String.format("Payment proceeded"));
            return ResponseEntity.ok(isProceeded);
        } else {
            LOGGER.info(String.format("Payment was not proceeded"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment aborted");
        }

    }
}
