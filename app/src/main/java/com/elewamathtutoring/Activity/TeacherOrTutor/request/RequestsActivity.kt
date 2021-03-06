package com.elewamathtutoring.Activity.TeacherOrTutor.request

import android.annotation.SuppressLint
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
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.Chat.Chat_Activity
import com.elewamathtutoring.Models.Modecommon.Commontoall
import com.elewamathtutoring.Models.Session_detail.Model_session_detail
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.constant.Constants.Companion.ConvertTimeStampToDate
import com.elewamathtutoring.Util.constant.Constants.Companion.Orderstatus
import com.elewamathtutoring.Util.constant.Constants.Companion.applyDim
import com.elewamathtutoring.Util.constant.Constants.Companion.clearDim
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel

import kotlinx.android.synthetic.main.activity_requests.*
import kotlinx.android.synthetic.main.activity_schedule_a_session2.*
import kotlinx.android.synthetic.main.dialog_report.*
import kotlinx.android.synthetic.main.item_calender.view.*
import kotlinx.android.synthetic.main.report_popup.view.*
import java.text.SimpleDateFormat

class RequestsActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    lateinit var viewGroup: ViewGroup
    var myPopupWindow: PopupWindow? = null
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    var receiverId=""
    var chatUserName=""
    var id=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requests)
        clicks()

        if (intent.getStringExtra("from").equals("Sessions")) {
            btnAcceptOffer.setText("Cancel Booking")
           // btnDenyOffer.setText("Cancel Session")
        }
        else {
            btnAcceptOffer.setText("Accept Offers")
           // btnDenyOffer.setText("Deny Offers")
            tv_inq.visibility=View.VISIBLE
            tv_inquiry.visibility=View.VISIBLE
        }
        api()
    }
    private fun api() {
//        /intent.getStringExtra("id").toString()
       // this.id=id
        baseViewModel.sessionDetails(this, true, intent.getStringExtra("id").toString())
        baseViewModel.getCommonResponse().observe(this, this)
    }


    private fun clicks() {
        ivPopupOptions.setOnClickListener(this)
        btnAcceptOffer.setOnClickListener(this)
     //   btnDenyOffer.setOnClickListener(this)
        ivRequestsBack.setOnClickListener(this)
        btnReject.setOnClickListener(this)
        rlMessageButton.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivPopupOptions -> {
                setPopUpWindow()
            }
            R.id.rlMessageButton -> {
                val i = Intent(this, Chat_Activity::class.java)
                i.putExtra("receiverId", receiverId)
                i.putExtra("chatUserName", chatUserName)
                startActivity(i)
            }
            R.id.btnAcceptOffer -> {
              //old E-Teacher  //     0=pending,1=accepted,2=completed,3 =cancel , 4 = cancelbyuser

             //   0=pending,1=accepted,2=complete,3 =cancelbyteacher , 5 = deny session , 6= UserCompleted
                if(btnAcceptOffer.text.toString().equals("Complete Offer"))
                {
                    apiAccept("2")
                }
                else
                {
                    apiAccept("1")
                }
            }
          /*  R.id.btnDenyOffer -> {
                Logout_Alert()

            }*/
                 R.id.btnReject -> {
                Logout_Alert()

            }


            R.id.ivRequestsBack -> {
                finish()
            }
            R.id.rlMessageButton -> {
                finish()
            }
        }
    }
    fun apiAccept(status:String){
        baseViewModel.requestAccept(this,  status,intent.getStringExtra("id").toString(),true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    private fun Logout_Alert() {

        val builder = let { AlertDialog.Builder(it) }
        if (builder != null) {
            builder.setTitle("Cancel Request")
        }
        if (builder != null) {
            builder.setMessage("Are you sure you want to cancel?")
                .setCancelable(false)
                .setPositiveButton("Yes, Cancel") { dialog, id ->
                    dialog.cancel()
                    if(btnAcceptOffer.text.toString().equals("Cancel Session"))
                    {
                        apiAccept("3")
                    }
                    else
                    {
                        apiAccept("4")
                    }
                }
                .setNegativeButton(
                    "Nevermind"
                ) { dialog, id -> dialog.cancel() }
        }
        val alert = builder?.create()
        if (alert != null) {
            alert.show()
        }
    }

    private fun setPopUpWindow() {
        val viewGroup: ViewGroup = window.decorView.rootView as ViewGroup
        val inflater =
            applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.report_popup, null)
        myPopupWindow = PopupWindow(view, 400, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        ivPopupOptions.setOnClickListener {
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
            baseViewModel.report(this, reportDialog.etText.text.toString(), intent.getStringExtra("id").toString(),true)
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
    override fun onChanged(liveData: RestObservable) {
        when (liveData.status) {
            Status.SUCCESS -> {
                if (liveData.data is RequestDetailResponse) {
                    tvRequestsName.text=liveData.data.body.Student.name
                    tvAmountHours.text="Amount ("+liveData.data.body.hours+" hours )"
                    tvAmount.text=Constants.Currency+liveData.data.body.perHour.toString()
                    tvCommision.text=Constants.Currency+liveData.data.body.adminCommision
                  tvAmtReceived.text = Constants.Currency+liveData.data.body.total.toString()

                    tv_inquiry.text="About "+liveData.data.body.Student.name
                    tv_inq.text="About "+liveData.data.body.Student.about
                    tv_requestemail.text=liveData.data.body.Student.email


                  /*  val total=liveData.data.body.perHour*liveData.data.body.adminCommision
                    tvAmtReceived.text = Constants.Currency+total*/
                  //  tv_aboutparent.text=liveData.data.body.Student.about
                    receiverId= liveData.data.body.userId.toString()
                    chatUserName= liveData.data.body.Student.name.toString()
                    chatUserName= liveData.data.body.Student.name.toString()

                    if(liveData.data.body.status==2||liveData.data.body.status==3||liveData.data.body.status==4||liveData.data.body.status==6)
                    {
                     //   btnLL.visibility=View.GONE
                    }
                    if(liveData.data.body.status==0){
                        btnAcceptOffer.visibility=View.VISIBLE
                        btnReject.visibility=View.VISIBLE
                    } else if(liveData.data.body.status==1){
                        btnAcceptOffer.visibility=View.GONE

                        btnReject.visibility=View.VISIBLE
                    }else if(liveData.data.body.status==3){
                        btnAcceptOffer.visibility=View.VISIBLE
                        btnAcceptOffer.setText("Cancel Booking")
                        btnReject.visibility=View.GONE
                    }

               /*     val dateParser = SimpleDateFormat("yyyy-MM-dd")
                    val date = dateParser.parse(liveData.data.body.date)
                    val dateFormatter = SimpleDateFormat("EEE, MMM dd")
                    tvDate.text=dateFormatter.format(date).toString()*/
                    tvDate.text = Constants.convertDateToRatingTime(liveData.data.body.date.toLong())
                    tv_requesteddate.text=Orderstatus(liveData.data.body.status)+" "+ConvertTimeStampToDate(liveData.data.body.updated.toLong(),"MM/dd/YY")
                    tv_inq.text=liveData.data.body.about
                    Glide.with(this).load(liveData.data.body.Student.image).placeholder(R.drawable.profile_unselected).into(ivProfileSignUp)
                    tv_requested_startendtime.text=liveData.data.body.timeslot.get(0).startTime+" - "+ liveData.data.body.timeslot.get(0).endTime
                }
              else if (liveData.data is Commontoall) {
                    Helper.showSuccessToast(this, liveData.data.message)
                  finish()
                }
            }
            Status.ERROR -> {
                if (liveData.error is RequestDetailResponse)
                {
                    Helper.showSuccessToast(this, liveData.error.message)
                }
                else if (liveData.error is Commontoall)
                {
                    Helper.showSuccessToast(this, liveData.error.message)
                }
            }
            else -> {
            }
        }
    }
   fun change_oderstatus(status:String) {
       //sessionStatus,sessionId
       baseViewModel.change_session_status(this, status, intent.getStringExtra("id").toString(),true)
       baseViewModel.getCommonResponse().observe(this, this)
   }
}