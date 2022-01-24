package com.elewamathtutoring.Activity.Chat.mathChat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.Adapter.ParentOrStudent.MathChatAdapter
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_math_chat.*

class MathChatActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math_chat)
        rv_chat.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        rv_chat.adapter = MathChatAdapter(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.ivBack->{
               finish()
            }
        }
    }
}