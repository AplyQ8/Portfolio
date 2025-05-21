using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BackLogic.Requests
{
    public class BaseRequest
    {
        public string Guid { get; set; }
        public string OperationType { get; set; }
    }
}
