using ClientBackLogic;
using ClientBackLogic.PaymentResponses;
using ClientBackLogic.Requests;
using ClientBackLogic.Responses;
using RestSharp;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net.Http;
using System.Security.Policy;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel;

namespace WPF_Client
{
    public partial class PaymentForm : Form
    {
        private static PaymentForm _instance;

        private static string _paymentServiceURL;
        private Form previousForm;

        private string _confimationURL;
        

        public PaymentForm()
        {
            InitializeComponent();
            this.FormClosing += AbortPayment;
        }

        public static PaymentForm GetForm()
        {
            if (_instance == null || _instance.IsDisposed)
                _instance = new PaymentForm();
            return _instance;
        }

        #region Show Methods

        public static void ShowForm(PaymentServiceInfoResponse paymentServiceInfoResponse, Form previousForm)
        {
            var form = GetForm();
            _paymentServiceURL = paymentServiceInfoResponse.paymentServiceInfo;
            

            if (form.InvokeRequired)
            {
                form.BeginInvoke(new Action(() => form.Show()));
            }
            else
            {
                form.Show();
                form.AssignPreviousForm(previousForm);
                form.MakeGetRequestToPaymentService();
            }
        }
        public new void Show()
        {

            UpdateConfirmButtonActivity(false);
            UpdatePurchaseLabelVisibility(false);
            UpdatePriceLabelVisibility(false);
            UpdatePriceLabel(string.Empty);
            UpdatePurchaseNameLabel(string.Empty);

            UpdateErrorLabel(string.Empty);
            UpdateSuccessLabel(string.Empty);

            base.Show();
        }

        public void HideForm()
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

        public void AssignPreviousForm(Form previousForm) => this.previousForm = previousForm;

        #endregion

        private void Confirmation_Button_Click(object sender, EventArgs e)
        {
            MakeGetConfimationRequest(true);
        }

        private void MakeGetRequestToPaymentService()
        {
            string getRequestUrl = $"{_paymentServiceURL}";

            var client = new RestClient(getRequestUrl);

            var request = new RestRequest($"/{UsernameHolder.Instance().Username}");

            var response = client.ExecuteGet<string>(request);

            if (response.IsSuccessStatusCode)
            {
                //string content = await response.Content.ReadAsStringAsync();
                string content = response.Content;
                PaymentInformationResponse payment = JsonParser.ParseInPaymentInfoResponse(content);
                UpdateConfirmButtonActivity(true);
                UpdatePurchaseLabelVisibility(true);
                UpdatePriceLabelVisibility(true);
                UpdatePriceLabel(payment.price.ToString());
                UpdatePurchaseNameLabel(payment.purchase);
                _confimationURL = payment.urlPaymentConfirmation;

            }
            else
            {
                MessageBox.Show("Failed to retrieve payment info", "Error");
                HideForm();
            }
        }

        private void MakeGetConfimationRequest(bool isConfirmed)
        {
            var confimationRequest = 
                new PaymentConfirmationRequest(
                    UsernameHolder.Instance().Username,
                    RabbitMq.Guid.ToString(),
                    isConfirmed
                    );
            var client = new RestClient(_confimationURL);
            //var request = new RestRequest($"/{confimationRequest}");

            var request = new RestRequest("/confirm", Method.Post);

            request.AddJsonBody(confimationRequest);

            var response = client.Execute<bool>(request);

            if (response.IsSuccessStatusCode)
            {
                UpdateSuccessLabel("Payment succeeded!");

            }
            else
            {
                UpdateErrorLabel($"{response.Content}");
            }
        }
        private void AbortPayment(object sender, EventArgs e)
        {
            MakeGetConfimationRequest(false);
        }

        #region Update labels and buttons

        private void UpdatePurchaseLabelVisibility(bool isVisible)
        {
            if (Purchase_Label.InvokeRequired)
            {
                // Если это не поток UI, вызываем делегат в потоке UI асинхронно
                Purchase_Label.BeginInvoke(new Action(() => UpdatePurchaseLabelVisibility(isVisible)));
            }
            else
            {
                // Если это поток UI, обновляем ErrorLabel
                Purchase_Label.Visible = isVisible;
            }
        }

        private void UpdatePriceLabelVisibility(bool isVisible)
        {
            if (Price_Label.InvokeRequired)
            {
                Price_Label.BeginInvoke(new Action(() => UpdatePriceLabelVisibility(isVisible)));
            }
            else
            {
                Price_Label.Visible = isVisible;
            }
        }

        private void UpdatePurchaseNameLabel(string purchaseName)
        {
            if (PurchaseName_Label.InvokeRequired)
            {
                PurchaseName_Label.BeginInvoke(new Action(() => UpdatePurchaseNameLabel(purchaseName)));
            }
            else
            {
                PurchaseName_Label.Text = purchaseName;
            }
        }

        private void UpdatePriceLabel(string price)
        {
            if (purchasePrice_Label.InvokeRequired)
            {
                purchasePrice_Label.BeginInvoke(new Action(() => UpdatePriceLabel(price)));
            }
            else
            {
                purchasePrice_Label.Text= price;
            }
        }

        private void UpdateConfirmButtonActivity(bool isEnabled)
        {
            if (Confirmation_Button.InvokeRequired)
            {
                Confirmation_Button.BeginInvoke(new Action(() => UpdateConfirmButtonActivity(isEnabled)));
            }
            else
            {
                Confirmation_Button.Enabled = isEnabled;
            }
        }

        private void UpdateErrorLabel(string message)
        {
            if (Error_label.InvokeRequired)
            {
                Error_label.BeginInvoke(new Action(() => UpdateErrorLabel(message)));
            }
            else
            {
                Error_label.Text = message;
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

        #endregion

        
    }
}
