﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientBackLogic.Responses
{
    public class BaseResponse
    {
        public string Sender { get; set; }
        public string ResponseType { get; set; }
        public string Message { get; set; }
    }
}
