using Newtonsoft.Json;
using SportCenter_Client.Responses;

namespace SportCenter_Client;

public class JsonParser
{
    public static BaseResponse? ParseInBaseResponse(string json)
    {
        return JsonConvert.DeserializeObject<BaseResponse>(json);
    }

    public static LoginResponse? ParseInLoginResponse(string json)
    {
        return JsonConvert.DeserializeObject<LoginResponse>(json);
    }

    public static SubscriptionListResponse? ParseInSubscriptionList(string json)
    {
        return JsonConvert.DeserializeObject<SubscriptionListResponse>(json);
    }
}