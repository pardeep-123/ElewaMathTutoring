package com.elewamathtutoring.Util.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.elewamathtutoring.Activity.Chat.Chat_Activity
import com.elewamathtutoring.Activity.Chat.call.IncomingCallActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.request.RequestsActivity
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import org.json.JSONObject

class FirebaseService : FirebaseMessagingService() {
    private val TAG = "FireBasePush"
    private var i = 0
    var title = ""
    var message: String? = ""
    var type: Int = 0
    var CHANNEL_ID = "AppointMe"
    var id = ""
    var name = ""
    var image = ""
    var recieverName = ""
    var recieverImage = ""
    var bookingId: String? = ""
    var status: Int? = null
    var receiverId: String? = null
    lateinit var soundUri: Uri
    var objectBody: JSONObject? = null

    override fun onNewToken(refreshedToken: String) {
        super.onNewToken(refreshedToken)
        Log.e(TAG, "Refreshed token: $refreshedToken")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.e(TAG, "Notification: ${remoteMessage.data}")

        val notificationModel = Gson().fromJson(
            (Gson().toJson(remoteMessage.data)).toString(),
            PushNotificationModel::class.java
        )
      //  title = notificationModel.name
        message = notificationModel.message
        try {
            title = notificationModel.title
        } catch (e: Exception) {
        }
        val intent: Intent?
        if(notificationModel.push_type=="17"){
            //if (appStatus == "online") {
                intent = Intent(this, IncomingCallActivity::class.java)
                intent.putExtra("friendId", notificationModel.friendId)
                intent.putExtra("friendName", notificationModel.friendName)
                intent.putExtra("friendImage", notificationModel.friendImage)
                intent.putExtra("channelName", notificationModel.channelName)
                intent.putExtra("token", notificationModel.token)
                makePushForCall(intent)
          //  }
        } else if (notificationModel.push_type=="18"){
            //if (appStatus == "online") {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(-1)
          //  }
        }else{
            when (notificationModel.push_type) {
                "1" -> {
                    //  intent = Intent(this,RequestsActivity::class.java)
                    intent = Intent(this,Chat_Activity::class.java)
        //                intent.putExtra("id",notificationModel.sessionId)
                    if (notificationModel.groupId!=null)
                    intent.putExtra("groupId",notificationModel.groupId)
                    else
                        intent.putExtra("receiverId",notificationModel.userId)
                }
                "2" -> {
                    intent = Intent(this,RequestsActivity::class.java)
                    intent.putExtra("id",notificationModel.sessionId)

                }
                else -> {
                    intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("pushNotificationModel", notificationModel)
                }
            }
            makePush(intent)
        }
      //  if (Constants.User2Id!= notificationModel.id || !Constants.OnMessageScreen)
    }
    private fun makePush(intent: Intent?) {
        val pendingIntent = PendingIntent.getActivity(this, i, intent, PendingIntent.FLAG_ONE_SHOT)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val channelId = CHANNEL_ID
        soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(notificationIcon)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.push_logo))
            .setContentTitle(getString(R.string.app_name))
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setColor(ContextCompat.getColor(this, R.color.blue))
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_ALL)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, "Elewa", NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.setShowBadge(true)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(i++, notificationBuilder.build())
    }
    private fun makePushForCall(intent: Intent?) {
        val pendingIntent = PendingIntent.getActivity(this, i, intent, PendingIntent.FLAG_ONE_SHOT)
        // callscreen.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent!!.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        val channelId = CHANNEL_ID
        soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(notificationIcon)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.push_logo))
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setColor(ContextCompat.getColor(this, R.color.blue))
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_ALL)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, "Elewa", NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.setShowBadge(true)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(-1, notificationBuilder.build())
    }
    private val notificationIcon: Int
        get() {
            val useWhiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            return if (useWhiteIcon) R.drawable.ic_noti else R.drawable.push_logo
        }
}