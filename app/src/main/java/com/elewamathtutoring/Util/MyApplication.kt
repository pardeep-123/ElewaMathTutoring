package com.elewamathtutoring.Util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.util.Log
import com.elewamathtutoring.R
import com.elewamathtutoring.api.RestApiInterface
import com.elewamathtutoring.network.ServiceGenerator

import com.google.android.libraries.places.api.Places

import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import java.util.*

class MyApplication : Application() {
    var preferences: SharedPreferences? = null
    var prefToken: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var editorToken: SharedPreferences.Editor? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        Album.initialize(
            AlbumConfig.newBuilder(this)
                .setAlbumLoader(MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        )
        Places.initialize(this, getString(R.string.google_maps_key))
        initializePreferences()
        initializePreferencesToken()
        Log.e(TAG, "FCM token : " + SharedPrefUtil.getInstance().fcmToken)


    }



    var restApiInterface: RestApiInterface? = null
    fun provideAuthservice(): RestApiInterface {
        if (restApiInterface == null) {
            restApiInterface = ServiceGenerator.createService(RestApiInterface::class.java)
            return restApiInterface!!
        } else {
            return restApiInterface!!
        }

    }


    fun checkIfHasNetwork(): Boolean {
        val cm =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    // initialize shared preferences
    private fun initializePreferences() {
        preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = preferences!!.edit()
    }

    // initialize shared preferences for token
    private fun initializePreferencesToken() {
        prefToken = getSharedPreferences(
            PREF_TOKEN,
            Context.MODE_PRIVATE
        )
        editorToken = prefToken!!.edit()
    }

    fun setString(key: String?, value: String?) {
        editor!!.putString(key, value)
        editor!!.commit()
    }

    fun getString(key: String?): String? {
        return preferences!!.getString(key, "")
    }

    fun setInt(key: String?, value: Int) {
        editor!!.putInt(key, value)
        editor!!.commit()
    }

    fun getInt(key: String?): Int {
        return preferences!!.getInt(key, 0)
    }

    fun clearData() {
        editor!!.clear().commit()
    }

    companion object {
        val TAG = MyApplication::class.java.simpleName
        private val PREF_NAME = "MyPref"
        var instance: MyApplication? = null

        const val PREF_TOKEN = "TruTraits"
        fun hasNetwork(): Boolean {
            return instance!!.checkIfHasNetwork()
        }

        fun getnstance(): MyApplication {
            return instance!!
        }
    }
}