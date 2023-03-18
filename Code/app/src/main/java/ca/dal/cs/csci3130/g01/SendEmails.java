package ca.dal.cs.csci3130.g01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class SendEmails extends AppCompatActivity {
    // define variables
    private EditText etFromEmail, etPassword, etToEmail, etSubject, etMessage;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_emails);

        // Initialize the UI elements
        etFromEmail = findViewById(R.id.et_from_email);
        etPassword = findViewById(R.id.et_password);
        etToEmail = findViewById(R.id.et_to_email);
        etSubject = findViewById(R.id.et_subject);
        etMessage = findViewById(R.id.et_message);
        btnSend = findViewById(R.id.btn_send);
        // Set a click listener to execute SendMailTask
        btnSend.setOnClickListener(view -> {
            String fromEmail = etFromEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String toEmail = etToEmail.getText().toString().trim();
            String subject = etSubject.getText().toString().trim();
            String message = etMessage.getText().toString().trim();

            // Execute SendMailTask with the input data
            new SendMailTask(fromEmail, password, toEmail, subject, message).execute();
        });
    }

    /**
     * This inner class will implement javaMail API which will send a massage using the regular mai;
     */
    private class SendMailTask extends AsyncTask<Void, Void, Boolean> {
        private String fromEmail;
        private String fromPassword;
        private String toEmail;
        private String emailSubject;
        private String emailBody;

        public SendMailTask(String fromEmail, String fromPassword, String toEmail, String emailSubject, String emailBody) {
            this.fromEmail = fromEmail;
            this.fromPassword = fromPassword;
            this.toEmail = toEmail;
            this.emailSubject = emailSubject;
            this.emailBody = emailBody;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // Set up the properties for the email service
                Properties properties = new Properties();
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");

                Authenticator auth = new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, fromPassword);
                    }
                };

                Session session = Session.getInstance(properties, auth);

                // Create a new email message with the provided details
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(fromEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject(emailSubject);
                message.setText(emailBody);

                // Send the email message
                Transport.send(message);
                return true;
            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // Show a toast message depending on whether the email was sent successfully or not
            super.onPostExecute(result);
            if (result) {
                Toast.makeText(SendEmails.this, "Email sent successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SendEmails.this, "Email not sent. Check the log for errors.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
