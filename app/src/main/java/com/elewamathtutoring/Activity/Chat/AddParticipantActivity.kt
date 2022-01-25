package com.elewamathtutoring.Activity.Chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.Adapter.ParentOrStudent.AddParticipantsAdapter
import com.elewamathtutoring.Adapter.ParentOrStudent.MathChatAdapter
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_add_participant.*
import kotlinx.android.synthetic.main.activity_add_participant.ivBack
import kotlinx.android.synthetic.main.activity_add_participant.rv_chat
import kotlinx.android.synthetic.main.activity_math_chat.*

class AddParticipantActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var addParticipantsAdapter: AddParticipantsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_participant)
        ivBack.setOnClickListener(this)
        addParticipantsAdapter = AddParticipantsAdapter(this)
        rv_chat.adapter = addParticipantsAdapter


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
           /* R.id.btnNext -> {
                startActivity(Intent(this, AddParticipantActivity::class.java))
            }*/
        }
    }
}