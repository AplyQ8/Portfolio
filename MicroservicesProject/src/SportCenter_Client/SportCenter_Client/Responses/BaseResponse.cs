namespace SportCenter_Client.Responses;

public class BaseResponse
{
    public string Sender { get; set; }
    public string ResponseType { get; set; }
    public string Message { get; set; }
}