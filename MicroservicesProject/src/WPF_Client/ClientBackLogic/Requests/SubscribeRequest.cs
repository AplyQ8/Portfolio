using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientBackLogic.Requests
{
    public class SubscribeRequest: BaseRequest
    {
        public string Username { get; set; }
        public long subscriptionID { get; set; }
    }
}
