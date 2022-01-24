package com.elewamathtutoring.Activity.ParentOrStudent

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.Chat.Chat_Activity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.constant.Constants.Companion.applyDim
import com.elewamathtutoring.Util.constant.Constants.Companion.clearDim
import kotlinx.android.synthetic.main.activity_upcoming_details.*
import kotlinx.android.synthetic.main.activity_upcoming_details.ivBack
import kotlinx.android.synthetic.main.activity_upcoming_details.iv_techerprofile
import kotlinx.android.synthetic.main.activity_upcoming_details.options
import kotlinx.android.synthetic.main.activity_upcoming_details.rlMessageButton
import kotlinx.android.synthetic.main.activity_upcoming_details.tvDateparent
import kotlinx.android.synthetic.main.activity_upcoming_details.tvSpecialized
import kotlinx.android.synthetic.main.activity_upcoming_details.tv_name
import kotlinx.android.synthetic.main.activity_upcoming_details.tv_requested_startendtimeparent
import kotlinx.android.synthetic.main.activity_upcoming_details.tv_tutor
import kotlinx.android.synthetic.main.dialog_report.*
import kotlinx.android.synthetic.main.report_popup.view.*
import java.text.SimpleDateFormat

class UpcomingDetailsActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var viewGroup: ViewGroup
    var myPopupWindow: PopupWindow? = null
    var Schedule = java.util.ArrayList<com.elewamathtutoring.Models.My_Schedule.Body>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_details)
        Schedule = ((intent.getSerializableExtra("listdata") as ArrayList<com.elewamathtutoring.Models.My_Schedule.Body>?)!!)

        var pozitiondata=Schedule.get(0).Upcoming_sessions.get(intent.getStringExtra("positionz").toString().toInt())

        tv_CancelationPolic.text=pozitiondata.Teacher.cancellationPolicy
        tv_about.text=pozitiondata.Teacher.about
        tv_history.text=pozitiondata.Teacher.teachingHistory
        tv_abouthadder.text="About "+pozitiondata.Teacher.name
        tv_name.text=pozitiondata.Teacher.name
        tvSpecialized.text=pozitiondata.Teacher.specialties
        Glide.with(this).load(Constants.IMAGE_URL+pozitiondata.Teacher.image).placeholder(R.drawable.profile_unselected).into(iv_techerprofile)
        tv_tutor.setText(Constants.isCertifiedOrtutor(pozitiondata.Teacher.isCertifiedOrtutor))
        tv_requested_startendtimeparent.text= pozitiondata.timeslot.get(0).startTime+" - "+pozitiondata.timeslot.get(0).endTime

        val dateParser = SimpleDateFormat("yyyy-MM-dd")
        val date = dateParser.parse(pozitiondata.date)
        val dateFormatter = SimpleDateFormat("EEE, MMM dd yyyy")
        tvDateparent.text=dateFormatter.format(date).toString()
        onClicks()

    }

    private fun onClicks() {
        rlMessageButton.setOnClickListener(this)
        btnViewReceipt.setOnClickListener(this)
        options.setOnClickListener(this)
        ivBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rlMessageButton -> {
                startActivity(Intent(this, Chat_Activity::class.java))
            }
            R.id.btnViewReceipt -> {
                val intentt = Intent(this, ReceiptActivity::class.java)
                intentt.putExtra("listdata", Schedule)
                intentt.putExtra("positionz", intent.getStringExtra("positionz").toString())
                startActivity(intentt)

            }
            R.id.options -> {
                setPopUpWindow()
            }
            R.id.ivBack -> {
                finish()
            }
        }
    }

    private fun setPopUpWindow()
    {
        val viewGroup: ViewGroup = window.decorView.rootView as ViewGroup
        val inflater =
            applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.report_popup, null)
        myPopupWindow = PopupWindow(view, 400, RelativeLayout.LayoutParams.WRAP_CONTENT, true)

        options.setOnClickListener {
            myPopupWindow?.showAsDropDown(it, 80, -45)
            applyDim(viewGroup, 0.5f)
        }
        view.report_cardview.setOnClickListener { dialogReport() }
        myPopupWindow?.setOnDismissListener {
            clearDim(viewGroup)
        }

    }
    private fun dialogReport()
    {
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
        reportDialog.report_Submit.setOnClickListener { reportDialog.cancel()

            myPopupWindow!!.dismiss()

        }

        reportDialog.window!!.setGravity(Gravity.CENTER)
        reportDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        reportDialog.setCancelable(true)
        reportDialog.setCanceledOnTouchOutside(true)
        reportDialog.show()


    }

}