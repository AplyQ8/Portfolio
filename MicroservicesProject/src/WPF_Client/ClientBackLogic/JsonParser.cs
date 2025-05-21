using ClientBackLogic.PaymentResponses;
using ClientBackLogic.Responses;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientBackLogic
{
    public  class JsonParser
    {
        public static BaseResponse ParseInBaseResponse(string json)
        {
            return JsonConvert.DeserializeObject<BaseResponse>(json);
        }

        public static LoginResponse ParseInLoginResponse(string json)
        {
            return JsonConvert.DeserializeObject<LoginResponse>(json);
        }

        public static SubscriptionListResponse ParseInSubscriptionList(string json)
        {
            return JsonConvert.DeserializeObject<SubscriptionListResponse>(json);
        }

        public static RegistrationResponse ParseInRegisterResponse(string json)
        {
            return JsonConvert.DeserializeObject<RegistrationResponse>(json);
        }

        public static SubscribeResponse ParseInSubscribeResponse(string json)
        {
            return JsonConvert.DeserializeObject<SubscribeResponse>(json);
        }

        public static PaymentServiceInfoResponse ParseInPaymentServiceInfoResponse(string json)
            => JsonConvert.DeserializeObject<PaymentServiceInfoResponse>(json);

        public static PaymentInformationResponse ParseInPaymentInfoResponse(string json)
            => JsonConvert.DeserializeObject<PaymentInformationResponse>(json);
    }
}
