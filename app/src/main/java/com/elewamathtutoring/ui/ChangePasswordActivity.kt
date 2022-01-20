package com.elewamathtutoring.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.bg_toolbar.*

class ChangePasswordActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        tv_title.setText("Change Password")
        ivBack.setOnClickListener(this)
        btnChangePassword.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }  R.id.btnChangePassword -> {
                finish()
            }
        }
    }
}