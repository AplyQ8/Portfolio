﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientBackLogic.Requests
{
    public class LoginRequest: BaseRequest
    {
        public string Login { get; set; }
        public string Password { get; set; }
    }
}
