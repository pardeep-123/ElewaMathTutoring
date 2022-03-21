package com.elewamathtutoring.Activity.Auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.elewamathtutoring.Activity.Auth.login.LoginScreen
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.SharedPrefUtil
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.extensions.savePrefrence
import kotlinx.android.synthetic.main.activity_sign_up_as.*

class SignUpAs : AppCompatActivity(),View.OnClickListener {
    var context: Context = this
    lateinit var shared: SharedPrefUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_as)
        shared = SharedPrefUtil(this)
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        llStudent.setOnClickListener(this)
        llTeacher.setOnClickListener(this)
    /* l1.setOnClickListener {
    ///////////////////student
            savePrefrence(Constants.user_type, "2")
            startActivity(Intent(this@SignUpAs, LoginScreen::class.java))
        }
        l2.setOnClickListener {
        ////////////teacher
            savePrefrence(Constants.user_type, "1")
            startActivity(Intent(this@SignUpAs, LoginScreen::class.java))
        }*/
    }
    override fun onBackPressed() {
        finishAffinity()
    }
    override fun onClick(v: View?) {
        when(v?.id) {
             R.id.llStudent -> {
                 savePrefrence(Constants.user_type, "1")
                 startActivity(Intent(this, LoginScreen::class.java)
                    .putExtra("Name", "Student")
                )
            } R.id.llTeacher -> {
                savePrefrence(Constants.user_type, "2")
                startActivity(Intent(this, LoginScreen::class.java)
                    .putExtra("Name","Tutor")
                )
            }
        }
    }
}