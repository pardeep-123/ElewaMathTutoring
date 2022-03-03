package com.elewamathtutoring.Activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.ParentOrStudent.payment.PaymentInfoActivity
import com.elewamathtutoring.Activity.ParentOrStudent.teacherDetail.TeacherDetailResponse
import com.elewamathtutoring.Models.Teacher_details.Body
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_schedule_a_session2.*
import java.util.*

class ScheduleASession2Activity : AppCompatActivity(), View.OnClickListener {
    var profile = ArrayList<TeacherDetailResponse.Body>()
    var selectedprice=0
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    var perHour=""
    var value=0
    var selected_Session=1
    var selected_Timeslot=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_a_session2)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT;
        ivBack.setOnClickListener(this)
     // rlInPerson.setOnClickListener(this)
      //  rlVirtualLearning.setOnClickListener(this)
        tvConfirmSession.setOnClickListener(this)
        profile = (intent.getSerializableExtra("teacher_detail") as ArrayList<TeacherDetailResponse.Body>?)!!

       /* for (i in 0 until profile.get(0).time_slots.size) {  // GET TOTAL SELECTED TIMESLOT
            if (profile.get(0).time_slots.get(i).check == true) {
                selected_Timeslot++
            }
        }*/
        tvTime.text = selected_Timeslot.toString()+" hour @"+ Constants.Currency+profile.get(0).InPersonRate.toString()+"/hr"
        selectedprice = profile.get(0).InPersonRate
        val total=selected_Timeslot*profile.get(0).InPersonRate
        tvSessionCost.text = Constants.Currency+total.toString()

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                finish()
            }
          /*  R.id.rlInPerson -> {
                ivTickInPerson.setImageResource(R.drawable.tick_blue)
                ivTickVirtualLearning.setImageResource(R.drawable.uncheck)
                tvSessionName.text = "In-Person Session"
                selected_Session=1
                tvTime.text = selected_Timeslot.toString() + " hour @" + Constants.Currency + profile.get(0).InPersonRate.toString() + "/hr"
                selectedprice = selected_Timeslot * profile.get(0).InPersonRate
                var total = selected_Timeslot * profile.get(0).InPersonRate
                tvSessionCost.text = Constants.Currency + total.toString()
            }
            R.id.rlVirtualLearning -> {
                ivTickInPerson.setImageResource(R.drawable.uncheck)
                ivTickVirtualLearning.setImageResource(R.drawable.tick_blue)
                tvSessionName.text = "Virtual Session"
                selected_Session=2
                tvTime.text =
                    selected_Timeslot.toString() + " hour @" + Constants.Currency + profile.get(0).virtualRate.toString() + "/hr"

                selectedprice =selected_Timeslot * profile.get(0).virtualRate
                var total = selected_Timeslot * profile.get(0).virtualRate
                tvSessionCost.text = Constants.Currency + total.toString()
            }*/
            R.id.tvConfirmSession -> {
                if(etSchedule.text.toString().isEmpty())
                {
                    Helper.showErrorAlert(this, "Please enter what you're looking for?")
                }
                else
                {
                    booking()
                }
             //   booking()
            }
        }
    }
    fun booking(){
        val inten = Intent(this, PaymentInfoActivity::class.java)
        inten.putExtra("teacher_detail", profile)
        inten.putExtra("aboutdetail", etSchedule.text.toString())
        inten.putExtra("selecteddate", intent.getStringExtra("selecteddate"))
        inten.putExtra("selected_Session", selected_Session.toString())
        startActivity(inten)
    }
   }