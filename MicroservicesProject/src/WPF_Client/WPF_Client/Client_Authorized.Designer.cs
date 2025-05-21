namespace WPF_Client
{
    partial class Client_Authorized
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
            this.LogoutButton = new System.Windows.Forms.Button();
            this.UsernameLabel = new System.Windows.Forms.Label();
            this.SubscriptionButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // LogoutButton
            // 
            this.LogoutButton.Location = new System.Drawing.Point(893, 52);
            this.LogoutButton.Name = "LogoutButton";
            this.LogoutButton.Size = new System.Drawing.Size(75, 33);
            this.LogoutButton.TabIndex = 0;
            this.LogoutButton.Text = "Log out";
            this.LogoutButton.UseVisualStyleBackColor = true;
            this.LogoutButton.Click += new System.EventHandler(this.LogoutButton_Click);
            // 
            // UsernameLabel
            // 
            this.UsernameLabel.AutoSize = true;
            this.UsernameLabel.Location = new System.Drawing.Point(923, 13);
            this.UsernameLabel.Name = "UsernameLabel";
            this.UsernameLabel.Size = new System.Drawing.Size(44, 16);
            this.UsernameLabel.TabIndex = 1;
            this.UsernameLabel.Text = "label1";
            // 
            // SubscriptionButton
            // 
            this.SubscriptionButton.Location = new System.Drawing.Point(12, 13);
            this.SubscriptionButton.Name = "SubscriptionButton";
            this.SubscriptionButton.Size = new System.Drawing.Size(193, 41);
            this.SubscriptionButton.TabIndex = 2;
            this.SubscriptionButton.Text = "Subscriptions";
            this.SubscriptionButton.UseVisualStyleBackColor = true;
            this.SubscriptionButton.Click += new System.EventHandler(this.SubscriptionButton_Click);
            // 
            // Client_Authorized
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(980, 483);
            this.Controls.Add(this.SubscriptionButton);
            this.Controls.Add(this.UsernameLabel);
            this.Controls.Add(this.LogoutButton);
            this.Name = "Client_Authorized";
            this.Text = "Client_Authorized";
            this.Load += new System.EventHandler(this.Client_Authorized_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button LogoutButton;
        private System.Windows.Forms.Label UsernameLabel;
        private System.Windows.Forms.Button SubscriptionButton;
    }
}