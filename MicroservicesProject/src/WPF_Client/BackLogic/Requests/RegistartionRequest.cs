﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BackLogic.Requests
{
    public class RegistartionRequest: BaseRequest
    {
        public string Login { get; set; }

        public string Password { get; set; }

        public string Email { get; set; }
    }
}
