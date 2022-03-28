package com.elewamathtutoring.Util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.util.Log
import com.elewamathtutoring.Activity.Chat.AppLifecycleHandler
import com.elewamathtutoring.Activity.Chat.socket.SocketManager
import com.elewamathtutoring.dagger.DaggerDicomponent
import com.elewamathtutoring.dagger.Dicomponent
import com.sinch.android.rtc.Sinch
import com.sinch.android.rtc.SinchClient
import com.sinch.android.rtc.calling.CallClient
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import java.util.*

class App : Application(), AppLifecycleHandler.AppLifecycleDelegates{

    var context: Context? = null



    companion object {
        var appStatus = "offline"

        var USER_ID: String? = null
        public var sinchClient: SinchClient? = null
        var callClient: CallClient? = null


        @SuppressLint("StaticFieldLeak")

        private var mSocketManager: SocketManager? = null

        var dicomponent: Dicomponent? = null

        lateinit var instance: App

        fun getinstance(): App {
            return instance
        }

        fun callClientSetup(userId: String?) {
            sinchClient = Sinch.getSinchClientBuilder().context(App.instance)
                .applicationKey("8aa7aa99-aadc-4575-897f-bf5d7d5ba414")
                .applicationSecret("9nduSrE400CsagsJajQDLQ==")
                .environmentHost("sandbox.sinch.com")
                .userId(userId)
                .build()
            sinchClient!!.setSupportActiveConnectionInBackground(
                true
            )
            sinchClient!!.startListeningOnActiveConnection()
            sinchClient!!.setSupportCalling(true)
        }

    }

    private var lifecycleHandler: AppLifecycleHandler? = null


    override fun onCreate() {
        super.onCreate()
        instance = this
        mSocketManager = getSocketManagernn()

        context = this

        dicomponent = DaggerDicomponent.builder().application(this).build()
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        Album.initialize(
            AlbumConfig.newBuilder(this)
                .setAlbumLoader(MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        )

      appStatus = "online"

        lifecycleHandler = AppLifecycleHandler(this)
        registerLifecycleHandler(lifecycleHandler!!)

    }

    private fun registerLifecycleHandler(lifeCycleHandler: AppLifecycleHandler) {
        registerActivityLifecycleCallbacks(lifeCycleHandler)
        registerComponentCallbacks(lifeCycleHandler)
    }





    fun getSocketManagernn(): SocketManager? {
        mSocketManager = if (mSocketManager == null) {
            SocketManager()
        } else {
            return mSocketManager
        }
        return mSocketManager
    }


    fun getmydicomponent(): Dicomponent {
        return dicomponent!!
    }

    override fun onAppForegrounded() {
//        if (SharedHelper.getKey(this, "id").isNotEmpty()) {
//            if (!mSocketManager!!.isConnected || mSocketManager!!.getmSocket() == null) {
        Log.e("ConnectSocket","Connect")
        if (!mSocketManager!!.isConnected() || mSocketManager!!.getmSocket() == null) {
            mSocketManager!!.init()
        }
      appStatus = "online"
//        }
    }

    override fun onTerminate() {
        super.onTerminate()

    }

    override fun onAppBackgrounded() {
        Log.e("DisconnectSocket","Disconnect")
     //    appStatus = "offline"
    }
}