package com.elewamathtutoring.Activity.ParentOrStudent

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.Models.Session_detail.Model_session_detail
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_receipt.*
import kotlinx.android.synthetic.main.activity_receipt.ivBack
import kotlinx.android.synthetic.main.activity_receipt.iv_techerprofile
import kotlinx.android.synthetic.main.activity_receipt.tvDateparent
import kotlinx.android.synthetic.main.activity_receipt.tvSpecialized
import kotlinx.android.synthetic.main.activity_receipt.tv_name
import kotlinx.android.synthetic.main.activity_receipt.tv_requested_startendtimeparent
import kotlinx.android.synthetic.main.activity_receipt.tv_tutor

import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class ReceiptActivity : AppCompatActivity() ,View.OnClickListener, Observer<RestObservable> {
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }

    var Schedule = java.util.ArrayList<com.elewamathtutoring.Models.Teacher_details.Body>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT;

        //Schedule = ((intent.getSerializableExtra("listdata") as ArrayList<com.elewamathtutoring.Models.Teacher_details.Body>?)!!)
       // var pozitiondata=Schedule.get(0)

        api()
    /*    tv_name.text=pozitiondata.name
        tvSpecialized.text=pozitiondata.specialties
        Glide.with(this).load(pozitiondata.image).placeholder(R.drawable.profile_unselected).into(iv_techerprofile)
        tv_tutor.setText(Constants.isCertifiedOrtutor(pozitiondata.isCertifiedOrtutor))

        for(i in 0 until pozitiondata.teaching_level.size)
        {
            var data=pozitiondata.teaching_level.get(i).level+","
            tv_parentteacherlevelp.text= tv_parentteacherlevelp.text.toString()+data
        }
        var s =tv_parentteacherlevelp.text.toString()
        tv_parentteacherlevelp.text = s.substring(0, s.length -1)

*/
        clicks()
    }

    private fun clicks() {
        ivBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.ivBack ->
            {
                finish()
            }
        }
    }
    override fun onChanged(liveData: RestObservable) {
        when (liveData.status) {
            Status.SUCCESS -> {
                if (liveData.data is Model_session_detail) {
            tv_requested_startendtimeparent.text= liveData.data.body.timeslot.get(0).startTime+" - "+liveData.data.body.timeslot.get(0).endTime
                    tv_impersonprice.text=Constants.Currency+ liveData.data.body.Teacher.InPersonRate.toString()+".00"
                    tv_price.text=Constants.Currency+ liveData.data.body.total.toString()+".00"
                    idper_hr.text="1 hour @ "+Constants.Currency+liveData.data.body.perHour+".00/hr"

                    val lastFourDigits = liveData.data.body.Card.card_number.substring(liveData.data.body.Card.card_number.length - 4)
                    tv_cardnumber.text="Mastercard XXXX-XXXX-XXXX"+lastFourDigits

            val dateParser = SimpleDateFormat("yyyy-MM-dd")
            val date = dateParser.parse(liveData.data.body.date)
            val dateFormatter = SimpleDateFormat("EEE, MMM dd yyyy")
            tvDateparent.text=dateFormatter.format(date).toString()
                }
            }
            Status.ERROR -> {
                if (liveData.error is Model_session_detail)
                {
                    Helper.showSuccessToast(this, liveData.error.message)
                }
            }
            else -> {
            }
        }
    }

    private fun api()
    {
        baseViewModel.sessionDetails(this, true, intent.getStringExtra("session_id").toString())
        baseViewModel.getCommonResponse().observe(this, this)
    }
}