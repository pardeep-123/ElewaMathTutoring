package com.elewamathtutoring.Activity.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_login_guest.*
import kotlinx.android.synthetic.main.toolbar.*

class LoginGuestActivity : AppCompatActivity(), View.OnClickListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_guest)
        ivBack.setOnClickListener(this)
        btnLogin.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.btnLogin -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}