using ClientBackLogic.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientBackLogic.Responses
{
    public class SubscriptionListResponse: BaseResponse
    {
        public List<SubscriptionEntity> Subscriptions { get; set; }
    }
}
