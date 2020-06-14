package com.example.flas.Notification_Pack;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
public class OreoNotification extends ContextWrapper {

    public static final String CHANNEL_ID = "channel";
    public static final String CHANNEL_NAME = "oreo";
    private NotificationManager notificationManager;

    public OreoNotification(Context base) {
        super( base );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT );
        channel.enableLights( true );
        channel.enableVibration( true );
        channel.setLockscreenVisibility( Notification.VISIBILITY_PRIVATE );
        getNotificationManager().createNotificationChannel( channel );
    }

    public NotificationManager getNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
        }
        return notificationManager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getOreoNotification(String title, String body, int icon, PendingIntent intent, Uri sound) {
        return new Notification.Builder( getApplicationContext(), CHANNEL_ID )
                .setContentTitle( title )
                .setContentText( body )
                .setSmallIcon( icon )
                .setContentIntent( intent )
                .setSound( sound )
                .setAutoCancel( true );
    }
}
