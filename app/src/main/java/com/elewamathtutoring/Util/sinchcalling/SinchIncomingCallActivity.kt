package com.elewamathtutoring.Util.sinchcalling

import com.elewamathtutoring.Util.App.Companion.callClient
import com.elewamathtutoring.Util.App.Companion.sinchClient
import androidx.appcompat.app.AppCompatActivity
import android.hardware.SensorEventListener
import android.media.Ringtone
import android.hardware.SensorManager
import android.os.Bundle
import android.view.WindowManager
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import android.media.RingtoneManager
import android.app.NotificationManager
import android.content.Intent
import android.graphics.PorterDuff
import android.hardware.Sensor
import android.os.CountDownTimer
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.elewamathtutoring.Util.sinchcalling.SinchStatus
import androidx.core.app.NotificationManagerCompat
import android.hardware.SensorEvent
import android.os.Build
import android.util.Log
import android.view.View
import com.sinch.android.rtc.calling.CallState
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import com.elewamathtutoring.Activity.Chat.socket.SocketManager
import com.elewamathtutoring.Activity.TeacherOrTutor.MainTeacherActivity
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.Util.helper.extensions.isMyServiceRunning
import com.sinch.android.rtc.calling.Call
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.lang.Exception

class SinchIncomingCallActivity : AppCompatActivity(), View.OnClickListener, SensorEventListener {
    private var mCallingStatus: TextView? = null
    private var mCallingName: TextView? = null
    private var mCallingNotify: LinearLayout? = null
    private var mCallingAnswer: Button? = null
    private var mCallingReject: Button? = null
    private var mCallingActionButton: LinearLayout? = null
    private var call: Call? = null
    private var r: Ringtone? = null
    private var isIncomming = false
    private var mSensorManager: SensorManager? = null
    private var mProximity: Sensor? = null
    private var mCallingBlacksreen: View? = null
    var rlMuteSpeaker: LinearLayout? = null
    var friendId=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.sinch_incoming_call_layout)
        initView()
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mProximity = mSensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        ifServiceNotStarted();
        try {
            friendId=intent.getStringExtra("friendId").toString()
        } catch (e: Exception) {
        }
        Log.e("callid",intent.getStringExtra("callid")!!)
        call = callClient!!.getCall(intent.getStringExtra("callid"))
        isIncomming = intent.getBooleanExtra("incomming", true)
        if (isIncomming) {
            setBlinking(mCallingNotify, true)
            mCallingStatus!!.text = "Incoming call"
            val friendName = intent.getStringExtra("friendName")
            mCallingName!!.text = friendName
            //    mCallingName.setText(call.getRemoteUserId()+"");
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            r = RingtoneManager.getRingtone(applicationContext, notification)
            UiUtils.setFullscreen(this, true)
            r!!.play()
        } else {
            UiUtils.setFullscreen(this, false)
            mCallingStatus!!.text = "Calling..."
            mCallingAnswer!!.visibility = View.GONE
            val friendName = intent.getStringExtra("friendName")
            mCallingName!!.text = friendName
            //  mCallingName.setText(call.getRemoteUserId()+"");
            mCallingReject!!.text = "END"
            timeCounter()
        }
        try {
            /*notification cancel code when app is background*/
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(-1)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            sinchClient!!.audioController.disableSpeaker()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {

        if (isTaskRoot) {
            if (getPrefrence("userType", "").equals("1")){
               var intent=Intent(this, MainActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }else{
                var intent =Intent(this, MainTeacherActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
           // super.onBackPressed()
        } else {
            finish()
        }

    }

    private fun ifServiceNotStarted() {

        var userId= getPrefrence(Constants.USER_ID,"")
        App.callClientSetup(userId)


        /* startService(new Intent(this, SinchService.class));*/


        /* startService(new Intent(this, SinchService.class));*/
        val mServiceIntent = Intent(this, SinchService::class.java)
        if (!isMyServiceRunning(this, SinchService::class.java)) {
            // startService(mServiceIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startService(mServiceIntent)
                // startForegroundService(mServiceIntent);
            } else {
                startService(mServiceIntent)
            }
        }
        if (App.Companion.callClient != null && App.Companion.sinchClient!!.isStarted) {
            Log.e("sinchClient", "Client Connected, ready to use!")
            App.Companion.sinchClient!!.audioController.disableSpeaker()
        }
    }

    fun onSwitchSpeakerphoneClicked(view: View) {
        val iv = view as ImageView
        if (iv.isSelected) {
            iv.isSelected = false
            iv.clearColorFilter()
            try {
                sinchClient!!.audioController.disableSpeaker()
            } catch (e: Exception) {
                // e.printStackTrace();
            }
        } else {
            iv.isSelected = true
            iv.setColorFilter(resources.getColor(R.color.defaultColor), PorterDuff.Mode.MULTIPLY)
            try {
                sinchClient!!.audioController.enableSpeaker()
            } catch (e: Exception) {
                // e.printStackTrace();
            }
        }
    }

    fun onLocalAudioMuteClicked(view: View) {
        val iv = view as ImageView
        if (iv.isSelected) {
            iv.isSelected = false
            iv.clearColorFilter()
            try {
                sinchClient!!.audioController.unmute()
            } catch (e: Exception) {
                // e.printStackTrace();
            }
        } else {
            iv.isSelected = true
            iv.setColorFilter(resources.getColor(R.color.defaultColor), PorterDuff.Mode.MULTIPLY)
            try {
                sinchClient!!.audioController.mute()
            } catch (e: Exception) {
                // e.printStackTrace();
            }
        }
    }

    var mCounter: CountDownTimer? = null
    override fun onDestroy() {
        super.onDestroy()
        endCall()
    }

    private fun timeCounter() {
        mCounter = object : CountDownTimer(13000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.e("Tag", "seconds remaining: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                endCall()
            }
        }.start()
    }
    private var socketManager: SocketManager? = null

    private fun endCall() {
        try {

            try {
                socketManager = App.instance.getSocketManagernn()
                if (!socketManager!!.isConnected() || socketManager!!.getmSocket() == null) {
                    socketManager!!.init()
                }

                //var userId= getPrefrence(Constants.USER_ID,"")
                var jsonObject= JSONObject()
                jsonObject.put("friendId",friendId)
                socketManager!!.endCallSocket(jsonObject)
            } catch (e: Exception) {
            }

            try {
                mCounter!!.cancel()
            } catch (e: Exception) {
            }
            call!!.hangup()
            if (r != null) r!!.stop()
            onBackPressed()
        } catch (e: Exception) {
            //   e.printStackTrace();
            onBackPressed()
        }
    }

    private fun initView() {
        mCallingStatus = findViewById(R.id.calling_status)
        mCallingName = findViewById(R.id.calling_name)
        mCallingNotify = findViewById(R.id.calling_notify)
        mCallingAnswer = findViewById(R.id.calling_answer)
        mCallingAnswer!!.setOnClickListener(this)
        mCallingReject = findViewById(R.id.calling_reject)
        mCallingReject!!.setOnClickListener(this)
        mCallingActionButton = findViewById(R.id.calling_action_button)
        mCallingBlacksreen = findViewById(R.id.calling_blackscreen)
        rlMuteSpeaker = findViewById(R.id.rl_mute_speaker)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.calling_answer -> try {
                try {
                    mCounter!!.cancel()
                } catch (e: Exception) {
                }
                call!!.answer()
                mCallingAnswer!!.visibility = View.GONE
                mCallingReject!!.text = "END"
                mCallingStatus!!.text = "ACTIVE CALL"
                setBlinking(mCallingNotify, false)
                if (r != null) r!!.stop()
                UiUtils.setFullscreen(this, false)
            } catch (e: Exception) {
                e.printStackTrace()
                onBackPressed()
            }
            R.id.calling_reject ->                /* call.hangup();
                if(r!=null)r.stop();
                finish();*/endCall()
            else -> {
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSinchLogging(sinchLogger: SinchStatus.SinchLogger) {
        runOnUiThread {
            if (sinchLogger.message.contains("terminationCause=DENIED") && !isIncomming) {
                if (r != null) r!!.stop()
                call!!.hangup()
                Toast.makeText(this@SinchIncomingCallActivity, "USER REJECTED", Toast.LENGTH_SHORT)
                    .show()
                onBackPressed()
            } else if (sinchLogger.message.contains("onSessionEstablished")) {


                   try {
                       mCounter!!.cancel()
                   } catch (e: Exception) {
                   }
                   rlMuteSpeaker!!.visibility = View.GONE
                   mCallingStatus!!.text = "ACTIVE CALL"

            } else if (sinchLogger.message.contains("onSessionTerminated")) {
                call!!.hangup()
                if (r != null) r!!.stop()
                if (sinchLogger.message.contains("terminationCause=NO_ANSWER")) {
                    Toast.makeText(this@SinchIncomingCallActivity, "NO ANSWER", Toast.LENGTH_SHORT)
                        .show()
                } else if (sinchLogger.message.contains("terminationCause=TIMEOUT")) {
                    Toast.makeText(this@SinchIncomingCallActivity, "TIMEOUT", Toast.LENGTH_SHORT)
                        .show()
                } else if (sinchLogger.message.contains("terminationCause=CANCELED")) {
                    val n = NotificationCompat.Builder(this@SinchIncomingCallActivity, "calling")
                        .setContentTitle("Missed Call").setAutoCancel(true)
                        .setContentText("You have missed call from " + call!!.remoteUserId)
                        .setSmallIcon(android.R.drawable.sym_call_missed).build()
                    NotificationManagerCompat.from(this@SinchIncomingCallActivity).notify(1133, n)
                }
                onBackPressed()
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    private fun setBlinking(`object`: View?, status: Boolean) {
        /*  if(!status){
            object.animate().cancel();
            return;
        }
        ObjectAnimator anim= ObjectAnimator.ofFloat(object, View.ALPHA, 0.1f,1.0f);
        anim.setDuration(1000);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.start();*/
    }

    override fun onSensorChanged(event: SensorEvent) {
        val params = window.attributes
        if (event.values[0] == 0f) {
            if (!isIncomming || call!!.state == CallState.ESTABLISHED) {
                params.flags = params.flags or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                params.screenBrightness = 0f
                window.attributes = params
                UiUtils.enableDisableViewGroup(
                    findViewById<View>(R.id.calling_root).parent as ViewGroup,
                    false
                )
                UiUtils.setFullscreen(this, true)
                mCallingBlacksreen!!.visibility = View.VISIBLE
            }
        } else {
            params.screenBrightness = -1f
            window.attributes = params
            UiUtils.enableDisableViewGroup(
                findViewById<View>(R.id.calling_root).parent as ViewGroup,
                true
            )
            UiUtils.setFullscreen(this, false)
            mCallingBlacksreen!!.visibility = View.GONE
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL)
    }
}