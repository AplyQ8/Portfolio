using ClientBackLogic;
using ClientBackLogic.Entities;
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
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WPF_Client
{
    public partial class SubscriptionForm : Form
    {
        private static SubscriptionForm _instance;
        private Form previousForm;
        private List<SubscriptionEntity> subscriptions;

        private readonly string _exchangeNode = "Subscription";
        private readonly string _routingKey = "";

        private Button currentClickedSubscriptionButton;

        private bool _messageReceived;

        private PaymentServiceInfoResponse _paymentServiceInfoResponse;

        public static SubscriptionForm GetForm()
        {
            if (_instance == null || _instance.IsDisposed)
                _instance = new SubscriptionForm();
            return _instance;
        }
        public SubscriptionForm()
        {
            InitializeComponent();
            this.FormClosing += FormClose;
        }

        private void ShowSubscriptions_Button_Click(object sender, EventArgs e)
        {
            flowLayoutPanel.Controls.Clear();
            var request = new BaseRequest()
            {
                OperationType = OperationType.SubscriptionList.ToString()
            };
            RabbitMq.Instance().Consumer.Received += OnMessageReceived;
            RabbitMq.Instance().StartConsuming();
            RabbitMq.PublicMessage(_exchangeNode, _routingKey, request);
        }

        private void GoBack_Button_Click(object sender, EventArgs e)
        {
            RabbitMq.Instance().Consumer.Received -= OnMessageReceived;
            this.Hide();
            flowLayoutPanel.Controls.Clear();
            InfoLabel.Text = string.Empty;
            
            UpdateErrorLabel(string.Empty);

            previousForm.Show();
        }

        #region Show Methods
        public static void ShowForm(Form previousForm)
        {
            var form = GetForm();
            if (form.InvokeRequired)
            {
                form.BeginInvoke(new Action(() => form.Show()));
            }
            else
            {
                form.Show();
                form.AssignPreviousForm(previousForm);
            }
        }
        public new void Show()
        {
            InfoLabel.Text = string.Empty;
            ArrangeSubscription_Button.Enabled = false;
            ErrorLabel.Text = string.Empty;
            currentClickedSubscriptionButton = null;
            GoToPayment_Button.Enabled = false;
            Success_label.Text = string.Empty;
            base.Show();
        }
        public void AssignPreviousForm(Form previousForm) => this.previousForm = previousForm;
        #endregion

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

        private void OnMessageReceived(object model, BasicDeliverEventArgs ea)
        {
            _messageReceived = true;
            var body = ea.Body.ToArray();
            var message = Encoding.UTF8.GetString(body);
            var baseResponse = JsonParser.ParseInBaseResponse(message);
            if (baseResponse is null)
            {
                Console.WriteLine("Null response");
                return;
            }

            ParseMessage(message, baseResponse);
        }

        private void HandleSubscriptionListResponse(SubscriptionListResponse response)
        {
            if(response is null)
            {
                UpdateErrorLabel("Something went wrong...");
                return;
            }
            if(response.Subscriptions.Count == 0)
            {
                UpdateErrorLabel("No subscriptions available");
                return;
            }
            subscriptions = response.Subscriptions;
            for(int i = 0; i < subscriptions.Count; i++)
            {
                Button subscriptionButton = new Button();
                subscriptionButton.Text = subscriptions[i].name;
                subscriptionButton.Tag = i;
                subscriptionButton.Click += SubscriptionButtonClickEvent;
                AddToPanelControl(subscriptionButton);
            }
        }

        private void HandleSubscribeResponse(SubscribeResponse response)
        {
            if(response is null)
            {
                UpdateErrorLabel("Something went wrong..");
                return;
            }
            if (!response.isSubscribed)
            {
                UpdateErrorLabel(response.Message);
                return;
            }
            UpdateSuccessLabel(response.Message);
        }

        private void HandlePaymentInfo(PaymentServiceInfoResponse response)
        {
            if(response is null)
            {
                UpdateErrorLabel("Something went wrong!");
                return;
            }
            RabbitMq.Instance().Consumer.Received -= OnMessageReceived;

            //PaymentForm.ShowForm(response, this);
            UpdateSuccessLabel(response.Message);
            UpdateGoToPaymentButton(true);
            _paymentServiceInfoResponse = response;
        }

        private void SubscriptionButtonClickEvent(object sender, EventArgs ea)
        {
            Button button = (Button)sender;
            currentClickedSubscriptionButton = button;
            ArrangeSubscription_Button.Enabled = true;
            StringBuilder stringBuilder = new StringBuilder();
            int subscriptionId = int.Parse(button.Tag.ToString());
            stringBuilder.AppendLine($"Price: {subscriptions[subscriptionId].price}");
            stringBuilder.AppendLine($"Payment type: {subscriptions[subscriptionId].type}");
            stringBuilder.AppendLine($"Description: {subscriptions[subscriptionId].description}");
            InfoLabel.Text = stringBuilder.ToString();
        }

        private void AddToPanelControl(Button button)
        {
            if (flowLayoutPanel.InvokeRequired)
            {
                flowLayoutPanel.BeginInvoke(new Action(() => AddToPanelControl(button)));
            }
            else
            {
                flowLayoutPanel.Controls.Add(button);
            }
        }

        private void ParseMessage(string message, BaseResponse baseResponse)
        {
            ResponseType responseType = (ResponseType)Enum.Parse(typeof(ResponseType), baseResponse.ResponseType);

            switch (responseType)
            {
                case ResponseType.SubscriptionList:
                    HandleSubscriptionListResponse(JsonParser.ParseInSubscriptionList(message));
                    break;
                case ResponseType.PaymentServiceInfo:
                    HandlePaymentInfo(JsonParser.ParseInPaymentServiceInfoResponse(message));
                    break;
                case ResponseType.SubscribeOperation:
                    HandleSubscribeResponse(JsonParser.ParseInSubscribeResponse(message));
                    break;
                default:
                    UpdateErrorLabel("Something went wrong...");
                    return;
            }
        }

        private void ArrangeSubscription_Button_Click(object sender, EventArgs e)
        {
            
            int subscriptionId = int.Parse(currentClickedSubscriptionButton.Tag.ToString());
            var request = new SubscribeRequest()
            {
                OperationType = OperationType.Subscribe.ToString(),
                Username = UsernameHolder.Instance().Username,
                subscriptionID = subscriptions[subscriptionId].id
            };
            RabbitMq.PublicMessage(_exchangeNode, _routingKey, request);
            BlockApplicationThreadUntilMessageReceived();
            
        }

        public void FormClose(object sender, EventArgs e)
        {
            Application.Exit();
            RabbitMq.Instance().Unbind();
        }

        private void BlockApplicationThreadUntilMessageReceived()
        {
            _messageReceived = false;
            while (!_messageReceived)
            {
                System.Threading.Thread.Sleep(100);
            }
        }

        private void HideForm()
        {
            var form = GetForm();
            if (form.InvokeRequired)
            {
                form.BeginInvoke(new Action(() => form.Hide()));
            }
            else
            {
                form.Hide();
            }
        }

        private void GoToPayment_Button_Click(object sender, EventArgs e)
        {
            //this.Hide();
            GoToPayment_Button.Enabled = false;
            Success_label.Text = string.Empty;
            ErrorLabel.Text = string.Empty;
            PaymentForm.ShowForm(_paymentServiceInfoResponse, this);
        }

        private void UpdateGoToPaymentButton(bool isEnabled)
        {
            if (GoToPayment_Button.InvokeRequired)
            {
                GoToPayment_Button.BeginInvoke(new Action(() => UpdateGoToPaymentButton(isEnabled)));
            }
            else
            {
                GoToPayment_Button.Enabled = isEnabled;
            }
        }
        private void UpdateSuccessLabel(string message)
        {
            if (Success_label.InvokeRequired)
            {
                Success_label.BeginInvoke(new Action(() => UpdateSuccessLabel(message)));
            }
            else
            {
                Success_label.Text = message;
            }
        }
    }
}
