namespace WPF_Client
{
    partial class PaymentForm
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
            this.Purchase_Label = new System.Windows.Forms.Label();
            this.PurchaseName_Label = new System.Windows.Forms.Label();
            this.Price_Label = new System.Windows.Forms.Label();
            this.purchasePrice_Label = new System.Windows.Forms.Label();
            this.Confirmation_Button = new System.Windows.Forms.Button();
            this.Error_label = new System.Windows.Forms.Label();
            this.Success_label = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // Purchase_Label
            // 
            this.Purchase_Label.AutoSize = true;
            this.Purchase_Label.Font = new System.Drawing.Font("Microsoft Sans Serif", 13.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.Purchase_Label.Location = new System.Drawing.Point(274, 107);
            this.Purchase_Label.Name = "Purchase_Label";
            this.Purchase_Label.Size = new System.Drawing.Size(120, 29);
            this.Purchase_Label.TabIndex = 0;
            this.Purchase_Label.Text = "Purchase:";
            // 
            // PurchaseName_Label
            // 
            this.PurchaseName_Label.AutoSize = true;
            this.PurchaseName_Label.Font = new System.Drawing.Font("Microsoft Sans Serif", 13.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.PurchaseName_Label.Location = new System.Drawing.Point(471, 107);
            this.PurchaseName_Label.Name = "PurchaseName_Label";
            this.PurchaseName_Label.Size = new System.Drawing.Size(79, 29);
            this.PurchaseName_Label.TabIndex = 1;
            this.PurchaseName_Label.Text = "label2";
            // 
            // Price_Label
            // 
            this.Price_Label.AutoSize = true;
            this.Price_Label.Font = new System.Drawing.Font("Microsoft Sans Serif", 13.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.Price_Label.Location = new System.Drawing.Point(279, 212);
            this.Price_Label.Name = "Price_Label";
            this.Price_Label.Size = new System.Drawing.Size(75, 29);
            this.Price_Label.TabIndex = 2;
            this.Price_Label.Text = "Price:";
            // 
            // purchasePrice_Label
            // 
            this.purchasePrice_Label.AutoSize = true;
            this.purchasePrice_Label.Font = new System.Drawing.Font("Microsoft Sans Serif", 13.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.purchasePrice_Label.Location = new System.Drawing.Point(476, 212);
            this.purchasePrice_Label.Name = "purchasePrice_Label";
            this.purchasePrice_Label.Size = new System.Drawing.Size(79, 29);
            this.purchasePrice_Label.TabIndex = 3;
            this.purchasePrice_Label.Text = "label3";
            // 
            // Confirmation_Button
            // 
            this.Confirmation_Button.Font = new System.Drawing.Font("Microsoft Sans Serif", 13.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.Confirmation_Button.Location = new System.Drawing.Point(279, 309);
            this.Confirmation_Button.Name = "Confirmation_Button";
            this.Confirmation_Button.Size = new System.Drawing.Size(271, 63);
            this.Confirmation_Button.TabIndex = 4;
            this.Confirmation_Button.Text = "Proceed Payment";
            this.Confirmation_Button.UseVisualStyleBackColor = true;
            this.Confirmation_Button.Click += new System.EventHandler(this.Confirmation_Button_Click);
            // 
            // Error_label
            // 
            this.Error_label.AutoSize = true;
            this.Error_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.Error_label.ForeColor = System.Drawing.Color.Red;
            this.Error_label.Location = new System.Drawing.Point(309, 413);
            this.Error_label.Name = "Error_label";
            this.Error_label.Size = new System.Drawing.Size(64, 25);
            this.Error_label.TabIndex = 5;
            this.Error_label.Text = "label1";
            // 
            // Success_label
            // 
            this.Success_label.AutoSize = true;
            this.Success_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(204)));
            this.Success_label.ForeColor = System.Drawing.Color.DarkGreen;
            this.Success_label.Location = new System.Drawing.Point(314, 413);
            this.Success_label.Name = "Success_label";
            this.Success_label.Size = new System.Drawing.Size(64, 25);
            this.Success_label.TabIndex = 6;
            this.Success_label.Text = "label1";
            // 
            // PaymentForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(902, 475);
            this.Controls.Add(this.Success_label);
            this.Controls.Add(this.Error_label);
            this.Controls.Add(this.Confirmation_Button);
            this.Controls.Add(this.purchasePrice_Label);
            this.Controls.Add(this.Price_Label);
            this.Controls.Add(this.PurchaseName_Label);
            this.Controls.Add(this.Purchase_Label);
            this.Name = "PaymentForm";
            this.Text = "PaymentForm";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label Purchase_Label;
        private System.Windows.Forms.Label PurchaseName_Label;
        private System.Windows.Forms.Label Price_Label;
        private System.Windows.Forms.Label purchasePrice_Label;
        private System.Windows.Forms.Button Confirmation_Button;
        private System.Windows.Forms.Label Error_label;
        private System.Windows.Forms.Label Success_label;
    }
}