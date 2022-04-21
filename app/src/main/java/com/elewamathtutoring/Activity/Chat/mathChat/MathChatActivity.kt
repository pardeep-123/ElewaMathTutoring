package com.elewamathtutoring.Activity.Chat.mathChat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.elewamathtutoring.Activity.Chat.StudyGroupActivity
import com.elewamathtutoring.Adapter.ParentOrStudent.MathChatAdapter
import com.elewamathtutoring.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_math_chat.*


class MathChatActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    lateinit var context: Context
    var viewType = ""
    var type = ""
    var teacherlevel = ArrayList<MathChatResponse.Body>()
    var aboutResponse: MathChatResponse? = null
    lateinit var mathChatAdapter: MathChatAdapter
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math_chat)
        context = this
        rv_chat.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        btnCreateGroup.setOnClickListener(this)
        rlTutor.setOnClickListener(this)
        rlStudent.setOnClickListener(this)

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterServices(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        if (intent.getStringExtra("tutor").equals("mathChat")) {
            llBtns.visibility = View.VISIBLE
            apiTeacherStudentList("2")
            viewType = "2"
        } else {
            llBtns.visibility = View.GONE
            apiTeacherStudentList("2")
            viewType = "1"
        }
    }

    private fun filterServices(text: String) {
//new array list that will hold the filtered data
        val filterServicesList = ArrayList<MathChatResponse.Body>()
//looping through existing elements
        for (s in teacherlevel) {
//if the existing elements contains the search input
            if (s.name.lowercase().contains(text.lowercase()) || s.teachingLevel.lowercase()
                    .contains(text.lowercase())
            ) {
//adding the element to filtered list
                filterServicesList.add(s)
            }
        }
        if (filterServicesList.size > 0) {
            when_nodatavideo.visibility = View.GONE
        } else {
            when_nodatavideo.visibility = View.VISIBLE
        }
//calling a method of the adapter class and passing the filtered list
        if(!teacherlevel.isNullOrEmpty()){
            mathChatAdapter.notifyData(filterServicesList)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.btnCreateGroup -> {
                startActivity(Intent(context, StudyGroupActivity::class.java))
            }
            R.id.rlTutor -> {
                tvTutor.setTextColor(getResources().getColor(R.color.white))
                tvStudent.setTextColor(getResources().getColor(R.color.app))
                rlTutor.setBackgroundResource(R.drawable.bg_btn)
                rlStudent.setBackgroundResource(R.drawable.bg_btn_white)
                apiTeacherStudentList("2")
                viewType = "2"
            }
            R.id.rlStudent -> {
                tvTutor.setTextColor(getResources().getColor(R.color.app))
                tvStudent.setTextColor(getResources().getColor(R.color.white))
                rlTutor.setBackgroundResource(R.drawable.bg_btn_white)
                rlStudent.setBackgroundResource(R.drawable.bg_btn)
                apiTeacherStudentList("1")
                viewType = "1"
            }

        }
    }
    fun apiTeacherStudentList(userType: String) {
        baseViewModel.getTeacherStudentList(this, userType,"", true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
    override fun onChanged(it: RestObservable?) {
        when (it!!.status) {
            Status.SUCCESS -> {
                if (it.data is MathChatResponse) {
                    teacherlevel.clear()
                    teacherlevel.addAll(it.data.body)
// aboutResponse = it.data
                    when_nodatavideo.visibility = View.GONE
                    mathChatAdapter = MathChatAdapter(this, teacherlevel,viewType)
                    rv_chat.adapter = mathChatAdapter
// rv_chat.adapter = MathChatAdapter(this, teacherlevel, viewType)
                }
            }
            Status.ERROR -> {
                if (it.error is MathChatResponse)
                    Helper.showSuccessToast(this, it.error.message)
            }
            else -> {
            }
        }
    }
}

//class MathChatActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
//    lateinit var context: Context
//    var viewType = ""
//    var type = ""
//    var aboutResponse: MathChatResponse? = null
//    lateinit var mathChatAdapter: MathChatAdapter
//    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_math_chat)
//        context = this
//        rv_chat.setOnClickListener(this)
//        ivBack.setOnClickListener(this)
//        btnCreateGroup.setOnClickListener(this)
//        rlTutor.setOnClickListener(this)
//        rlStudent.setOnClickListener(this)
//
//        if (intent.getStringExtra("tutor").equals("mathChat")) {
//            llBtns.visibility = View.VISIBLE
//            apiTeacherStudentList("2")
//            viewType = "2"
//        } else {
//            llBtns.visibility = View.GONE
//            apiTeacherStudentList("2")
//            viewType = "1"
//        }
//    }
//    override fun onClick(p0: View?) {
//        when (p0?.id) {
//            R.id.ivBack -> {
//                finish()
//            }
//            R.id.btnCreateGroup -> {
//                startActivity(Intent(context, StudyGroupActivity::class.java))
//            }
//            R.id.rlTutor -> {
//                tvTutor.setTextColor(getResources().getColor(R.color.white))
//                tvStudent.setTextColor(getResources().getColor(R.color.app))
//                rlTutor.setBackgroundResource(R.drawable.bg_btn)
//                rlStudent.setBackgroundResource(R.drawable.bg_btn_white)
//                apiTeacherStudentList("2")
//                viewType = "2"
//            }
//            R.id.rlStudent -> {
//                tvTutor.setTextColor(getResources().getColor(R.color.app))
//                tvStudent.setTextColor(getResources().getColor(R.color.white))
//                rlTutor.setBackgroundResource(R.drawable.bg_btn_white)
//                rlStudent.setBackgroundResource(R.drawable.bg_btn)
//                apiTeacherStudentList("1")
//                viewType = "1"
//            }
//        }
//    }
//    fun apiTeacherStudentList(userType: String) {
//        baseViewModel.getTeacherStudentList(this, userType,"", true)
//        baseViewModel.getCommonResponse().observe(this, this)
//    }
//    override fun onChanged(it: RestObservable?) {
//        when (it!!.status) {
//            Status.SUCCESS -> {
//                if (it.data is MathChatResponse) {
//                    aboutResponse = it.data
//                    rv_chat.adapter = MathChatAdapter(this, aboutResponse!!, viewType)
//                }
//            }
//            Status.ERROR -> {
//                if (it.error is MathChatResponse)
//                    Helper.showSuccessToast(this, it.error.message)
//            }
//            else -> {
//            }
//        }
//    }
//}