package com.elewamathtutoring.Activity.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.Activity.TeacherOrTutor.availability.AvailablityActivity
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_signup_teacher2.*

class SignupTeacher2Activity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_teacher2)
        ivBack.setOnClickListener(this)
        btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext -> {
                startActivity(Intent(this, AvailablityActivity::class.java))
            }R.id.ivBack -> {
            finish()
        }
        }
    }
}