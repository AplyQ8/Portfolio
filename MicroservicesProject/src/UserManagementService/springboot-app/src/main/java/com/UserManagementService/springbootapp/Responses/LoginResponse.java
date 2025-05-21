package com.UserManagementService.springbootapp.Responses;

public class LoginResponse {
    public String Sender;
    public String ResponseType;
    public String Username;
    public boolean LoggedIn;
    public String Message;

    public LoginResponse(String sender, String responseType, String username, boolean loggedIn, String message) {
        Sender = sender;
        ResponseType = responseType;
        Username = username;
        LoggedIn = loggedIn;
        Message = message;
    }
    public LoginResponse(String sender, String responseType, boolean loggedIn, String message) {
        Sender = sender;
        ResponseType = responseType;
        LoggedIn = loggedIn;
        Message = message;
    }

}
