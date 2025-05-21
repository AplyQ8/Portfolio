package com.SubscriptionMicroservice.springbootapp.Responses;

public class PaymentServiceInfoResponse extends BaseResponse{

    public String paymentServiceInfo;

    public PaymentServiceInfoResponse(String sender, String responseType, String message, String paymentServiceInfo) {
        super(sender, responseType, message);
        this.paymentServiceInfo = paymentServiceInfo;
    }
}
