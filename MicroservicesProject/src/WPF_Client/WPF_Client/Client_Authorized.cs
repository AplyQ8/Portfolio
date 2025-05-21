using ClientBackLogic.Requests;
using ClientBackLogic;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel;

namespace WPF_Client
{
    public partial class Client_Authorized : Form
    {
        private static Client_Authorized _instance;

        private readonly string ExchangeNode = "UserManagement";
        private static readonly string RoutingKey = "";

        public static Client_Authorized GetForm()
        {
            if(_instance == null || _instance.IsDisposed)
                _instance = new Client_Authorized();
            return _instance;
        }
        public Client_Authorized()
        {
            InitializeComponent();

            this.FormClosing += FormClose;
        }
        public static void ShowForm()
        {
            var form = GetForm();
            if (form.InvokeRequired)
            {
                form.BeginInvoke(new Action(() => form.Show()));
            }
            else
            {
                form.Show();
                
            }
        }
        public new void Show()
        {
            UsernameLabel.Text = UsernameHolder.Instance().Username;
            base.Show();
        }
        private void LogoutButton_Click(object sender, EventArgs e)
        {
            var logoutRequest = new LogoutRequest()
            {
                OperationType = OperationType.Logout.ToString(),
                Login = UsernameHolder.Instance().Username

            };
            RabbitMq.PublicMessage(ExchangeNode, RoutingKey, logoutRequest);
            this.Hide();
            UsernameHolder.Instance().SetEmpty();
            Client_NotAuthorized.ShowForm();
        }

        private void Client_Authorized_Load(object sender, EventArgs e)
        {

        }

        private void SubscriptionButton_Click(object sender, EventArgs e)
        {
            this.Hide();
            SubscriptionForm.ShowForm(this);
        }
        public void FormClose(object sender, EventArgs e)
        {
            Application.Exit();
            RabbitMq.Instance().Unbind();
        }
    }
}
