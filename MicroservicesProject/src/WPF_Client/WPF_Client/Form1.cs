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

namespace WPF_Client
{
    public partial class Client_NotAuthorized : Form
    {
        private LoginForm loginForm;
        private RegistrationForm registrationForm;

        private static Client_NotAuthorized _instance;

        public static Client_NotAuthorized GetForm()
        {
            if (_instance == null || _instance.IsDisposed)
                _instance = new Client_NotAuthorized();
            return _instance;
        }
        public Client_NotAuthorized()
        {
            InitializeComponent();
            
            registrationForm = new RegistrationForm(this);
            loginForm = new LoginForm(this, registrationForm);
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

        private void Registration_Button_Click(object sender, EventArgs e)
        {
            this.Hide();

            registrationForm.Show();
        }

        private void Login_Button_Click(object sender, EventArgs e)
        {
            this.Hide();

            loginForm.Show();
        }

        private void Subscriptions_Button_Click(object sender, EventArgs e)
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
