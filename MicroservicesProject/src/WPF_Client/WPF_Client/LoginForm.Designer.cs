namespace WPF_Client
{
    partial class LoginForm
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
            this.Username_textBox = new System.Windows.Forms.TextBox();
            this.Password_textBox = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.Login_Button = new System.Windows.Forms.Button();
            this.Registration_Button = new System.Windows.Forms.Button();
            this.GoBack_button = new System.Windows.Forms.Button();
            this.ErrorLabel = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // Username_textBox
            // 
            this.Username_textBox.Location = new System.Drawing.Point(372, 187);
            this.Username_textBox.Name = "Username_textBox";
            this.Username_textBox.Size = new System.Drawing.Size(311, 22);
            this.Username_textBox.TabIndex = 0;
            // 
            // Password_textBox
            // 
            this.Password_textBox.Location = new System.Drawing.Point(372, 244);
            this.Password_textBox.Name = "Password_textBox";
            this.Password_textBox.Size = new System.Drawing.Size(311, 22);
            this.Password_textBox.TabIndex = 1;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(369, 168);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(73, 16);
            this.label1.TabIndex = 2;
            this.label1.Text = "Username:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(372, 222);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(70, 16);
            this.label2.TabIndex = 3;
            this.label2.Text = "Password:";
            // 
            // Login_Button
            // 
            this.Login_Button.Location = new System.Drawing.Point(429, 292);
            this.Login_Button.Name = "Login_Button";
            this.Login_Button.Size = new System.Drawing.Size(176, 34);
            this.Login_Button.TabIndex = 4;
            this.Login_Button.Text = "Login";
            this.Login_Button.UseVisualStyleBackColor = true;
            this.Login_Button.Click += new System.EventHandler(this.Login_Button_Click);
            // 
            // Registration_Button
            // 
            this.Registration_Button.Location = new System.Drawing.Point(429, 346);
            this.Registration_Button.Name = "Registration_Button";
            this.Registration_Button.Size = new System.Drawing.Size(176, 34);
            this.Registration_Button.TabIndex = 5;
            this.Registration_Button.Text = "Register";
            this.Registration_Button.UseVisualStyleBackColor = true;
            this.Registration_Button.Click += new System.EventHandler(this.Registration_Button_Click);
            // 
            // GoBack_button
            // 
            this.GoBack_button.Location = new System.Drawing.Point(13, 13);
            this.GoBack_button.Name = "GoBack_button";
            this.GoBack_button.Size = new System.Drawing.Size(75, 23);
            this.GoBack_button.TabIndex = 6;
            this.GoBack_button.Text = "Back";
            this.GoBack_button.UseVisualStyleBackColor = true;
            this.GoBack_button.Click += new System.EventHandler(this.GoBack_button_Click);
            // 
            // ErrorLabel
            // 
            this.ErrorLabel.AutoSize = true;
            this.ErrorLabel.BackColor = System.Drawing.Color.Silver;
            this.ErrorLabel.ForeColor = System.Drawing.Color.Red;
            this.ErrorLabel.Location = new System.Drawing.Point(429, 432);
            this.ErrorLabel.Name = "ErrorLabel";
            this.ErrorLabel.Size = new System.Drawing.Size(0, 16);
            this.ErrorLabel.TabIndex = 7;
            // 
            // LoginForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1026, 648);
            this.Controls.Add(this.ErrorLabel);
            this.Controls.Add(this.GoBack_button);
            this.Controls.Add(this.Registration_Button);
            this.Controls.Add(this.Login_Button);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.Password_textBox);
            this.Controls.Add(this.Username_textBox);
            this.Name = "LoginForm";
            this.Text = "LoginForm";
            this.Load += new System.EventHandler(this.LoginForm_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox Username_textBox;
        private System.Windows.Forms.TextBox Password_textBox;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button Login_Button;
        private System.Windows.Forms.Button Registration_Button;
        private System.Windows.Forms.Button GoBack_button;
        private System.Windows.Forms.Label ErrorLabel;
    }
}