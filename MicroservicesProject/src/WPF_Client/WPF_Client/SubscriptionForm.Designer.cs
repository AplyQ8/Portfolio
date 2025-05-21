namespace WPF_Client
{
    partial class SubscriptionForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.ShowSubscriptions_Button = new System.Windows.Forms.Button();
            this.GoBack_Button = new System.Windows.Forms.Button();
            this.YourSubscriptions_Button = new System.Windows.Forms.Button();
            this.flowLayoutPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.InfoLabel = new System.Windows.Forms.Label();
            this.ArrangeSubscription_Button = new System.Windows.Forms.Button();
            this.ErrorLabel = new System.Windows.Forms.Label();
            this.SubscriptionListLabel = new System.Windows.Forms.Label();
            this.GoToPayment_Button = new System.Windows.Forms.Button();
            this.Success_label = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // ShowSubscriptions_Button
            // 
            this.ShowSubscriptions_Button.Location = new System.Drawing.Point(12, 75);
            this.ShowSubscriptions_Button.Name = "ShowSubscriptions_Button";
            this.ShowSubscriptions_Button.Size = new System.Drawing.Size(151, 40);
            this.ShowSubscriptions_Button.TabIndex = 0;
            this.ShowSubscriptions_Button.Text = "Show Subscriptions";
            this.ShowSubscriptions_Button.UseVisualStyleBackColor = true;
            this.ShowSubscriptions_Button.Click += new System.EventHandler(this.ShowSubscriptions_Button_Click);
            // 
            // GoBack_Button
            // 
            this.GoBack_Button.Location = new System.Drawing.Point(13, 13);
            this.GoBack_Button.Name = "GoBack_Button";
            this.GoBack_Button.Size = new System.Drawing.Size(75, 23);
            this.GoBack_Button.TabIndex = 1;
            this.GoBack_Button.Text = "Back";
            this.GoBack_Button.UseVisualStyleBackColor = true;
            this.GoBack_Button.Click += new System.EventHandler(this.GoBack_Button_Click);
            // 
            // YourSubscriptions_Button
            // 
            this.YourSubscriptions_Button.Location = new System.Drawing.Point(13, 143);
            this.YourSubscriptions_Button.Name = "YourSubscriptions_Button";
            this.YourSubscriptions_Button.Size = new System.Drawing.Size(150, 38);
            this.YourSubscriptions_Button.TabIndex = 2;
            this.YourSubscriptions_Button.Text = "Your Subscriptions";
            this.YourSubscriptions_Button.UseVisualStyleBackColor = true;
            // 
            // flowLayoutPanel
            // 
            this.flowLayoutPanel.FlowDirection = System.Windows.Forms.FlowDirection.TopDown;
            this.flowLayoutPanel.Location = new System.Drawing.Point(285, 65);
            this.flowLayoutPanel.Name = "flowLayoutPanel";
            this.flowLayoutPanel.Size = new System.Drawing.Size(261, 429);
            this.flowLayoutPanel.TabIndex = 3;
            // 
            // InfoLabel
            // 
            this.InfoLabel.AutoSize = true;
            this.InfoLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.InfoLabel.Location = new System.Drawing.Point(585, 13);
            this.InfoLabel.Name = "InfoLabel";
            this.InfoLabel.Size = new System.Drawing.Size(58, 22);
            this.InfoLabel.TabIndex = 4;
            this.InfoLabel.Text = "label1";
            // 
            // ArrangeSubscription_Button
            // 
            this.ArrangeSubscription_Button.Location = new System.Drawing.Point(588, 442);
            this.ArrangeSubscription_Button.Name = "ArrangeSubscription_Button";
            this.ArrangeSubscription_Button.Size = new System.Drawing.Size(221, 51);
            this.ArrangeSubscription_Button.TabIndex = 5;
            this.ArrangeSubscription_Button.Text = "Arrange Subscription";
            this.ArrangeSubscription_Button.UseVisualStyleBackColor = true;
            this.ArrangeSubscription_Button.Click += new System.EventHandler(this.ArrangeSubscription_Button_Click);
            // 
            // ErrorLabel
            // 
            this.ErrorLabel.AutoSize = true;
            this.ErrorLabel.BackColor = System.Drawing.Color.White;
            this.ErrorLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.ErrorLabel.ForeColor = System.Drawing.Color.Red;
            this.ErrorLabel.Location = new System.Drawing.Point(12, 472);
            this.ErrorLabel.Name = "ErrorLabel";
            this.ErrorLabel.Size = new System.Drawing.Size(64, 25);
            this.ErrorLabel.TabIndex = 6;
            this.ErrorLabel.Text = "label1";
            // 
            // SubscriptionListLabel
            // 
            this.SubscriptionListLabel.AutoSize = true;
            this.SubscriptionListLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.SubscriptionListLabel.Location = new System.Drawing.Point(285, 25);
            this.SubscriptionListLabel.Name = "SubscriptionListLabel";
            this.SubscriptionListLabel.Size = new System.Drawing.Size(116, 20);
            this.SubscriptionListLabel.TabIndex = 7;
            this.SubscriptionListLabel.Text = "Subscriptions:";
            // 
            // GoToPayment_Button
            // 
            this.GoToPayment_Button.Location = new System.Drawing.Point(855, 442);
            this.GoToPayment_Button.Name = "GoToPayment_Button";
            this.GoToPayment_Button.Size = new System.Drawing.Size(197, 51);
            this.GoToPayment_Button.TabIndex = 8;
            this.GoToPayment_Button.Text = "Go to payment form";
            this.GoToPayment_Button.UseVisualStyleBackColor = true;
            this.GoToPayment_Button.Click += new System.EventHandler(this.GoToPayment_Button_Click);
            // 
            // Success_label
            // 
            this.Success_label.AutoSize = true;
            this.Success_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.Success_label.ForeColor = System.Drawing.Color.DarkGreen;
            this.Success_label.Location = new System.Drawing.Point(12, 442);
            this.Success_label.Name = "Success_label";
            this.Success_label.Size = new System.Drawing.Size(64, 25);
            this.Success_label.TabIndex = 9;
            this.Success_label.Text = "label1";
            // 
            // SubscriptionForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1064, 506);
            this.Controls.Add(this.Success_label);
            this.Controls.Add(this.GoToPayment_Button);
            this.Controls.Add(this.SubscriptionListLabel);
            this.Controls.Add(this.ErrorLabel);
            this.Controls.Add(this.ArrangeSubscription_Button);
            this.Controls.Add(this.InfoLabel);
            this.Controls.Add(this.flowLayoutPanel);
            this.Controls.Add(this.YourSubscriptions_Button);
            this.Controls.Add(this.GoBack_Button);
            this.Controls.Add(this.ShowSubscriptions_Button);
            this.Name = "SubscriptionForm";
            this.Text = "SubscriptionForm";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button ShowSubscriptions_Button;
        private System.Windows.Forms.Button GoBack_Button;
        private System.Windows.Forms.Button YourSubscriptions_Button;
        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel;
        private System.Windows.Forms.Label InfoLabel;
        private System.Windows.Forms.Button ArrangeSubscription_Button;
        private System.Windows.Forms.Label ErrorLabel;
        private System.Windows.Forms.Label SubscriptionListLabel;
        private System.Windows.Forms.Button GoToPayment_Button;
        private System.Windows.Forms.Label Success_label;
    }
}