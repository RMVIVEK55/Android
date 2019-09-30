package com.asow.astan.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;


import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.asow.astan.R;
import com.asow.astan.activity.MainActivity;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        String message = "";

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> messageArray = remoteMessage.getData();
            if (messageArray != null && messageArray.size() > 0) {
                message = messageArray.get("message");
                handleMessage(message);
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            if (remoteMessage.getNotification().getBody() != null &&
                    remoteMessage.getNotification().getBody().trim().length() > 0) {
                message = remoteMessage.getNotification().getBody().trim();
                handleMessage(message);

            }
        }


    }


    private void handleMessage(String message) {

        if (message != null && message.trim().length() > 0&&!TextUtils.isEmpty(message)) {
            // send the broadcast to auto sync receiver
         //   sendBroadCast();

            generateNotification(message);
        }
    }




    //    /**
//     * Called if InstanceID token is updated.
//     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
                sendRegistrationToServer(token);
    }

    /**
     * Send Registration token to the server
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param message FCM message body received.
     */
    private void generateNotification(String message) {
     final Context con=this;


        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String title = getString(R.string.app_name);
        Intent notificationIntent;
        notificationIntent = new Intent(this,
                MainActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("notification", "yes");
        notificationIntent.putExtras(bundle);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Notification notification = null;


        //android 'O' changes
        if (Build.VERSION.SDK_INT >= 26) {

            NotificationChannel channel = new NotificationChannel("Noti_channel", "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("");
            notificationManager.createNotificationChannel(channel);

            // Create the persistent notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Noti_channel")
                    .setContentTitle(title)
                    .setContentText(message)
                    .setWhen(when)
                    .setContentIntent(intent)
                    .setTicker(message)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.ic_android);
            notification = builder.build();
        } else {
            // Notification notification = new Notification(icon, message, when);
            notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_android)
                    .setTicker(message)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setWhen(when)
                    .setAutoCancel(true)
                    .setContentIntent(intent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();
        }


        notification.defaults = Notification.DEFAULT_ALL;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notification);
    }

}

