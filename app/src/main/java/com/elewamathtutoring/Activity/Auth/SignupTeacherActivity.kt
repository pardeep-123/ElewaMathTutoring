package com.elewamathtutoring.Activity.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfoActivity
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignupTeacherActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_teacher)
        ivBack.setOnClickListener(this)
        btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext -> {
                startActivity(Intent(this, TeachingInfoActivity::class.java))
            }R.id.ivBack -> {
              finish()
            }
        }
    }
}