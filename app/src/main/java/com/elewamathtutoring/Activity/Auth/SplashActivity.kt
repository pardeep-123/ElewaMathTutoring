package com.elewamathtutoring.Activity.Auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.elewamathtutoring.Activity.TeacherOrTutor.MainTeacherActivity
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.SharedPrefUtil
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.extensions.savePrefrence
import com.elewamathtutoring.Util.helper.extensions.savePrefrencewelcome


//cqlsystech123@gmail.com	cqlsys123@@@@	new	..
class SplashActivity : AppCompatActivity() {
    val context: Context = this
    lateinit var shared: SharedPrefUtil
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val w: Window = window

        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        setContentView(R.layout.activity_splash)
        shared = SharedPrefUtil(this)
        Handler(Looper.getMainLooper()).postDelayed({
           /* if (SharedPrefUtil.getInstance().isLogin) {
               *//* if ( savePrefrence(Constants.user_type, "1")){
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }else{
                    startActivity(Intent(this, MainTeacherActivity::class.java))
                    finishAffinity()
                }*//*
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()

            } else {
                val i = Intent(context, IntroSlider::class.java)
                startActivity(i)
            }*/
            val i = Intent(context, IntroSlider::class.java)
            startActivity(i)
        }, 1000)

    }
}