package com.SubscriptionMicroservice.springbootapp.Requests;

import com.SubscriptionMicroservice.springbootapp.OperationType.OperationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class BaseRequest {

    @Getter @Setter
    public String Guid;

    @Getter @Setter
    public String OperationType;

}
