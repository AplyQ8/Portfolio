namespace WPF_Client
{
    partial class RegistrationForm
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
            this.username_textBox = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.Password_textBox = new System.Windows.Forms.TextBox();
            this.Email_textBox = new System.Windows.Forms.TextBox();
            this.ConfirmPassword_textBox = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.RegistrationButton = new System.Windows.Forms.Button();
            this.GoBack_button = new System.Windows.Forms.Button();
            this.ErrorLabel = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // username_textBox
            // 
            this.username_textBox.Location = new System.Drawing.Point(348, 79);
            this.username_textBox.Name = "username_textBox";
            this.username_textBox.Size = new System.Drawing.Size(343, 22);
            this.username_textBox.TabIndex = 0;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(345, 42);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(73, 16);
            this.label1.TabIndex = 1;
            this.label1.Text = "Username:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(345, 199);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(70, 16);
            this.label2.TabIndex = 2;
            this.label2.Text = "Password:";
            // 
            // Password_textBox
            // 
            this.Password_textBox.Location = new System.Drawing.Point(348, 238);
            this.Password_textBox.Name = "Password_textBox";
            this.Password_textBox.PasswordChar = '*';
            this.Password_textBox.Size = new System.Drawing.Size(340, 22);
            this.Password_textBox.TabIndex = 3;
            // 
            // Email_textBox
            // 
            this.Email_textBox.Location = new System.Drawing.Point(348, 156);
            this.Email_textBox.Name = "Email_textBox";
            this.Email_textBox.Size = new System.Drawing.Size(340, 22);
            this.Email_textBox.TabIndex = 4;
            // 
            // ConfirmPassword_textBox
            // 
            this.ConfirmPassword_textBox.Location = new System.Drawing.Point(348, 323);
            this.ConfirmPassword_textBox.Name = "ConfirmPassword_textBox";
            this.ConfirmPassword_textBox.PasswordChar = '*';
            this.ConfirmPassword_textBox.Size = new System.Drawing.Size(340, 22);
            this.ConfirmPassword_textBox.TabIndex = 5;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(345, 128);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(44, 16);
            this.label3.TabIndex = 6;
            this.label3.Text = "Email:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(345, 282);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(117, 16);
            this.label4.TabIndex = 7;
            this.label4.Text = "Confirm password:";
            // 
            // RegistrationButton
            // 
            this.RegistrationButton.Location = new System.Drawing.Point(429, 372);
            this.RegistrationButton.Name = "RegistrationButton";
            this.RegistrationButton.Size = new System.Drawing.Size(155, 54);
            this.RegistrationButton.TabIndex = 8;
            this.RegistrationButton.Text = "Register";
            this.RegistrationButton.UseVisualStyleBackColor = true;
            this.RegistrationButton.Click += new System.EventHandler(this.RegistrationButton_Click);
            // 
            // GoBack_button
            // 
            this.GoBack_button.Location = new System.Drawing.Point(13, 13);
            this.GoBack_button.Name = "GoBack_button";
            this.GoBack_button.Size = new System.Drawing.Size(75, 23);
            this.GoBack_button.TabIndex = 9;
            this.GoBack_button.Text = "Back";
            this.GoBack_button.UseVisualStyleBackColor = true;
            this.GoBack_button.Click += new System.EventHandler(this.GoBack_button_Click);
            // 
            // ErrorLabel
            // 
            this.ErrorLabel.AutoSize = true;
            this.ErrorLabel.Location = new System.Drawing.Point(484, 464);
            this.ErrorLabel.Name = "ErrorLabel";
            this.ErrorLabel.Size = new System.Drawing.Size(0, 16);
            this.ErrorLabel.TabIndex = 10;
            // 
            // RegistrationForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1028, 652);
            this.Controls.Add(this.ErrorLabel);
            this.Controls.Add(this.GoBack_button);
            this.Controls.Add(this.RegistrationButton);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.ConfirmPassword_textBox);
            this.Controls.Add(this.Email_textBox);
            this.Controls.Add(this.Password_textBox);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.username_textBox);
            this.Name = "RegistrationForm";
            this.Text = "RegistrationForm";
            this.Load += new System.EventHandler(this.RegistrationForm_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox username_textBox;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox Password_textBox;
        private System.Windows.Forms.TextBox Email_textBox;
        private System.Windows.Forms.TextBox ConfirmPassword_textBox;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button RegistrationButton;
        private System.Windows.Forms.Button GoBack_button;
        private System.Windows.Forms.Label ErrorLabel;
    }
}