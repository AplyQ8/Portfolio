namespace WPF_Client
{
    partial class Client_NotAuthorized
    {
        /// <summary>
        /// Обязательная переменная конструктора.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Освободить все используемые ресурсы.
        /// </summary>
        /// <param name="disposing">истинно, если управляемый ресурс должен быть удален; иначе ложно.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Код, автоматически созданный конструктором форм Windows

        /// <summary>
        /// Требуемый метод для поддержки конструктора — не изменяйте 
        /// содержимое этого метода с помощью редактора кода.
        /// </summary>
        private void InitializeComponent()
        {
            this.Login_Button = new System.Windows.Forms.Button();
            this.Registration_Button = new System.Windows.Forms.Button();
            this.Subscriptions_Button = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // Login_Button
            // 
            this.Login_Button.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.Login_Button.Location = new System.Drawing.Point(824, 12);
            this.Login_Button.Name = "Login_Button";
            this.Login_Button.Size = new System.Drawing.Size(75, 35);
            this.Login_Button.TabIndex = 0;
            this.Login_Button.Text = "Login";
            this.Login_Button.UseVisualStyleBackColor = true;
            this.Login_Button.Click += new System.EventHandler(this.Login_Button_Click);
            // 
            // Registration_Button
            // 
            this.Registration_Button.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.Registration_Button.Location = new System.Drawing.Point(905, 12);
            this.Registration_Button.Name = "Registration_Button";
            this.Registration_Button.Size = new System.Drawing.Size(112, 34);
            this.Registration_Button.TabIndex = 1;
            this.Registration_Button.Text = "Registration";
            this.Registration_Button.UseVisualStyleBackColor = true;
            this.Registration_Button.Click += new System.EventHandler(this.Registration_Button_Click);
            // 
            // Subscriptions_Button
            // 
            this.Subscriptions_Button.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)));
            this.Subscriptions_Button.Location = new System.Drawing.Point(334, 109);
            this.Subscriptions_Button.Name = "Subscriptions_Button";
            this.Subscriptions_Button.Size = new System.Drawing.Size(362, 55);
            this.Subscriptions_Button.TabIndex = 2;
            this.Subscriptions_Button.Text = "Subscriptions";
            this.Subscriptions_Button.UseVisualStyleBackColor = true;
            this.Subscriptions_Button.Click += new System.EventHandler(this.Subscriptions_Button_Click);
            // 
            // Client_NotAuthorized
            // 
            this.ClientSize = new System.Drawing.Size(1029, 618);
            this.Controls.Add(this.Subscriptions_Button);
            this.Controls.Add(this.Registration_Button);
            this.Controls.Add(this.Login_Button);
            this.Name = "Client_NotAuthorized";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button Login_Button;
        private System.Windows.Forms.Button Registration_Button;
        private System.Windows.Forms.Button Subscriptions_Button;
    }
}

