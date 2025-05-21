package com.UserManagementService.springbootapp.Requests;

import lombok.Getter;
import lombok.Setter;

public class LogoutRequest extends BaseRequest{
    @Getter
    @Setter
    public String Login;
}
