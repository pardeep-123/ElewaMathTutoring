package com.elewamathtutoring.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_signup_as.*
import kotlinx.android.synthetic.main.toolbar.*

class SignupAsActivity : AppCompatActivity(), View.OnClickListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_as)
        ivBack.setOnClickListener(this)
        cardStudent.setOnClickListener(this)
        cardTutor.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.cardStudent -> {
                startActivity(Intent(this, SignupActivity::class.java))
            }
            R.id.cardTutor -> {
                startActivity(Intent(this, SignupAsActivity::class.java))
            }
        }
    }
}