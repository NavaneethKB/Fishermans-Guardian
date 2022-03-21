package com.example.firshermensguardian;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title=remoteMessage.getNotification().getTitle();
        String body=remoteMessage.getNotification().getBody();
        final String channel_ID="HEADS_UP_NOTIFICATION";
        NotificationChannel channel=new NotificationChannel(
                channel_ID,
                "MyNotification",
                NotificationManager.IMPORTANCE_DEFAULT);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification=new Notification.Builder(this,channel_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.google_icon)
                .setAutoCancel(true);

        NotificationManagerCompat.from(this).notify(1,notification.build());
    }
}
