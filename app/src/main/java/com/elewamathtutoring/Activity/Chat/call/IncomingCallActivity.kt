package com.elewamathtutoring.Activity.Chat.call

import android.app.NotificationManager
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.Chat.socket.SocketManager
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.constant.Constants
import kotlinx.android.synthetic.main.activity_incoming_call.*
import org.json.JSONArray
import org.json.JSONObject

class IncomingCallActivity : AppCompatActivity(), SocketManager.Observer {
    var mCallerId = 0
    var mrecieverID = 0
    var mSenderImage = ""
    var callerName = ""
    var mChannelName = ""
    var agoraToken = ""
    var requestId = ""
    var callerImage = ""
    var receiverId = ""
    var type = ""
    // private var mAnimator: PortraitAnimator? = null
    private var mPlayer: MediaPlayer? = null
    private var mCounter: CountDownTimer? = null
    private lateinit var socketManager: SocketManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incoming_call)
        initializeSocket()
        activateReceiverListenerSocket()
/*
friendId",
friendName"
friendImage
channelName
token", not
 */
        receiverId = intent.getStringExtra("friendId").toString()
        mChannelName = intent.getStringExtra("channelName").toString()
        agoraToken = intent.getStringExtra("token").toString()
        // requestId = intent.getStringExtra("requestId").toString()
        callerName = intent.getStringExtra("friendName").toString()
        callerImage = intent.getStringExtra("friendImage").toString()
        type = intent.getStringExtra("type").toString()
        tvUserName.text = callerName
        Glide.with(this).load(Constants.IMAGE_URL+callerImage).into(circleImageView4)

        Log.e("channelName", mChannelName)
        //videoToken = intent.getStringExtra("videoToken")!!


        setOnClicks()

//        mAnimator = PortraitAnimator(
//            findViewById(R.id.anim_layer_1),
//            findViewById(R.id.anim_layer_2),
//            findViewById(R.id.anim_layer_3)
//        )
        timeCounter()
        //startRinging()


    }


    fun activateReceiverListenerSocket() {
//        socketManager.call_statusActivate()
    }

    private fun setOnClicks() {
        phone_call_btn.setOnClickListener {
            stopRinging()
            if (App.getinstance().hasNetwork()) {
//                val jsonObject = JSONObject()
//                //jsonObject.put("requestId", requestId)
//                jsonObject.put("userType", "1")
//                jsonObject.put("status", CALL_CONNECTED.toString())
//                jsonObject.put("channelName", mChannelName)
//                jsonObject.put("callDuration", "")
//                jsonObject.put("type", VOICE)
//                jsonObject.put("friendId", receiverId)
//                socketManager.callStatus(jsonObject)
           if (type == "2") {
               val intent = Intent(this@IncomingCallActivity, VideoCallActivity::class.java)
               // intent.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
               intent.putExtra("friendId", receiverId)
               intent.putExtra("channelName", mChannelName)
               intent.putExtra("agoraToken", agoraToken)
               // intentAction.putExtra("requestId", callData.requestId.toString());
               intent.putExtra("friendName", callerName)
               intent.putExtra("friendImage", callerImage)
               startActivity(intent)
               finish()
           }else{
               val intent = Intent(this@IncomingCallActivity, AudioCallActivity::class.java)
               // intent.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
               intent.putExtra("friendId", receiverId)
               intent.putExtra("channelName", mChannelName)
               intent.putExtra("agoraToken", agoraToken)
               // intentAction.putExtra("requestId", callData.requestId.toString());
               intent.putExtra("friendName", callerName)
               intent.putExtra("friendImage", callerImage)
               startActivity(intent)
               finish()
           }
            }

        }
        cancel_call_btn.setOnClickListener {
            stopRinging()
             if (App.getinstance().hasNetwork()) {
                 val jsonObject = JSONObject()
                 jsonObject.put("status", "3")
                 jsonObject.put("channelName", mChannelName)

                 socketManager.callStatus(jsonObject)

                 val notifManager: NotificationManager =
                     getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                 notifManager.cancelAll()
             }
            //   showAlertWithOk(resources.getString(R.string.internet_connection))
        }
    }

    private fun initializeSocket() {

        socketManager = App.instance.getSocketManagernn()!!
        if (!socketManager.isConnected() || socketManager.getmSocket() == null) {
            socketManager.init()
        }
        socketManager.unRegister(this)
        socketManager.onRegister(this)

    }


    override fun onDestroy() {
        socketManager.unRegister(this)

        super.onDestroy()
        stopRinging()
        mCounter?.cancel()
    }

    override fun onResume() {
        super.onResume()
        socketManager.unRegister(this)
        socketManager.onRegister(this)
    }

    private fun startRinging() {
        /*if (isCallee()) {
            mPlayer = playCalleeRing()
        } else if (isCaller()) {*/
        mPlayer = playCallerRing()
        /*}*/
    }

    private fun playCallerRing(): MediaPlayer {
        return startRinging(R.raw.basic_tones)
    }


    private fun startRinging(resource: Int): MediaPlayer {
        val player = MediaPlayer.create(this, resource)
        player.isLooping = true
        player.start()
        return player
    }

    private fun stopRinging() {

        try {
            runOnUiThread {
                if (mPlayer != null && mPlayer?.isPlaying!!) {
                    mPlayer?.stop()
                    mPlayer?.release()
                    mPlayer = null
                }
            }

        } catch (e: Exception) {
        }
    }

