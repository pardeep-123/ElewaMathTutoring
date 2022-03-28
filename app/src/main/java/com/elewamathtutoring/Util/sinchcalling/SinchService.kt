package com.elewamathtutoring.Util.sinchcalling

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.App.Companion.sinchClient
import com.elewamathtutoring.Util.sinchcalling.SinchStatus.*
import com.sinch.android.rtc.ClientRegistration
import com.sinch.android.rtc.SinchClient
import com.sinch.android.rtc.SinchClientListener
import com.sinch.android.rtc.SinchError
import com.sinch.android.rtc.calling.Call
import com.sinch.android.rtc.calling.CallClient
import com.sinch.android.rtc.calling.CallClientListener
import org.greenrobot.eventbus.EventBus


class SinchService : Service(), CallClientListener {
    private var callClient: CallClient? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            runAsForeground();
        } else {
        }
    }
    private fun runAsForeground() {
        val icon1 = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChannel(
                notificationManager
            ) else ""
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(notificationIcon)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.app_icon))
            .setAutoCancel(true)
            .setContentTitle(getString(R.string.app_name)).setContentText(getString(R.string.app_name))
             .setPriority(
                NotificationCompat.PRIORITY_MIN
            )
            .setCategory(NotificationCompat.CATEGORY_SERVICE).build()
        startForeground(101, notification)

//        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        mNotificationManager.cancel(101)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager): String {
        val channelId = "my_service_channelid"
        val channelName = "My Foreground Service"
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        // omitted the LED color
        channel.importance = NotificationManager.IMPORTANCE_NONE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        notificationManager.createNotificationChannel(channel)
        return channelId
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        sinchClient!!.addSinchClientListener(object : SinchClientListener {
            override fun onClientStarted(client: SinchClient) {
                callClient = client.callClient
                callClient!!.addCallClientListener(this@SinchService)
                App.callClient = callClient
                EventBus.getDefault().post(SinchConnected(client, callClient))
            }

            override fun onClientStopped(client: SinchClient) {
                EventBus.getDefault().post(SinchDisconnected(client))
            }

            override fun onClientFailed(client: SinchClient, error: SinchError) {
                EventBus.getDefault().post(SinchFailed(client, error))
            }

            override fun onRegistrationCredentialsRequired(
                client: SinchClient,
                registrationCallback: ClientRegistration
            ) {
            }

            override fun onLogMessage(level: Int, area: String, message: String) {
                EventBus.getDefault().post(SinchStatus.SinchLogger(area, message, level))
                Log.d("callz", "-- $message//$area")
            }
        })
        if (!sinchClient!!.isStarted) sinchClient!!.start()
        return START_STICKY
    }

    override fun onIncomingCall(callClient: CallClient, call: Call) {
        //  EventBus.getDefault().post(new SinchStatus.SinchIncommingCall(callClient, call));
        Log.d("callz", "ADA TELEPON MASUK: " + call.remoteUserId)
        var friendId: String? = ""
        var friendName: String? = ""
        var friendImage: String? = ""
        try {
            Log.d("friendInfo", "friendInfo: " + call.headers)
            friendId = call.headers["friendId"]
            friendName = call.headers["friendName"]
            friendImage = call.headers["friendImage"]
        } catch (e: Exception) {
        }
        // if (  App.Companion.getAppStatus().equals("online")) {
        val callscreen = Intent(this, SinchIncomingCallActivity::class.java)
        callscreen.putExtra("callid", call.callId)
        callscreen.putExtra("friendId", friendId)
        callscreen.putExtra("friendName", friendName)
        callscreen.putExtra("friendImage", friendImage)
        // callscreen.addFlags(FLAG_ACTIVITY_NEW_TASK);
        callscreen.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(callscreen)
        // }else {
        //      //sendMessagePush(call,friendName,friendImage);
        //  }
    }

    //    for oreo
    var CHANNEL_ID = "" // The id of the channel.
    var CHANNEL_ONE_NAME = "Channel One"
    var notificationManager: NotificationManager? = null
    var notificationChannel: NotificationChannel? = null
    var notification: Notification? = null
    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun sendMessagePush(call: Call, friendName: String, friendImage: String) {
        manager
        CHANNEL_ID = applicationContext.packageName
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ONE_NAME,
                NotificationManager.IMPORTANCE_MAX
            )
            notificationChannel!!.enableLights(true)
            notificationChannel!!.lightColor = Color.RED
            notificationChannel!!.setShowBadge(true)
            notificationChannel!!.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }
        var intent: Intent? = null
        intent = Intent(this, SinchIncomingCallActivity::class.java)
        intent.putExtra("callid", call.callId)
        intent.putExtra("friendName", friendName)
        intent.putExtra("friendImage", friendImage)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(this, i, intent, PendingIntent.FLAG_ONE_SHOT)
        val icon1 = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = Notification.Builder(applicationContext)
            .setSmallIcon(notificationIcon).setLargeIcon(icon1)
            .setStyle(Notification.BigTextStyle().bigText("Incoming voice call"))
            .setColor(ContextCompat.getColor(applicationContext, R.color.defaultColor))
            .setContentTitle(friendName)
            .setContentText("Incoming voice call")
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(CHANNEL_ID)
            notificationManager!!.createNotificationChannel(notificationChannel!!)
        }
        notification = notificationBuilder.build()
        notificationManager!!.notify(-1, notification)
    }

    private val manager: NotificationManager?
        private get() {
            if (notificationManager == null) {
                notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            }
            return notificationManager
        }
    private val notificationIcon: Int
        private get() {
            val useWhiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            return if (useWhiteIcon) R.drawable.ic_noti else R.drawable.app_icon
        }

    companion object {
        private const val i = 0
    }
}