package com.example.mypracticals;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Practical29 extends AppCompatActivity {
    private static final String TAG = "Practical29";
    private static final int SMS_PERMISSION_REQUEST_CODE = 101;
    private static final String SMS_SENT = "SMS_SENT";
    private static final String SMS_DELIVERED = "SMS_DELIVERED";
    private TextInputLayout phoneInputLayout;
    private TextInputLayout messageInputLayout;
    private TextInputEditText phoneEditText;
    private TextInputEditText messageEditText;
    private Button sendButton;
    private RecyclerView messagesRecyclerView;
    private CircularProgressIndicator progressIndicator;
    private MessageAdapter messageAdapter;
    private SMSViewModel viewModel;
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,15}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practical29);

        // Initialize view model
        viewModel = new ViewModelProvider(this).get(SMSViewModel.class);

        // Find views
        phoneInputLayout = findViewById(R.id.phoneInputLayout);
        messageInputLayout = findViewById(R.id.messageInputLayout);
        phoneEditText = findViewById(R.id.phoneEditText);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        progressIndicator = findViewById(R.id.progressIndicator);

        // Set up RecyclerView
        messageAdapter = new MessageAdapter(new ArrayList<>());
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesRecyclerView.setAdapter(messageAdapter);

        // Check for permissions
        checkAndRequestPermissions();

        // Set up observers
        observeViewModel();

        // Set up SMS listeners
        registerSMSDeliveryReceivers();

        // Set up button click listener
        sendButton.setOnClickListener(v -> attemptToSendSMS());
    }

    private void observeViewModel() {
        viewModel.getMessages().observe(this, messages -> {
            messageAdapter.updateMessages(messages);
            if (!messages.isEmpty()) {
                messagesRecyclerView.scrollToPosition(messages.size() - 1);
            }
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            progressIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            sendButton.setEnabled(!isLoading);
        });
    }

    private void checkAndRequestPermissions() {
        String[] permissions = {
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS
        };

        ArrayList<String> permissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }
        if (!permissionsNeeded.isEmpty()) {
            showPermissionExplanationDialog(permissionsNeeded.toArray(new String[0]));
        }
    }

    private void showPermissionExplanationDialog(String[] permissions) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.permission_required)
                .setMessage(R.string.permission_required_message)
                .setPositiveButton(R.string.grant_permission, (dialog, which) -> ActivityCompat.requestPermissions(
                        Practical29.this,
                        permissions,
                        SMS_PERMISSION_REQUEST_CODE
                ))
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                    Toast.makeText(Practical29.this,
                            "App requires SMS permissions to function",
                            Toast.LENGTH_LONG).show();
                })
                .setCancelable(false)
                .create()
                .show();
    }

//    @SuppressLint("UnspecifiedRegisterReceiverFlag")
@SuppressLint("UnspecifiedRegisterReceiverFlag")
private void registerSMSDeliveryReceivers() {
    // Register for SMS sent tracking
    // Check if Android version is TIRAMISU or higher
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                viewModel.setLoading(false);

                switch (getResultCode()) {
                    case RESULT_OK:
                        Toast.makeText(context, R.string.sms_sent_success, Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, R.string.sms_sent_failure, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "SMS sending failed with error code: " + getResultCode());
                        break;
                }
            }
        }, new IntentFilter(SMS_SENT), Context.RECEIVER_NOT_EXPORTED);
    } else {
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                viewModel.setLoading(false);

                switch (getResultCode()) {
                    case RESULT_OK:
                        Toast.makeText(context, R.string.sms_sent_success, Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, R.string.sms_sent_failure, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "SMS sending failed with error code: " + getResultCode());
                        break;
                }
            }
        }, new IntentFilter(SMS_SENT));
    }

    // Register for SMS delivery tracking
    // Check if Android version is TIRAMISU or higher
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case RESULT_OK:
                        Log.d(TAG, "SMS delivered successfully");
                        break;
                    case RESULT_CANCELED:
                        Log.d(TAG, "SMS delivery failed");
                        break;
                }
            }
        }, new IntentFilter(SMS_DELIVERED), Context.RECEIVER_NOT_EXPORTED);
    } else {
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case RESULT_OK:
                        Log.d(TAG, "SMS delivered successfully");
                        break;
                    case RESULT_CANCELED:
                        Log.d(TAG, "SMS delivery failed");
                        break;
                }
            }
        }, new IntentFilter(SMS_DELIVERED));
    }

}

    private void attemptToSendSMS() {
        // Reset any previous errors
        phoneInputLayout.setError(null);
        messageInputLayout.setError(null);

        // Get input values
        String phoneNumber = phoneEditText.getText() != null ? phoneEditText.getText().toString() : "";
        String message = messageEditText.getText() != null ? messageEditText.getText().toString() : "";

        // Validate inputs
        boolean hasError = false;

        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(message)) {
            Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
            hasError = true;

            if (TextUtils.isEmpty(phoneNumber)) {
                phoneInputLayout.setError("Phone number required");
            }

            if (TextUtils.isEmpty(message)) {
                messageInputLayout.setError("Message required");
            }
        } else if (!isValidPhoneNumber(phoneNumber)) {
            phoneInputLayout.setError(getString(R.string.error_invalid_phone));
            hasError = true;
        }

        // If no errors, send the SMS
        if (!hasError) {
            sendSMS(phoneNumber, message);
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    private void sendSMS(String phoneNumber, String message) {
        try {
            // Check permission before sending
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                checkAndRequestPermissions();
                return;
            }

            // Set loading state
            viewModel.setLoading(true);

            // Create pending intents for delivery reports
            PendingIntent sentPI = PendingIntent.getBroadcast(
                    this, 0, new Intent(SMS_SENT),
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            PendingIntent deliveredPI = PendingIntent.getBroadcast(
                    this, 0, new Intent(SMS_DELIVERED),
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            // Get SmsManager instance
            SmsManager smsManager = SmsManager.getDefault();

            // If message is too long, divide it into parts
            if (message.length() > 160) {
                ArrayList<String> parts = smsManager.divideMessage(message);
                ArrayList<PendingIntent> sentIntents = new ArrayList<>();
                ArrayList<PendingIntent> deliveredIntents = new ArrayList<>();

                for (int i = 0; i < parts.size(); i++) {
                    sentIntents.add(sentPI);
                    deliveredIntents.add(deliveredPI);
                }

                smsManager.sendMultipartTextMessage(
                        phoneNumber, null, parts, sentIntents, deliveredIntents);
            } else {
                smsManager.sendTextMessage(
                        phoneNumber, null, message, sentPI, deliveredPI);
            }

            // Add sent message to local storage
            viewModel.addSentMessage(phoneNumber, message);

        } catch (Exception e) {
            Log.e(TAG, "Error sending SMS", e);
            Toast.makeText(this, "Failed to send SMS: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            viewModel.setLoading(false);
        }
    }

    private void clearInputFields() {
        phoneEditText.setText("");
        messageEditText.setText("");
        messageEditText.clearFocus();
        phoneEditText.clearFocus();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            boolean allPermissionsGranted = true;

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (!allPermissionsGranted) {
                Toast.makeText(this,
                        "SMS functionality will be limited without permissions",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister receivers to prevent memory leaks
        try {
            unregisterReceiver(null); // Will throw if not registered
        } catch (Exception e) {
            // Ignore
        }
    }
}