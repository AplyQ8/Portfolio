package SOA_Microservices.PaymentService.Requests;

public class PaymentRequest extends BaseRequest {
    public String sender;
    public String username;
    public String purchase;
    public Long purchase_id;
    public Float price;
}
