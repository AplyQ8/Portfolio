using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientBackLogic.Entities
{
    public class SubscriptionEntity
    {
        public long id { get; set; }

        public string name { get; set; }

        public string type { get; set; }

        public int price { get; set; }

        public string description { get; set; }
    }
}
