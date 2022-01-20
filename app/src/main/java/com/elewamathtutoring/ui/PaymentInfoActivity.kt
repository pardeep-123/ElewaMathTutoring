package com.elewamathtutoring.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_payment_info.*
import kotlinx.android.synthetic.main.bg_toolbar.*

class PaymentInfoActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_info)
        tv_title.setText("Payment Information")
        ivBack.setOnClickListener(this)
        btnPayNow.setOnClickListener(this)
        rl_addCard.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.btnPayNow -> {
                finish()
            }
            R.id.rl_addCard -> {
                startActivity(Intent(this, AddCardActivity::class.java))
            }
        }
    }
}