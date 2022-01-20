package com.elewamathtutoring.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.bg_toolbar.*

class SettingsActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        tv_title.setText("Settings")
        ivBack.setOnClickListener(this)
        rlChangePassword.setOnClickListener(this)
        rlManagePayment.setOnClickListener(this)
        rlWithdrawel.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.rlChangePassword -> {
                startActivity(Intent(this, ChangePasswordActivity::class.java))
            }
            R.id.rlManagePayment -> {
                startActivity(Intent(this, PaymentInfoActivity::class.java))
            } R.id.rlWithdrawel -> {
            startActivity(Intent(this, SignupActivity::class.java))
            }
        }
    }
}