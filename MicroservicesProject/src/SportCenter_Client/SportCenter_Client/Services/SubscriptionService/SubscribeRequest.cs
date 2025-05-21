namespace SportCenter_Client.Services;

public class SubscribeRequest: BaseRequest
{
    public string Username { get; set; }
    public string SubscriptionType { get; set; }
}