package com.elewamathtutoring.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.bg_toolbar.*

class AddCardActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        tv_title.setText("Payment Information")
        ivBack.setOnClickListener(this)
        btnPayNow.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }  R.id.btnPayNow -> {
            finish()
        }
        }
    }
}