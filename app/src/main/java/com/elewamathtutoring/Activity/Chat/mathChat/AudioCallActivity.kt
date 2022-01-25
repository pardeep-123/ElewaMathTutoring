package com.elewamathtutoring.Activity.Chat.mathChat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_audio_call.*

class AudioCallActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_call)
        ivCross.setOnClickListener(this)
        ivCut.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivCross -> {
                finish()
            }
            R.id.ivCut -> {
                finish()
                // startActivity(Intent(this, VideoCallActivity::class.java))
            }
        }
        }
}