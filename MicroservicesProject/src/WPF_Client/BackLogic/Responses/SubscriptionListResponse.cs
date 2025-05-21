using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BackLogic.Entities;

namespace BackLogic.Responses
{
    public class SubscriptionListResponse
    {
        public List<SubscriptionEntity> Subscriptions { get; set; }
    }
}
