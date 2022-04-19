package com.elewamathtutoring.Util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.StrictMode
import android.util.Log
import com.elewamathtutoring.Activity.Chat.AppLifecycleHandler
import com.elewamathtutoring.Activity.Chat.socket.SocketManager
import com.elewamathtutoring.dagger.DaggerDicomponent
import com.elewamathtutoring.dagger.Dicomponent
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import java.util.*

class App : Application(), AppLifecycleHandler.AppLifecycleDelegates{

    var context: Context? = null



    companion object {
        var appStatus = "offline"

        var USER_ID: String? = null

        @SuppressLint("StaticFieldLeak")

        private var mSocketManager: SocketManager? = null

        var dicomponent: Dicomponent? = null

        lateinit var instance: App

        fun getinstance(): App {
            return instance
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



    fun hasNetwork(): Boolean {
        return instance.checkIfHasNetwork()
    }

    private fun checkIfHasNetwork(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
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