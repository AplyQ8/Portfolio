package com.UserManagementService.springbootapp.Responses;

import lombok.Data;

@Data
public class RegistrationResponse {
    public String Sender;
    public String ResponseType;
    public boolean IsRegistered;
    public String Message;

    public RegistrationResponse(String sender, String responseType, boolean isRegistered, String message) {
        Sender = sender;
        ResponseType = responseType;
        IsRegistered = isRegistered;
        Message = message;
    }
}
