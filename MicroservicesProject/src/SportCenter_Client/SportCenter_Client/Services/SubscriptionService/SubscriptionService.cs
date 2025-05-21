using System.Diagnostics;
using Newtonsoft.Json;
using SportCenter_Client.Entities;
using SportCenter_Client.Responses;

namespace SportCenter_Client.Services;

public class SubscriptionService
{
    private static SubscriptionService _instance;
    
    private readonly string _exchangeNode = "Subscription";
    private readonly string _routingKey = "";

    public static SubscriptionService GetInstance()
    {
        return _instance ??= new SubscriptionService();
    }

    public void SendMessage(OperationType operation)
    {
        BaseRequest request;
        switch (operation)
        {
            case OperationType.Subscribe:
                request = new SubscribeRequest
                {
                    OperationType = OperationType.Subscribe.ToString(),
                    Username = UserManagementService.UserManagementService.Username,
                    SubscriptionType = SubscriptionType.Ordinary.ToString()
                };
                RabbitMq.PublicMessage(_exchangeNode, _routingKey, request);
                break;
            case OperationType.SubscriptionList:
                request = new BaseRequest()
                {
                    OperationType = OperationType.SubscriptionList.ToString()
                };
                RabbitMq.PublicMessage(_exchangeNode, _routingKey, request);
                break;
            default:
                Console.WriteLine("Invalid Operation");
                return;
        }
        
    }

    public void ViewActionList()
    {
        Console.WriteLine("1. Show available subscriptions");
        Console.WriteLine("2. Show your subscriptions");
        Console.WriteLine("3. Go back");

        string? input = Console.ReadLine();
        switch (input)
        {
            case "1":
                SendMessage(OperationType.SubscriptionList);
                break;
            case "2":
                break;
            case "3":
                return;
            default:
                break;
        }
        
    }

    public void ShowSubscriptions(SubscriptionListResponse? response)
    {
        if (response is null)
            return;
        if (response.Subscriptions.Count == 0)
        {
            Console.WriteLine("No subscriptions available...");
            Console.ReadKey();
            return;
        }

        for (int i = 0; i < response.Subscriptions.Count; i++)
        {
            ShowSubscriptionInfo(response.Subscriptions[i], i+1);
        }
        int lineLength = 20;
        string line = new string('#', Math.Min(Console.WindowWidth - 1, lineLength));
        Console.WriteLine(line);
        for (int i = 0; i < response.Subscriptions.Count; i++)
        {
            Console.WriteLine($"{i+1}. Subscribe to {response.Subscriptions[i].name}");
        }
        Console.WriteLine($"{response.Subscriptions.Count + 1}. Exit");
        SubscriptionInput();
        
    }

    private void ShowSubscriptionInfo(SubscriptionEntity subscription, int orderNumber)
    {
        int lineLength = 20;
        string line = new string('#', Math.Min(Console.WindowWidth - 1, lineLength));
        Console.WriteLine(line);
        Console.WriteLine($"{orderNumber}. ");
        Console.WriteLine($"Name: {subscription.name}");
        Console.WriteLine($"Payment type: {subscription.type}");
        Console.WriteLine($"Price: {subscription.price}$");
        Console.WriteLine($"Description: {subscription.description}");
    }

    private void SubscriptionInput()
    {
        int number;
        bool inputIsNumber = false;
        
        while (!inputIsNumber)
        {
            Console.Write("Choose options: ");
            string? input = Console.ReadLine();

            inputIsNumber = int.TryParse(input, out number);

            if (inputIsNumber)
            {
                Console.WriteLine($"Вы ввели число: {number}");
                return;
            }
            else
            {
                Console.WriteLine("Input is not a number. try again");
            }
        }
    }
}