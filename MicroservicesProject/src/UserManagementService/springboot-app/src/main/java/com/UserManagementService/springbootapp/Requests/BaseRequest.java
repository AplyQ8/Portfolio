package com.UserManagementService.springbootapp.Requests;

import lombok.Getter;
import lombok.Setter;

public class BaseRequest {
    @Getter
    @Setter
    public String Guid;

    @Getter
    @Setter
    public String OperationType;
}
