package com.example.mypracticals;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import timber.log.Timber;

public class ReceiveEmailActivity extends AppCompatActivity {

    private static final String TAG = "ReceiveEmailActivity";

    private EditText etEmailHost, etEmailUsername, etEmailPassword, etPort;
    private ArrayAdapter<String> emailAdapter;
    private List<String> emailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_email);

        // Initialize UI components
        etEmailHost = findViewById(R.id.etEmailHost);
        etEmailUsername = findViewById(R.id.etEmailUsername);
        etEmailPassword = findViewById(R.id.etEmailPassword);

        // Add a new EditText for port in the layout
        etPort = findViewById(R.id.etPort);
        Button btnFetchEmails = findViewById(R.id.btnFetchEmails);
        ListView lvEmails = findViewById(R.id.lvEmails);

        // Preset common email provider configurations
        setupEmailProviderPresets();

        // Initialize email list and adapter
        emailList = new ArrayList<>();
        emailAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, emailList);
        lvEmails.setAdapter(emailAdapter);

        // Fetch emails button click listener
        btnFetchEmails.setOnClickListener(v -> fetchEmails());
    }

    private void setupEmailProviderPresets() {
        // Dropdown or buttons in actual implementation
        etEmailHost.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String host = etEmailHost.getText().toString().toLowerCase().trim();
                switch (host) {
                    case "gmail.com":
                    case "pop.gmail.com":
                        etEmailHost.setText("pop.gmail.com");
                        etPort.setText("995");
                        break;
                    case "outlook.com":
                    case "hotmail.com":
                        etEmailHost.setText("pop3.live.com");
                        etPort.setText("995");
                        break;
                    case "yahoo.com":
                        etEmailHost.setText("pop.mail.yahoo.com");
                        etPort.setText("995");
                        break;
                }
            }
        });
    }

    private void fetchEmails() {
        String host = etEmailHost.getText().toString().trim();
        String username = etEmailUsername.getText().toString().trim();
        String password = etEmailPassword.getText().toString().trim();
        String portStr = etPort.getText().toString().trim();

        // Validate input fields
        if (!validateInputs(host, username, password, portStr)) {
            return;
        }

        int port = Integer.parseInt(portStr);

        // Execute email fetching in background
        new FetchEmailsTask().execute(host, username, password, String.valueOf(port));
    }

    private boolean validateInputs(String host, String username,
                                   String password, String portStr) {
        // Input validation with specific error messages
        if (host.isEmpty()) {
            etEmailHost.setError("Email host is required");
            return false;
        }

        if (username.isEmpty()) {
            etEmailUsername.setError("Email address is required");
            return false;
        }

        if (password.isEmpty()) {
            etEmailPassword.setError("Password is required");
            return false;
        }

        if (portStr.isEmpty()) {
            etPort.setError("Port is required");
            return false;
        }

        try {
            int port = Integer.parseInt(portStr);
            if (port < 1 || port > 65535) {
                etPort.setError("Invalid port number");
                return false;
            }
        } catch (NumberFormatException e) {
            etPort.setError("Invalid port number");
            return false;
        }

        return true;
    }

    private class FetchEmailsTask extends AsyncTask<String, Void, List<String>> {
        private Exception exception;

        @Override
        protected List<String> doInBackground(String... params) {
            String host = params[0];
            String username = params[1];
            String password = params[2];
            int port = Integer.parseInt(params[3]);

            List<String> emails = new ArrayList<>();

            try {
                // Configure properties for email retrieval
                Properties props = new Properties();
                props.put("mail.pop3.host", host);
                props.put("mail.pop3.port", String.valueOf(port));
                props.put("mail.pop3.starttls.enable", "true");

                // Add debug logging
                props.put("mail.debug", "true");

                // Detailed SSL/TLS configuration
                props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.pop3.socketFactory.fallback", "false");
                props.put("mail.pop3.socketFactory.port", String.valueOf(port));

                // Create a session
                Session session = Session.getInstance(props);

                // Enable debug output
                session.setDebug(true);

                // Connect to the store
                Store store = session.getStore("pop3s");

                // Log connection details
                Timber.tag(TAG).d("Attempting to connect to: " + host + ":" + port);
                Timber.tag(TAG).d("Username: %s", username);

                store.connect(host, username, password);

                // Open the folder
                Folder folder = store.getFolder("INBOX");
                folder.open(Folder.READ_ONLY);

                // Retrieve messages
                Message[] messages = folder.getMessages();
                for (Message message : messages) {
                    String emailSummary = String.format(
                            "From: %s\nSubject: %s\nDate: %s",
                            message.getFrom()[0],
                            message.getSubject(),
                            message.getSentDate()
                    );
                    emails.add(emailSummary);
                }

                // Close folder and store
                folder.close(false);
                store.close();

                return emails;
            } catch (AuthenticationFailedException e) {
                Timber.tag(TAG).e(e, "Authentication Failed");
                exception = new Exception("Authentication failed. Check credentials.");
                return null;
            } catch (NoSuchProviderException e) {
                Timber.tag(TAG).e(e, "No Such Provider");
                exception = new Exception("Email provider not supported.");
                return null;
            } catch (MessagingException e) {
                Timber.tag(TAG).e(e, "Messaging Exception");
                exception = new Exception("Connection error: " + e.getMessage());
                return null;
            } catch (Exception e) {
                Timber.tag(TAG).e(e, "Unexpected Error");
                exception = new Exception("Unexpected error: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<String> emails) {
            if (emails != null && !emails.isEmpty()) {
                // Clear previous emails and add new ones
                emailList.clear();
                emailList.addAll(emails);
                emailAdapter.notifyDataSetChanged();

                Toast.makeText(ReceiveEmailActivity.this,
                        "Emails fetched successfully!", Toast.LENGTH_SHORT).show();
            } else {
                String errorMsg = "Failed to fetch emails: ";
                if (exception != null) {
                    errorMsg += exception.getMessage();
                }

                // Show a detailed dialog for connection issues
                showConnectionHelpDialog(errorMsg);
            }
        }
    }

    private void showConnectionHelpDialog(String errorMessage) {
        new AlertDialog.Builder(this)
                .setTitle("Email Retrieval Failed")
                .setMessage(errorMessage + "\n\n" +
                        "Troubleshooting Tips:\n" +
                        "1. Verify Host and Port:\n" +
                        "   - Gmail: pop.gmail.com:995\n" +
                        "   - Outlook: pop3.live.com:995\n" +
                        "   - Yahoo: pop.mail.yahoo.com:995\n" +
                        "2. Enable POP3/IMAP in Email Settings\n" +
                        "3. Check Username and Password\n" +
                        "4. Ensure Less Secure App Access or App Password\n" +
                        "5. Check Internet Connection")
                .setPositiveButton("OK", null)
                .setNeutralButton("Learn More", (dialog, which) -> Toast.makeText(this,
                        "Check your email provider's documentation",
                        Toast.LENGTH_LONG).show())
                .show();
    }
}