package com.elewamathtutoring.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_withdrawel.*
import kotlinx.android.synthetic.main.bg_toolbar.*

class WithdrawelActivity : AppCompatActivity() , View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdrawel)
        tv_title.setText("Withdrawal")
        ivBack.setOnClickListener(this)
        btnWithdraw.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.btnWithdraw -> {
                startActivity(Intent(this, ChangePasswordActivity::class.java))
            }
        }
    }
}