using SportCenter_Client.Services;
using SportCenter_Client.Services.UserManagementService;

namespace SportCenter_Client;

public class ConsoleApplication
{
    private static ConsoleApplication _instance;

    public ConsoleApplication GetInstance()
    {
        return _instance ??= new ConsoleApplication();
    }
    private void DisplayMenu()
    {
        Console.WriteLine("1. Subscriptions");
        Console.WriteLine("2. Login in");
        Console.WriteLine("3. Register");
        Console.WriteLine("4. Exit");
    }

    public void StartApplication()
    {
        while (true)
        {
            DisplayMenu();
            Console.Write("Enter your choice (1-3): \n");
            string? choice = Console.ReadLine();
            if (!HandleInput(choice))
                return;

        }
    }
    private bool HandleInput(string? choice)
    {
        switch (choice)
        {
            case "1":
                Console.Clear();
                SubscriptionService.GetInstance().ViewActionList();
                break;
            case "2":
                Console.Clear();
                UserManagementService.GetInstance().SendRequest(OperationType.Login);
                break;
            case "3":
                Console.Clear();
                UserManagementService.GetInstance().SendRequest(OperationType.Registration);
                break;
            case "4":
                UserManagementService.GetInstance().Logout();
                return false;
            default:
                Console.Clear();
                Console.WriteLine("Invalid Operation");
                break;
        }

        return true;
    }
    
    
    
    
}