package com.elewamathtutoring.Util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.util.Log
import com.elewamathtutoring.Activity.Chat.AppLifecycleHandler
import com.elewamathtutoring.Activity.Chat.socket.SocketManager
import com.elewamathtutoring.Activity.Chat.socket.SocketManagernewew
import com.elewamathtutoring.Activity.Chat.socket.SocketManagernewew.Companion.getSocket
import com.elewamathtutoring.dagger.DaggerDicomponent
import com.elewamathtutoring.dagger.Dicomponent
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import java.util.*

class App : Application(), AppLifecycleHandler.AppLifecycleDelegates{

    var context: Context? = null
    companion object {
        @SuppressLint("StaticFieldLeak")

        private var mSocketManager: SocketManager? = null

        var dicomponent: Dicomponent? = null

        lateinit var instance: App

        fun getinstance(): App {
            return instance
        }
    }

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

//        }
    }

    override fun onAppBackgrounded() {
        Log.e("DisconnectSocket","Disconnect")

    }
}