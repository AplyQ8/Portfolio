package com.SubscriptionMicroservice.springbootapp.PaymentRequests;

public class RequestToPaymentService {
    public String sender;
    public String paymentRequestType;
    public String username;
    public String purchase;
    public Long purchase_id;
    public Float price;

    public RequestToPaymentService(String sender, String paymentRequestType, String username, String purchase, Long purchase_id, Float price) {
        this.sender = sender;
        this.paymentRequestType = paymentRequestType;
        this.username = username;
        this.purchase = purchase;
        this.purchase_id = purchase_id;
        this.price = price;
    }
}
