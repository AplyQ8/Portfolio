package com.SubscriptionMicroservice.springbootapp.Requests;

import lombok.Data;

@Data
public class SubscribeRequest extends BaseRequest{
    public String Username;
    public Long subscriptionID;
}
