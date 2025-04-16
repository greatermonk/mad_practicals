package com.example.mypracticals;


import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailActivity extends AppCompatActivity {

    private EditText etEmailFrom, etPassword, etEmailTo, etSubject, etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        // Initialize UI components
        etEmailFrom = findViewById(R.id.etEmailFrom);
        etPassword = findViewById(R.id.etPassword);
        etEmailTo = findViewById(R.id.etEmailTo);
        etSubject = findViewById(R.id.etSubject);
        etMessage = findViewById(R.id.etMessage);
        Button btnSend = findViewById(R.id.btnSend);

        // Send button click listener
        btnSend.setOnClickListener(v -> sendEmail());
    }

    private void sendEmail() {
        final String username = etEmailFrom.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        String recipient = etEmailTo.getText().toString().trim();
        String subject = etSubject.getText().toString().trim();
        String message = etMessage.getText().toString().trim();

        // Validate input fields
        if (!validateInputs(username, password, recipient, subject, message)) {
            return;
        }

        // Execute email sending in background
        new SendEmailTask().execute(username, password, recipient, subject, message);
    }

    private boolean validateInputs(String username, String password,
                                   String recipient, String subject, String message) {
        // Input validation with specific error messages
        if (username.isEmpty()) {
            etEmailFrom.setError("Email address is required");
            return false;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            return false;
        }

        if (recipient.isEmpty()) {
            etEmailTo.setError("Recipient email is required");
            return false;
        }

        if (subject.isEmpty()) {
            etSubject.setError("Subject is required");
            return false;
        }

        if (message.isEmpty()) {
            etMessage.setError("Message is required");
            return false;
        }

        return true;
    }

    private class SendEmailTask extends AsyncTask<String, Void, Boolean> {
        private MessagingException exception;

        @Override
        protected Boolean doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String recipient = params[2];
            String subject = params[3];
            String messageBody = params[4];

            // Configure email properties for SMTP
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Create a session with authentication
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                // Create a MimeMessage
                Message mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(username));
                mimeMessage.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(recipient));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(messageBody);

                // Send the message
                Transport.send(mimeMessage);

                return true;
            } catch (MessagingException e) {
                exception = e;
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(SendEmailActivity.this,
                        "Email sent successfully!", Toast.LENGTH_SHORT).show();
                // Clear input fields after successful send
                clearInputFields();
            } else {
                String errorMsg = "Failed to send email: ";
                if (exception != null) {
                    errorMsg += exception.getMessage();
                }
                Toast.makeText(SendEmailActivity.this,
                        errorMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void clearInputFields() {
        etEmailTo.setText("");
        etSubject.setText("");
        etMessage.setText("");
    }
}