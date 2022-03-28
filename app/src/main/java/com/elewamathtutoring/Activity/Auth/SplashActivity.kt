package com.elewamathtutoring.Activity.Auth

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.elewamathtutoring.Activity.TeacherOrTutor.MainTeacherActivity
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.SharedPrefUtil
import com.elewamathtutoring.Util.helper.extensions.getPrefrence


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


        setUpPermission()

    }


    private fun setUpPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionForAll()
                //   return false;
            } else {
                gotoActivity()
                //  return true;
            }
        } else {
            gotoActivity()
            // return true;
        }
    }

    private fun gotoActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
         if(shared.isLogin!=null){
             if (shared.isLogin) {
                 if (getPrefrence("userType", "").equals("1")){
                     startActivity(Intent(this, MainActivity::class.java))
                     finishAffinity()
                 }else{
                     startActivity(Intent(this, MainTeacherActivity::class.java))
                     finishAffinity()
                 }


                } else {
                    val i = Intent(context, IntroSlider::class.java)
                    startActivity(i)
                }
            }

        }, 1000)

    }

    fun requestPermissionForAll() {
        /*  requestPermissions(
                new String[] {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, PERMISSION_REQUEST_CODE_FOR_SCANNER
        );*/
        ActivityCompat.requestPermissions(
            this, arrayOf(
               Manifest.permission.RECORD_AUDIO
            ), 456
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            456 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Toast.makeText(mContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                    // main logic
                    gotoActivity()
                } else {
                    /* Toast.makeText(activity!!, "Permission Denied", Toast.LENGTH_SHORT).show()*/
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissionForAll()
                            }
                        }
                    }
                }
            }
        }
    }


}