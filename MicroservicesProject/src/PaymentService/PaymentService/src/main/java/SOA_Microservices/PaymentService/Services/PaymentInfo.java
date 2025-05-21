package SOA_Microservices.PaymentService.Services;

import lombok.Data;

@Data
public class PaymentInfo {
    public String username;
    public Float price;
    public String purchase;
    public String urlPaymentConfirmation;

    public PaymentInfo(String username, Float price, String purchase, String urlPayment) {
        this.username = username;
        this.price = price;
        this.purchase = purchase;
        this.urlPaymentConfirmation = urlPayment;

    }
}
