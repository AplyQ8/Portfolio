using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientBackLogic.PaymentResponses
{
    public class PaymentInformationResponse
    {
        public string username { get; set; }
        public float price { get; set; }
        public string purchase {  get; set; }
        public string urlPaymentConfirmation { get; set; }
    }
}
