using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientBackLogic.Requests
{
    public class RegistrationRequest: BaseRequest
    {
        public string Login { get; set; }

        public string Password { get; set; }

        public string Email { get; set; }
    }
}
