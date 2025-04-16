package com.example.basicui;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <h2>This is a simple Implementation of a Service</h2><hr>
 *     <p style="font-size:15px;font-family:sans-serif;">For more Information, refer to this <i>article:</i><blockquote>{@link Service}</blockquote></p>
 */
public class ClockService extends Service {
    private static final String CHANNEL_ID = "ClockServiceChannel";
    private static final int NOTIFICATION_ID = 1;
    private final Handler handler = new Handler();
    private final Runnable updateNotificationTask = new Runnable() {
        @Override
        public void run() {
            updateNotification();
            handler.postDelayed(this, 10000); // update every 10 seconds
             }};
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, buildNotification("Starting clock..."));
        // Begin updating the notification.
        handler.post(updateNotificationTask);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // If the service is killed, it will be restarted with the last delivered Intent.
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the periodic update.
        handler.removeCallbacks(updateNotificationTask);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // This is not a bound service.
        return null;
    }
    // Helper method to update the notification with the current time.
    private void updateNotification() {
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        Notification notification = buildNotification("Current Time: " + currentTime);
        // Update the notification.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_DENIED){
            return;
        }
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notification);}
    // Build the notification that shows the clock.
    @NonNull
    private Notification buildNotification(String contentText) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Clock Service")
                .setContentText(contentText)
                .setColor(Color.parseColor("#FF0000"))
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
    }
    // Create a notification channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "Clock Service Channel";
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {manager.createNotificationChannel(channel);}}}}