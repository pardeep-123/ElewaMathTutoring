package com.elewamathtutoring.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.toolbar.*

class SignupActivity : AppCompatActivity(), View.OnClickListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        tvTerms.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        btnNext.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.tvTerms -> {
                startActivity(Intent(this, SignupActivity::class.java))
            } R.id.btnNext -> {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }
}