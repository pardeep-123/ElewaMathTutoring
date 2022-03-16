package com.elewamathtutoring.Util.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.elewamathtutoring.MainActivity;
import com.elewamathtutoring.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static int i;
    String message, user_id, type, job_id;
    String CHANNEL_ID = "";// The id of the channel.
    String CHANNEL_ONE_NAME = "Channel One";
    NotificationManager notificationManager;
    NotificationChannel notificationChannel;
    Notification notification;

    @Override
    public void onNewToken(String refreshedToken) {
        super.onNewToken(refreshedToken);
        Log.d(TAG, "Refreshed token:" + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        Log.e("device_token", token);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        getManager();
        CHANNEL_ID = getApplicationContext().getPackageName();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ONE_NAME, notificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        }

//Body: {user_id=123, body=Redeeemed 10 points successfully, 10 twd added to your wallet successfully, type=4}

        Log.e("MyFirebaseMsgService", "Notification Message Body: " + remoteMessage.getData());
        user_id = remoteMessage.getData().get("user_id");
        type = remoteMessage.getData().get("type");
        message = remoteMessage.getData().get("body");
        job_id = remoteMessage.getData().get("job_id");
        Log.e("BodyMyFirebvice", "" + user_id + "   -" + type + "   -" + message);
        sendMessagePush();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void sendMessagePush() {
        //'1=>award_bid, 2=>chat_messages, 3=>review_provider,4=>redeem_points_vouchers,
        // 5=>promocode_added,,8=>subscription_purchased,'
        Intent intent = null;
        intent = new Intent(getBaseContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Bitmap icon1 = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext())
                .setSmallIcon(getNotificationIcon()).setLargeIcon(icon1)
                .setStyle(new Notification.BigTextStyle().bigText(message))
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.defaultColor))
                .setContentTitle("ElewaMathTutoring")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notification = notificationBuilder.build();
        notificationManager.notify(i++, notification);
    }

    private NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.app_icon : R.drawable.app_icon;
    }
}
