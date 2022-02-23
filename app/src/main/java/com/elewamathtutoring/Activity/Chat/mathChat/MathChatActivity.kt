package com.elewamathtutoring.Activity.Chat.mathChat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.Activity.Chat.StudyGroupActivity
import com.elewamathtutoring.Activity.ParentOrStudent.resources.ResoucesActivity
import com.elewamathtutoring.Adapter.ParentOrStudent.MathChatAdapter
import com.elewamathtutoring.Adapter.SearchHomeAdapter
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_math_chat.*
import kotlinx.android.synthetic.main.fragment_search.*

class MathChatActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var context:Context
    lateinit var mathChatAdapter: MathChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math_chat)
        context=this
        rv_chat.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        btnCreateGroup.setOnClickListener(this)
        rlTutor.setOnClickListener(this)
        rlStudent.setOnClickListener(this)

        mathChatAdapter = MathChatAdapter(this)
        rv_chat.adapter = mathChatAdapter

        if( intent.getStringExtra("tutor").equals("mathChat")){
            llBtns.visibility=View.VISIBLE
        }else{
            llBtns.visibility=View.GONE
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.ivBack->{
               finish()
            }
            R.id.btnCreateGroup->{
                startActivity(Intent(context, StudyGroupActivity::class.java))
            }
            R.id.rlTutor->{
                startActivity(Intent(context, StudyGroupActivity::class.java))
            }
            R.id.rlStudent->{
                startActivity(Intent(context, StudyGroupActivity::class.java))
            }
        }
    }
}