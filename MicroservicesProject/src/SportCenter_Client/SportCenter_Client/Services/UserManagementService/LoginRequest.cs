namespace SportCenter_Client.Services.UserManagementService;

public class LoginRequest: BaseRequest
{
    public string Login { get; set; }
    public string Password { get; set; }
}