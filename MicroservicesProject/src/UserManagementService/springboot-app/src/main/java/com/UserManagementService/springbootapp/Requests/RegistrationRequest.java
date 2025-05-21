package com.UserManagementService.springbootapp.Requests;

import lombok.Getter;
import lombok.Setter;

public class RegistrationRequest extends BaseRequest{
    @Getter
    @Setter
    public String Login;

    @Getter
    @Setter
    public String Password;

    @Getter
    @Setter
    public String Email;
}
