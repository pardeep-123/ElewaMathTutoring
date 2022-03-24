package com.elewamathtutoring.Activity.ParentOrStudent.teacherDetail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.Chat.Chat_Activity
import com.elewamathtutoring.Activity.Chat.mathChat.MathPersonChatActivity
import com.elewamathtutoring.Activity.ParentOrStudent.ReceiptActivity
import com.elewamathtutoring.Activity.ParentOrStudent.session.ScheduleASessionActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.request.RequestDetailResponse
import com.elewamathtutoring.Models.Modecommon.Commontoall
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.SharedPrefUtil
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.constant.Constants.Companion.applyDim
import com.elewamathtutoring.Util.constant.Constants.Companion.clearDim
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel

import kotlinx.android.synthetic.main.activity_teacher_details.*
import kotlinx.android.synthetic.main.dialog_report.*
import kotlinx.android.synthetic.main.report_popup.view.*

class TeacherDetailsActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    var myPopupWindow: PopupWindow? = null
    lateinit var sharedPrefUtil: SharedPrefUtil
    var teacherdetails = ArrayList<RequestDetailResponse.Body>()
    var tutordetails = ArrayList<TeacherDetailResponse.Body>()
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    var receiverId = ""
    var subjectList = ArrayList<String>()
    var subjects = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_details)
        sharedPrefUtil = SharedPrefUtil(this)
        onClicks()
        if(intent.getStringExtra("Type").equals("schedule")) {
            btnScheduleSession.visibility = View.GONE
            btnviewreciept.visibility = View.VISIBLE
            apiTecherdetail()

        } else if (intent.getStringExtra("Type").equals("PastTeacher")) {
            btnScheduleSession.visibility = View.GONE
            btnviewreciept.visibility = View.VISIBLE
            apiTecherdetail()
            btnScheduleSession.setText("Reschedule")
        } else {
            btnScheduleSession.visibility = View.VISIBLE
            Techerdetailapi()

        }
    }
    private fun onClicks() {
        options.setOnClickListener(this)
        btnScheduleSession.setOnClickListener(this)
        btnReschedule.setOnClickListener(this)
        btnviewreciept.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        llChat.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.options -> {
                setPopUpWindow()
            }

            R.id.ivBack -> {
                finish()
            }

            R.id.llChat -> {
                var intent = Intent(this, Chat_Activity::class.java)
                intent.putExtra("receiverId",receiverId)
                intent.putExtra("chatUserName",tv_techername.text.toString())
                startActivity(intent)
            }

            R.id.btnScheduleSession -> {
                val intent = Intent(this, ScheduleASessionActivity::class.java)
                intent.putExtra("teacher_detail", tutordetails)
                startActivity(intent)
            }

            R.id.btnviewreciept -> {
                val intentt = Intent(this, ReceiptActivity::class.java)
                intentt.putExtra("listdata", teacherdetails)
                intentt.putExtra("session_id", intent.getStringExtra("session_id").toString())
                startActivity(intentt)
            }
        }
    }
    private fun setPopUpWindow() {
        val viewGroup: ViewGroup = window.decorView.rootView as ViewGroup
        val inflater =
            applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.report_popup, null)
        myPopupWindow = PopupWindow(view, 400, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        options.setOnClickListener {
            myPopupWindow?.showAsDropDown(it, -0, -35)
            applyDim(viewGroup, 0.5f)
        }
        view.report_cardview.setOnClickListener { dialogReport() }
        myPopupWindow?.setOnDismissListener {
            clearDim(viewGroup)
        }
    }
    private fun dialogReport() {
        val reportDialog = Dialog(this)
        reportDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        reportDialog.setContentView(R.layout.dialog_report)
        reportDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        reportDialog.report_cancel.setOnClickListener {
            reportDialog.cancel()
            myPopupWindow!!.dismiss()

        }
        reportDialog.report_Submit.setOnClickListener {
            baseViewModel.report(this,
                reportDialog.etText.text.toString(),
                intent.getStringExtra("teacher_id").toString(),
                true
            )
            baseViewModel.getCommonResponse().observe(this, this)
            reportDialog.cancel()
            myPopupWindow!!.dismiss()

        }

        reportDialog.window!!.setGravity(Gravity.CENTER)
        reportDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        reportDialog.setCancelable(true)
        reportDialog.setCanceledOnTouchOutside(true)
        reportDialog.show()
    }
    @SuppressLint("SetTextI18n")
    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is RequestDetailResponse) {
                    teacherdetails.clear()
                    teacherdetails.addAll(listOf(liveData.data.body))
                    Glide.with(this).load(liveData.data.body.Teacher.image)
                        .placeholder(R.drawable.profile_unselected).into(ivtecher_image)
                    tv_techername.setText(liveData.data.body.Teacher.name)
                    tvparentSpecialized.text = liveData.data.body.Teacher.specialties
                    tv_teachercertified.text = Constants.isCertifiedOrtutor(liveData.data.body.Teacher.isCertifiedOrtutor)
                    tv_about.setText("About " + liveData.data.body.Teacher.name)
                    tv_teacher_AboutUser.setText(liveData.data.body.Teacher.about)
                    tvDate.text = Constants.convertDateToRatingTime(liveData.data.body.date.toLong())
                    tvTime.text= liveData.data.body.timeslot[0].startTime+" - "+liveData.data.body.timeslot[0].endTime
                    tv_teacher_TeachingHistory.text = liveData.data.body.Teacher.teachingHistory

                    val s = tv_parentteacherlevel.text.toString()
                    tv_parentteacherlevel.text = s.substring(0, s.length - 1)
                     } else if (liveData.data is Commontoall) {
                    finish()
                }
                else if (liveData.data is TeacherDetailResponse) {
                    tutordetails.addAll(listOf(liveData.data.body))
                    tv_techername.text = liveData.data.body.name
                    receiverId = liveData.data.body.id.toString()
                   // if (liveData.data.body.teaching_level!="")
                    tv_teachercertified.text = liveData.data.body.teaching_level[0].level
                    /**
                     * @author Pardeep Sharma
                     *  to set specialties from subjects
                     */
                    liveData.data.body.subjects.forEach {
                     subjectList.add(it.name)
                    }
                    subjects = TextUtils.join(",",subjectList)
                    tvparentSpecialized.text = subjects
                    tvTime.text = "$"+liveData.data.body.hourlyPrice.toString()+"/Hr"
                    tv_about.text = "About " + liveData.data.body.name
                    tv_teacher_AboutUser.text = liveData.data.body.about
                    tv_teacher_TeachingHistory.text = liveData.data.body.teachingHistory
                  Glide.with(this).load(liveData.data.body.image)
                        .placeholder(R.drawable.profile_unselected).into(ivtecher_image)
                }

            }
            Status.ERROR -> {
                if (liveData.error is RequestDetailResponse) {
                    Helper.showSuccessToast(this, liveData.error.message)
                } else if (liveData.error is Commontoall) {
                    Helper.showSuccessToast(this, liveData.error.message)
                }
            }
            else -> {
                if (liveData.error is TeacherDetailResponse)
                    Helper.showSuccessToast(this, liveData.error.message)
            }
        }
    }
    fun Techerdetailapi() {
        baseViewModel.teachersDetails(this, intent.getStringExtra("teacher_id").toString(), true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
    private fun apiTecherdetail() {
        baseViewModel.sessionDetails(this, true, intent.getStringExtra("session_id").toString())
        baseViewModel.getCommonResponse().observe(this, this)
    }
}