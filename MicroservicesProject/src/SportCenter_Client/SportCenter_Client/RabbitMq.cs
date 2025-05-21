using System.Text;
using Newtonsoft.Json;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using SportCenter_Client.Responses;
using SportCenter_Client.Services;
using SportCenter_Client.Services.UserManagementService;

namespace SportCenter_Client;

public class RabbitMq
{
    public static Guid Guid { get; private set; }
    private string _globalExchanger = "Client";
    private ConnectionFactory _factory;
    private IConnection _connection;
    private static IModel _channel;

    public RabbitMq()
    {
        Guid = Guid.NewGuid();
        ConnectToMq();
    }
    
    private void ConnectToMq()
    {
        _factory = new ConnectionFactory
        {
            HostName = "localhost",
            UserName = "admin",
            Password = "admin",
            //VirtualHost ="/",
            Port = 5672
        };
        _connection = _factory.CreateConnection();
        _channel = _connection.CreateModel();
    }

    public void DeclareQueue()
    {
        _channel.QueueDeclare(
            queue: Guid.ToString(),
            durable: true,
            exclusive: false,
            autoDelete: true,
            arguments: null);
    }
    public void Bind()
    {
        _channel.QueueBind(
            exchange: "Client",
            queue: Guid.ToString(),
            routingKey: Guid.ToString());
    }
    public void Unbind()
    {
        _channel.QueueUnbind(
            exchange:"Client",
            queue: Guid.ToString(),
            routingKey: Guid.ToString()
            );
    }
    public void StartListeningForQueue()
    {
        var consumer = new EventingBasicConsumer(_channel);
            
        // Define callback for received messages
        consumer.Received += (model, ea) =>
        {
            var body = ea.Body.ToArray();
            var message = Encoding.UTF8.GetString(body);
            Console.WriteLine("Received message: {0}", message);
            HandleResponse(message);
        };
        
        _channel.BasicConsume(queue: Guid.ToString(),
            autoAck: true,
            consumer: consumer);
    }

    public static void PublicMessage(string exchangeNode, string routingKey, BaseRequest request)
    {
        request.Guid = Guid.ToString();
        var message = JsonConvert.SerializeObject(request);
        var body = Encoding.UTF8.GetBytes(message);
        _channel.BasicPublish(
            exchange: exchangeNode,
            routingKey: routingKey,
            basicProperties: null,
            body: body);
    }

    private void HandleResponse(string response)
    {
        var baseResponse = JsonParser.ParseInBaseResponse(response);
        if (baseResponse is null)
        {
            Console.WriteLine("Null response");
            return;
        }

        ResponseType responseType = (ResponseType)Enum.Parse(typeof(ResponseType), baseResponse.ResponseType);
        switch (responseType)
        {
            case ResponseType.Login:
                var loginResponse = JsonParser.ParseInLoginResponse(response);
                UserManagementService.Login(loginResponse);
                break;
            case ResponseType.SubscriptionList:
                var subscriptionListResponse = JsonParser.ParseInSubscriptionList(response);
                SubscriptionService.GetInstance().ShowSubscriptions(subscriptionListResponse);
                break;
            default:
                Console.WriteLine("Invalid operation");
                break;
        }
        
    }
}