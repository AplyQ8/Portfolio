namespace SportCenter_Client.Responses;

public class LoginResponse: BaseResponse
{
    public string Username { get; set; }
    public bool LoggedIn { get; set; }
}