using ClientBackLogic.Requests;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.ListView;
using RabbitMQ.Client.Events;
using ClientBackLogic;
using ClientBackLogic.Responses;

namespace WPF_Client
{
    public partial class RegistrationForm : Form
    {
        private readonly string ExchangeNode = "UserManagement";
        private static readonly string RoutingKey = "";
        private Client_NotAuthorized client_NA;
        private RegistrationForm form;
        
        public RegistrationForm(Client_NotAuthorized client_NA)
        {
            InitializeComponent();
            this.client_NA = client_NA;
            form = this;
            this.FormClosing += FormClose;
        }

        private void GoBack_button_Click(object sender, EventArgs e)
        {
            RabbitMq.Instance().Consumer.Received -= OnMessageReceived;
            this.Hide();
            username_textBox.Text = string.Empty;
            Password_textBox.Text = string.Empty;
            ConfirmPassword_textBox.Text = string.Empty;
            Email_textBox.Text = string.Empty;
            client_NA.Show();
        }

        private void RegistrationButton_Click(object sender, EventArgs e)
        {
            if(username_textBox.Text == string.Empty || Password_textBox.Text == string.Empty)
            {
                ErrorLabel.Text = "Username or passowrd cannot be empty!";
                return;
            }
            if(Password_textBox.Text.Trim() != ConfirmPassword_textBox.Text.Trim())
            {
                ErrorLabel.Text = "Password does not match!";
                return;
            }

            var registerRequest = new RegistrationRequest()
            {
                OperationType = OperationType.Registration.ToString(),
                Login = username_textBox.Text,
                Password = Password_textBox.Text,
                Email = Email_textBox.Text
            };

            RabbitMq.Instance().Consumer.Received += OnMessageReceived;
            RabbitMq.Instance().StartConsuming();

            RabbitMq.PublicMessage(ExchangeNode, RoutingKey, registerRequest);

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
                case ResponseType.Registration:
                    HandleResponse(JsonParser.ParseInRegisterResponse(message));
                    break;
                default:
                    UpdateErrorLabel("Something went wrong...");
                    return;
            }


        }

        private void HandleResponse(RegistrationResponse response)
        {
            if (!response.IsRegistered)
            {
                
                UpdateErrorLabel(response.Message);
                return;
            }
            UpdateErrorLabel(response.Message);
            RabbitMq.Instance().Consumer.Received -= OnMessageReceived;
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

        private void RegistrationForm_Load(object sender, EventArgs e)
        {
            username_textBox.Text = string.Empty;
            Password_textBox.Text = string.Empty;
            ConfirmPassword_textBox.Text = string.Empty;
            Email_textBox.Text = string.Empty;
        }
        public void FormClose(object sender, EventArgs e)
        {
            Application.Exit();
            RabbitMq.Instance().Unbind();
        }
    }

   
}