//    override fun onStart() {
//        super.onStart()
//        mAnimator?.start()
//    }

    override fun onStop() {
        super.onStop()
        stopRinging()
//        mAnimator?.stop()
        socketManager.unRegister(this)
    }


    private fun timeCounter() {
        //mCounter = object : CountDownTimer(45000, 1000) {
        mCounter = object : CountDownTimer(45000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                Log.e("Tag", "seconds remaining: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                //showToast(resources.getString(R.string.no_answer))
                /* val jsonObject = JSONObject()
                 jsonObject.put("userType", "1")
                 jsonObject.put("status", CALL_DECLINE.toString())
                 jsonObject.put("channelName", mChannelName)
                 jsonObject.put("callDuration", "")
                 jsonObject.put("type", VOICE)
                 jsonObject.put("friendId", receiverId)
                 socketManager.callStatus(jsonObject)*/
                finish()

//                val notifManager: NotificationManager =
//                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                notifManager.cancelAll()
//                finish()
            }
        }.start()
    }

    private class PortraitAnimator internal constructor(
        private val mLayer1: View,
        private val mLayer2: View,
        private val mLayer3: View
    ) {
        private val mAnim1: Animation
        private val mAnim2: Animation
        private val mAnim3: Animation
        private var mIsRunning = false
        private fun buildAnimation(startOffset: Int): AnimationSet {
            val set = AnimationSet(true)
            val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
            alphaAnimation.duration = ANIM_DURATION.toLong()
            alphaAnimation.startOffset = startOffset.toLong()
            alphaAnimation.repeatCount = Animation.INFINITE
            alphaAnimation.repeatMode = Animation.RESTART
            alphaAnimation.fillAfter = true
            val scaleAnimation = ScaleAnimation(
                1.0f, 1.3f, 1.0f, 1.3f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f
            )
            scaleAnimation.duration = ANIM_DURATION.toLong()
            scaleAnimation.startOffset = startOffset.toLong()
            scaleAnimation.repeatCount = Animation.INFINITE
            scaleAnimation.repeatMode = Animation.RESTART
            scaleAnimation.fillAfter = true
            set.addAnimation(alphaAnimation)
            set.addAnimation(scaleAnimation)
            return set
        }

        fun start() {
            if (!mIsRunning) {
                mIsRunning = true
                mLayer1.visibility = View.VISIBLE
                mLayer2.visibility = View.VISIBLE
                mLayer3.visibility = View.VISIBLE
                mLayer1.startAnimation(mAnim1)
                mLayer2.startAnimation(mAnim2)
                mLayer3.startAnimation(mAnim3)
            }
        }

        fun stop() {
            mLayer1.clearAnimation()
            mLayer2.clearAnimation()
            mLayer3.clearAnimation()
            mLayer1.visibility = View.GONE
            mLayer2.visibility = View.GONE
            mLayer3.visibility = View.GONE
        }

        companion object {
            const val ANIM_DURATION = 3000
        }

        init {
            mAnim1 = buildAnimation(0)
            mAnim2 = buildAnimation(1000)
            mAnim3 = buildAnimation(2000)
        }
    }

    override fun onBackPressed() {
        // showToast("Please accept or decline call")
    }

    override fun onResponseArray(event: String, args: JSONArray) {

    }

    override fun onResponse(event: String, args: JSONObject) {

        when (event) {

            SocketManager.callStatus -> {
              runOnUiThread {
                  finish()
              }
            }

        }

    }

    override fun onError(event: String, vararg args: Array<*>) {

    }

}
