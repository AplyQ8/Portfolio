using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using ClientBackLogic;

namespace WPF_Client
{
    internal static class Program
    {
        /// <summary>
        /// Главная точка входа для приложения.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Client_NotAuthorized());
            AppDomain.CurrentDomain.ProcessExit += (s, e) => RabbitMq.Instance().Unbind();
        }
    }
}
