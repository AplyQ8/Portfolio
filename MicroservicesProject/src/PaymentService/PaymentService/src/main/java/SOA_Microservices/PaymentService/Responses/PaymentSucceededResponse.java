package SOA_Microservices.PaymentService.Responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentSucceededResponse {
    @JsonProperty("Sender")
    public String Sender;
    @JsonProperty("OperationType")
    public String OperationType;
    @JsonProperty("userGuid")
    public String userGuid;
    public String username;
    public Long purchase_id;

    public PaymentSucceededResponse(String sender, String operationType, String username, Long purchase_id, String userGuid) {
        Sender = sender;
        OperationType = operationType;
        this.username = username;
        this.purchase_id = purchase_id;
        this.userGuid = userGuid;
    }
}
