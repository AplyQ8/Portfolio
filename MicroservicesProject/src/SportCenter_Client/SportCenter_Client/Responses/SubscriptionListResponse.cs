using SportCenter_Client.Entities;

namespace SportCenter_Client.Responses;

public class SubscriptionListResponse: BaseResponse
{
    public List<SubscriptionEntity> Subscriptions { get; set; }
}