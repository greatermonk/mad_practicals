package com.example.mypracticals;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null &&
                intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            try {
                SmsMessage[] messages = extractMessages(intent);
                if (messages != null && messages.length > 0) {
                    StringBuilder messageBody = new StringBuilder();
                    String sender = messages[0].getOriginatingAddress();
                    for (SmsMessage message : messages) {
                        messageBody.append(message.getMessageBody());
                    }
                    String fullMessage = messageBody.toString();
                    long timestamp = messages[0].getTimestampMillis();
                    Log.d(TAG, "SMS received from: " + sender);
                    MessageRepository repository = new MessageRepository(context);
                    Message message = new Message(
                            0,                   // Auto-generated ID
                            sender,                 // Phone number
                            fullMessage,            // Message content
                            timestamp,              // Timestamp
                            false);                 // Not a sent message
                    repository.insert(message);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error processing received SMS", e);
            }}}

    private SmsMessage[] extractMessages(Intent intent) {
        return Telephony.Sms.Intents.getMessagesFromIntent(intent);
    }}
