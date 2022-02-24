package com.elewamathtutoring.Activity.Chat.mathChat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.Activity.Chat.StudyGroupActivity
import com.elewamathtutoring.Adapter.ParentOrStudent.MathChatAdapter
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_math_chat.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_math_chat.ivBack

class MathChatActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    lateinit var context:Context
    var userType = ""
    var type = ""
    var aboutResponse: MathChatResponse? = null
    lateinit var mathChatAdapter: MathChatAdapter
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math_chat)
        context=this
        rv_chat.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        btnCreateGroup.setOnClickListener(this)
        rlTutor.setOnClickListener(this)
        rlStudent.setOnClickListener(this)
        apibankAccounts("2")


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
                tvTutor.setTextColor(getResources().getColor(R.color.white))
                tvStudent.setTextColor(getResources().getColor(R.color.app))
                rlTutor.setBackgroundResource(R.drawable.bg_btn)
                rlStudent.setBackgroundResource(R.drawable.bg_btn_white)
                apibankAccounts("2")
            }
            R.id.rlStudent->{
                tvTutor.setTextColor(getResources().getColor(R.color.app))
                tvStudent.setTextColor(getResources().getColor(R.color.white))
                rlTutor.setBackgroundResource(R.drawable.bg_btn_white)
                rlStudent.setBackgroundResource(R.drawable.bg_btn)
                apibankAccounts("1")
            }
        }
    }
    fun apibankAccounts(userType: String) {
        this.userType=userType
        baseViewModel.getTeacherStudentList(this, userType,true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
    override fun onChanged(it: RestObservable?) {
        when (it!!.status) {
            Status.SUCCESS -> {
                if (it.data is MathChatResponse) {
                    aboutResponse=it.data
                  rv_chat.adapter = MathChatAdapter(this, aboutResponse!!, userType)
                }
            }
            Status.ERROR -> {
                if (it.error is MathChatResponse)
                    Helper.showSuccessToast(this, it.error.message)
            }
            else -> {}
        }
    }
}