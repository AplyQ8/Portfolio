package com.UserManagementService.springbootapp.Requests;

import lombok.Getter;
import lombok.Setter;

public class LoginRequest extends BaseRequest {
    @Getter
    @Setter
    public String Login;

    @Getter
    @Setter
    public String Password;
}
