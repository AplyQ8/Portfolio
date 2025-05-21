using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientBackLogic.Requests
{
    public class PaymentConfirmationRequest
    {
        public string username {  get; set; }
        public string userGuid { get; set; }
        public bool confirm {  get; set; }

        public PaymentConfirmationRequest(string username, string userGuid, bool confirm)
        {
            this.username = username;
            this.confirm = confirm;
            this.userGuid = userGuid;
        }
    }
}
