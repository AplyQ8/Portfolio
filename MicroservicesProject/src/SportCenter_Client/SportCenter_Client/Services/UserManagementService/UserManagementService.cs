using SportCenter_Client.Responses;

namespace SportCenter_Client.Services.UserManagementService;

public class UserManagementService
{
    public static string Username { get; private set; }

    private readonly string ExchangeNode = "UserManagement";
    private static readonly string RoutingKey = "";
    
    private static UserManagementService _instance;

    public static UserManagementService GetInstance()
    {
        return _instance ??= new UserManagementService();
    }
    
    public void SendRequest(OperationType operation)
    {
        switch (operation)
        {
            case OperationType.Login:
                LoginInput(out string? login, out string? password);
                if (!InputIsValid(login, password))
                {
                    Console.WriteLine("Invalid Input");
                    return;
                }
                var loginRequest = new LoginRequest()
                {
                    OperationType = OperationType.Login.ToString(),
                    Login = login,
                    Password = password
                };
                RabbitMq.PublicMessage(ExchangeNode, RoutingKey, loginRequest);
                break;
            case OperationType.Registration:
                RegisterInput(out string? regLogin, out string? regPassword, out string? email);
                if (!InputIsValid(regLogin, regPassword))
                {
                    Console.WriteLine("Invalid Input");
                    return;
                }
                var registerRequest = new RegistrationRequest()
                {
                    OperationType = OperationType.Registration.ToString(),
                    Login = regLogin,
                    Password = regPassword,
                    Email = email
                };
                RabbitMq.PublicMessage(ExchangeNode, RoutingKey, registerRequest);
                break;
            default:
                Console.WriteLine("Invalid Operation");
                return;
        }
    }

    private void LoginInput(out string? login, out string? password)
    {
        Console.WriteLine("Enter login: \n");
        login = Console.ReadLine();
        
        Console.WriteLine("Enter password: \n");
        password = Console.ReadLine();
    }

    private void RegisterInput(out string? login, out string? password, out string? email)
    {
        LoginInput(out login, out password);
        Console.WriteLine("Enter email: \n");
        email = Console.ReadLine();
    }

    private bool InputIsValid(string? login, string? password) =>
        login != string.Empty && password != string.Empty;

    public static void Login(LoginResponse? loginResponse)
    {
        if (loginResponse is null)
        {
            Console.WriteLine("Null response");
            return;
        }
        if (loginResponse.LoggedIn)
        {
            Username = loginResponse.Username;
        }
        Console.WriteLine(loginResponse.Message);
    }

    public void Logout()
    {
        var logoutRequest = new LogoutRequest()
        {
            OperationType = OperationType.Logout.ToString(),
            Login = Username
                
        };
        RabbitMq.PublicMessage(ExchangeNode, RoutingKey, logoutRequest);
    }
}