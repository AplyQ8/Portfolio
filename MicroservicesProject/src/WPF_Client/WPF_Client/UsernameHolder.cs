using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WPF_Client
{
    public  class UsernameHolder
    {
        public string Username { get; set; }

        private static UsernameHolder _instance;

        public static UsernameHolder Instance()
        {
            if(_instance is null)
                _instance = new UsernameHolder();
            return _instance;
        }

        public void SetEmpty() => Username = null;
    }
}
