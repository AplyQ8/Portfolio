namespace SportCenter_Client.Entities;

public class SubscriptionEntity
{
    public long id { get; set; }

    public string name { get; set; }

    public string type { get; set; }

    public int price { get; set; }
    
    public string description { get; set; }
}