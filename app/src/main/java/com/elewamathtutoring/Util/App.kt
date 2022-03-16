package com.elewamathtutoring.Util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.util.Log
import com.elewamathtutoring.Activity.Chat.AppLifecycleHandler
import com.elewamathtutoring.Activity.Chat.socket.SocketManagernewew
import com.elewamathtutoring.Activity.Chat.socket.SocketManagernewew.Companion.getSocket
import com.elewamathtutoring.dagger.DaggerDicomponent
import com.elewamathtutoring.dagger.Dicomponent
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import java.util.*

class App : Application(), AppLifecycleHandler.AppLifecycleDelegates{

   private var mSocketManagernew: SocketManagernewew? = null
    var context: Context? = null
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var application: App
        var dicomponent: Dicomponent? = null

        @get:Synchronized
        var instance: App? = null
            private set

        fun getinstance(): App {
            return application
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        getSocketManagernn()
        context = applicationContext
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


    fun getSocketManagernn(): SocketManagernewew {
        return if (mSocketManagernew == null) {
            mSocketManagernew = getSocket()
            mSocketManagernew as SocketManagernewew
        }
        else
        {
            mSocketManagernew as SocketManagernewew
        }
    }


    fun getmydicomponent(): Dicomponent {
        return dicomponent!!
    }

    override fun onAppForegrounded() {
//        if (SharedHelper.getKey(this, "id").isNotEmpty()) {
//            if (!mSocketManager!!.isConnected || mSocketManager!!.getmSocket() == null) {
        Log.e("ConnectSocket","Connect")
        if (!mSocketManagernew!!.isConnected() || mSocketManagernew!!.mSocket == null) {
            mSocketManagernew!!.init()
        }

//        }
    }

    override fun onAppBackgrounded() {
        Log.e("DisconnectSocket","Disconnect")
        mSocketManagernew!!.onDisconnect()
    }
}