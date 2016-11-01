package com.example.android.sunshine.app.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.example.android.sunshine.app.MainActivity;
import com.example.android.sunshine.app.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Jayson Dela Cruz on 11/1/2016.
 */

public class MyFcmListenerService extends FirebaseMessagingService{
    public static final String EXTRA_WEATHER = "weather";
    public static final String EXTRA_LOCATION = "location";
    public static final int NOTIFICATION_ID = 0;


    @Override
    public void onMessageReceived(RemoteMessage message){
        String from = message.getFrom();
        Map data = message.getData();

        if(!data.isEmpty()){
            if((getString(R.string.gcm_defaultSenderId)).equals(from)){
                // Parse the message
                String weather = (String) data.get(EXTRA_WEATHER);
                String location = (String) data.get(EXTRA_LOCATION);
                String alert = String.format(
                        getString(R.string.gcm_weather_alert), weather, location);
                sendNotification(alert);
            }
        }
    }


    private void sendNotification(String message){
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent
                .getActivity(this, 0, new Intent(this, MainActivity.class),0);

        Bitmap largeIcon =
                BitmapFactory.decodeResource(this.getResources(), R.drawable.art_storm);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.art_clear)
                    .setLargeIcon(largeIcon)
                    .setContentTitle("Weather Alert!")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }

}
