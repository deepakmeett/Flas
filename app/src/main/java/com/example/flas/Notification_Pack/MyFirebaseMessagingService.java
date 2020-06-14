package com.example.flas.Notification_Pack;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.flas.Notification_5Activity;
import com.example.flas.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String title, message;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived( remoteMessage );
//        title = remoteMessage.getData().get( "Title" );
//        message = remoteMessage.getData().get( "Message" );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sendOreoNotification();
        } else {
            sendNotification();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendOreoNotification() {
//        String title = remoteMessage.getData().get( "title" );
//        String body = remoteMessage.getData().get( "body" );
//        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Intent intent = new Intent( getApplicationContext(), Notification_5Activity.class );
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 1, intent, PendingIntent.FLAG_ONE_SHOT );
        Uri sound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
        OreoNotification oreoNotification = new OreoNotification( this );
        Notification.Builder builder = oreoNotification.getOreoNotification(
                "Oreo Notification", "Firebase Retrofit Notification", R.mipmap.ic_launcher, pendingIntent, sound );
        oreoNotification.getNotificationManager().notify( 1, builder.build() );
    }

    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( getApplicationContext() )
                .setSmallIcon( R.mipmap.ic_launcher )
                .setContentTitle( "title" )
                .setContentText( "message" );
        NotificationManager manager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
        manager.notify( 0, builder.build() );
    }
}
