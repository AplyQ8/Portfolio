using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientBackLogic.Responses
{
    public class LoginResponse: BaseResponse
    {
        public string Username { get; set; }
        public bool LoggedIn { get; set; }
    }
}
