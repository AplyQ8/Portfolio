package com.SubscriptionMicroservice.springbootapp.Requests;

import lombok.Data;

@Data
public class UserHasSubscriptionRequest {

    public String username;

    public String subscriptionName;
}
