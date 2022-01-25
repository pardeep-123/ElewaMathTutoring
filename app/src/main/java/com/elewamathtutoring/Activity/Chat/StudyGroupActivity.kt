package com.elewamathtutoring.Activity.Chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_study_group.*

class StudyGroupActivity : AppCompatActivity(), View.OnClickListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_group)
        btnNext.setOnClickListener(this)
        ivBack.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.btnNext -> {
                startActivity(Intent(this, AddParticipantActivity::class.java))
            }
        }
    }
}