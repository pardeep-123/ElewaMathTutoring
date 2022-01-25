package com.elewamathtutoring.Activity.Chat.mathChat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.Activity.Auth.SignUpAs
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_math_person_chat.*

class MathPersonChatActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math_person_chat)
        ivBack.setOnClickListener(this)
        ivVideo.setOnClickListener(this)
        ivAudio.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.ivVideo -> {
                startActivity(Intent(this, VideoCallActivity::class.java))
            }
            R.id.ivAudio -> {
                startActivity(Intent(this, AudioCallActivity::class.java))
            }


        }
    }
}