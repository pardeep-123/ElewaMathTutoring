package com.elewamathtutoring.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ivBack.setOnClickListener(this)
        btnGuest.setOnClickListener(this)
        btnCreate.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.btnGuest -> {
                startActivity(Intent(this, LoginGuestActivity::class.java))
            }
            R.id.btnCreate -> {
                startActivity(Intent(this, SignupAsActivity::class.java))
            }
        }
    }
}