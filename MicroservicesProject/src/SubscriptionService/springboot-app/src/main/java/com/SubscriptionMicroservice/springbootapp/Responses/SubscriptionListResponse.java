package com.SubscriptionMicroservice.springbootapp.Responses;

import com.SubscriptionMicroservice.springbootapp.SubscriptionData.SubscriptionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SubscriptionListResponse extends BaseResponse {
    @JsonProperty("Subscriptions")
    List<SubscriptionEntity> Subscriptions;



    public SubscriptionListResponse(String sender, String responseType, String message, List<SubscriptionEntity> subscriptions) {
        super(sender, responseType, message);
        Subscriptions = subscriptions;
    }
}
