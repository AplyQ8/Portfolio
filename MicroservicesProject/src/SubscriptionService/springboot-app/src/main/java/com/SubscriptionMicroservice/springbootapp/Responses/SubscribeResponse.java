package com.SubscriptionMicroservice.springbootapp.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscribeResponse extends BaseResponse{

    @JsonProperty
    public boolean isSubscribed;

    public SubscribeResponse(String sender, String responseType, String message, boolean isSubscribed){
        super(sender, responseType, message);
        this.isSubscribed = isSubscribed;

    }
}
