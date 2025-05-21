using ClientBackLogic;
using ClientBackLogic.Requests;
using ClientBackLogic.Responses;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel;

namespace WPF_Client
{
    public partial class LoginForm : Form
    {
        private Client_NotAuthorized client_NA;
        private RegistrationForm registrationForm;
        private readonly string ExchangeNode = "UserManagement";
        private static readonly string RoutingKey = "";

        public LoginForm(Client_NotAuthorized client_notAuthorizedForm, RegistrationForm registrationForm)
        {
            InitializeComponent();
            client_NA = client_notAuthorizedForm;
            this.registrationForm = registrationForm;
            this.FormClosing += FormClose;
        }

        private void GoBack_button_Click(object sender, EventArgs e)
        {
            this.Hide();
            Username_textBox.Text = string.Empty;
            Password_textBox.Text = string.Empty;

            RabbitMq.Instance().Consumer.Received -= OnMessageReceived;

            client_NA.Show();
        }

        private void Registration_Button_Click(object sender, EventArgs e)
        {
            this.Hide();
            Username_textBox.Text = string.Empty;
            Password_textBox.Text = string.Empty;

            RabbitMq.Instance().Consumer.Received -= OnMessageReceived;

            registrationForm.Show();
        }

        private void LoginForm_Load(object sender, EventArgs e)
        {

        }

        private void OnMessageReceived(object model, BasicDeliverEventArgs ea)
        {
            var body = ea.Body.ToArray();
            var message = Encoding.UTF8.GetString(body);
            var baseResponse = JsonParser.ParseInBaseResponse(message);
            if (baseResponse is null)
            {
                Console.WriteLine("Null response");
                return;
            }

            ResponseType responseType = (ResponseType)Enum.Parse(typeof(ResponseType), baseResponse.ResponseType);

            switch (responseType)
            {
                case ResponseType.Login:
                    HandleResponse(JsonParser.ParseInLoginResponse(message)); 
                    break;
                default:
                    UpdateErrorLabel("Something went wrong...");
                    return;
            }


        }

        private void UpdateErrorLabel(string errorMessage)
        {
            if (ErrorLabel.InvokeRequired)
            {
                // Если это не поток UI, вызываем делегат в потоке UI асинхронно
                ErrorLabel.BeginInvoke(new Action(() => UpdateErrorLabel(errorMessage)));
            }
            else
            {
                // Если это поток UI, обновляем ErrorLabel
                ErrorLabel.Text = errorMessage;
            }
        }

        private void Login_Button_Click(object sender, EventArgs e)
        {
            if (Username_textBox.Text == string.Empty)
            {
                UpdateErrorLabel("Username field can not be empty!");
                return;
            }

            if(Password_textBox.Text == string.Empty)
            {
                UpdateErrorLabel("Password field can not be empty!");
                return;
            }

            var loginRequest = new LoginRequest()
            {
                OperationType = OperationType.Login.ToString(),
                Login = Username_textBox.Text,
                Password = Password_textBox.Text
            };

            RabbitMq.Instance().Consumer.Received += OnMessageReceived;
            RabbitMq.Instance().StartConsuming();

            RabbitMq.PublicMessage(ExchangeNode, RoutingKey, loginRequest);
        }

        private void HandleResponse(LoginResponse response)
        {
            if (response is null)
            {
                UpdateErrorLabel("Something went wrong...");
                return;
            }
            if (!response.LoggedIn)
            {
                UpdateErrorLabel(response.Message);
                return;
            }
            UsernameHolder.Instance().Username = response.Username;
            RabbitMq.Instance().Consumer.Received -= OnMessageReceived;
            HideForm();

            
            this.BeginInvoke(new Action(() =>
            {
                Client_Authorized.ShowForm();
            }));

        }

        private void HideForm()
        {
            if (this.InvokeRequired)
            {
                // Если это не поток UI, вызываем делегат в потоке UI асинхронно
                this.BeginInvoke(new Action(() => HideForm()));
            }
            else
            {
                // Если это поток UI, скрываем форму
                this.Hide();
            }
        }

        public void FormClose(object sender, EventArgs e)
        {
            Application.Exit();
            RabbitMq.Instance().Unbind();
        }

    }
}
