package com.SubscriptionMicroservice.springbootapp.Responses;

public class BaseResponse {

    public String Sender;
    public String ResponseType;
    public String Message;

    public BaseResponse(String sender, String responseType, String message) {
        Sender = sender;
        ResponseType = responseType;
        Message = message;
    }
}
