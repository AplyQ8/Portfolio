namespace SportCenter_Client.Services.UserManagementService;

public class RegistrationRequest: BaseRequest
{
    public string Login { get; set; }

    public string Password { get; set; }

    public string Email { get; set; }
}