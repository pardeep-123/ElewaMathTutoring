package com.elewamathtutoring.Activity.ParentOrStudent.payment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.ParentOrStudent.add_card.AddCardActivity
import com.elewamathtutoring.Activity.ParentOrStudent.teacherDetail.TeacherDetailResponse
import com.elewamathtutoring.Adapter.ParentOrStudent.CardsListAdapter
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.pawskeeper.Modecommon.Commontoall
import com.pawskeeper.Modecommon.Commontoall2
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.activity_payment_info.*
import kotlinx.android.synthetic.main.activity_payment_info.ivBack
import kotlinx.android.synthetic.main.dialog_booking_thanks.*
import kotlinx.android.synthetic.main.dialog_delete.*
import java.io.StringWriter

class PaymentInfoActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {

    var profile = java.util.ArrayList<TeacherDetailResponse.Body>()
    var selectedprice = 0
    var finalvalue = ""
    var perHour = ""
    var value = 0
    var cardlist = ArrayList<CardListingResponse.Body>()
    var typescreen = ""
    var teachinglevel = ArrayList<String>()
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_info)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT;
        App.getinstance().getmydicomponent().inject(this)
        try {
            profile =
                ((intent.getSerializableExtra("teacher_detail") as java.util.ArrayList<TeacherDetailResponse.Body>?)!!)
            if (intent.getStringExtra("selected_Session").equals("1")) {
                selectedprice = profile.get(0).InPersonRate
            } else {
                selectedprice = profile.get(0).virtualRate
            }
            typescreen = "frombooking"
        } catch (e: Exception) {
            typescreen = "fromsetting"
        }
        onClicks()
    }

    override fun onResume() {
        super.onResume()
        api()
    }

    private fun api() {
        baseViewModel.card_listing(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    private fun onClicks() {
        rl_addCard.setOnClickListener(this)
        ivBack.setOnClickListener(this)
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is CardListingResponse) {
                    cardlist.clear()
                    cardlist.addAll(liveData.data.body)
                    rv_cards.adapter = CardsListAdapter(
                        this,
                        cardlist,
                        this@PaymentInfoActivity,
                        typescreen,
                        teachinglevel
                    )

                    if (cardlist.size == 0) {
                        whennodata.visibility = View.VISIBLE
                    } else {
                        whennodata.visibility = View.GONE
                    }
                } else if (liveData.data is Commontoall) {
                    api()
                } else if (liveData.data is Commontoall2) {
                    val filterDialog = Dialog(this)
                    filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    filterDialog.setContentView(R.layout.dialog_booking_thanks)
                    filterDialog.window!!.setLayout(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT
                    )
                    filterDialog.window!!.setGravity(Gravity.CENTER)
                    filterDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    filterDialog.setCancelable(true)
                    filterDialog.setCanceledOnTouchOutside(true)
                    var tvPromoCode = filterDialog.findViewById(R.id.tvPromoCode) as TextView
                    var edPromoCode = filterDialog.findViewById(R.id.edPromoCode) as TextView
                    tvPromoCode.text = "Thanks for booking\n" + profile.get(0).name + "!"
                    edPromoCode.text =
                        "We've let " + profile.get(0).name + " know that you are interested.Once they have confirmed your session, your card on file will be debited for $" + selectedprice + ".00."
                    filterDialog.btnThanksContinue.setOnClickListener {
                        filterDialog.dismiss()
                        this.startActivity(Intent(this, MainActivity::class.java))
                    }
                    filterDialog.show()
                }
            }
            Status.ERROR -> {
                if (liveData.error is CardListingResponse) {
                    Helper.showSuccessToast(this, liveData.error.toString())
                } else if (liveData.error is Commontoall) {
                    Helper.showSuccessToast(this, liveData.error.toString())
                }
            }
            else -> {
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rl_addCard -> {
                val intent = Intent(this, AddCardActivity::class.java)
                intent.putExtra("type", "add")
                intent.putExtra("from", "paymentinfo")
                startActivity(intent)
            }
            R.id.ivBack -> {
                finish()
            }
        }
    }

    fun deleteDialog(cardId: String) {
        val deleteDialog = Dialog(this)
        deleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        deleteDialog.setContentView(R.layout.dialog_delete)

        deleteDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        deleteDialog.window!!.setGravity(Gravity.CENTER)
        deleteDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        deleteDialog.setCancelable(true)
        deleteDialog.setCanceledOnTouchOutside(true)

        deleteDialog.btnDeleteNo.setOnClickListener {
            deleteDialog.dismiss()
        }
        deleteDialog.btnDeleteYes.setOnClickListener {
            baseViewModel.deletecard(this, cardId, true)
            baseViewModel.getCommonResponse().observe(this, this)

            deleteDialog.dismiss()
        }
        deleteDialog.show()
    }

    fun ThanksForBookingDialog(cardid: String) {
        booking(cardid)
    }

    fun booking(cardid: String) {
        var selected = ""
        finalvalue = ""
        var Hours = ""
        perHour = ""
        value = 0
        var count = 0
        var Stringselectedprice = ""
        val selecteddateconcat: StringWriter = StringWriter()
        val price: StringWriter = StringWriter()
        var selecteddate = profile.get(0).time_slots.get(0).endTime


        for (i in 0 until profile.get(0).time_slots.size) {
            if (profile.get(0).time_slots.get(i).check == true) {
                if (selecteddate.equals(profile.get(0).time_slots.get(i).startTime)) {
                    selected = ("-" + profile.get(0).time_slots.get(i).id.toString())
                    Stringselectedprice = ("-" + selectedprice)
                } else {
                    selected = ("," + profile.get(0).time_slots.get(i).id.toString())
                    Stringselectedprice = ("," + selectedprice)
                }
                selecteddateconcat.append(selected);
                price.append(Stringselectedprice);
                selecteddate = profile.get(0).time_slots.get(i).endTime
            }
        }

        val s = selecteddateconcat.toString()
        val selecteddat = s.substring(1, s.length)

        val sprice = price.toString()
        val selectedpric = sprice.substring(1, sprice.length).toString()

        try {
            val words2: java.util.ArrayList<String> =
                selectedpric.split(",") as java.util.ArrayList<String>
//--20,10,20=hour=10,10,10=time=1-2,5,7-8cardid70
            for (i in 0 until words2.size) {
                perHour += selectedprice.toString() + ","
                finalvalue = finalvalue + ","
                Hours = Hours + ","
                value = 0
                count = 0
                try {
                    val aftersplit: java.util.ArrayList<String> =
                        words2.get(i).split("-") as java.util.ArrayList<String>
                    for (ii in 0 until aftersplit.size) {
                        value = value + aftersplit.get(ii).toInt()
                        count++
                    }
                    finalvalue = finalvalue + value.toString()
                    Hours = Hours + count.toString()
                } catch (e: Exception) {
                    finalvalue = finalvalue + words2.get(i)
                    Hours = Hours + "1"
                }

            }

        } catch (e: Exception) {
            finalvalue = finalvalue + ","
            Hours = Hours + ","
            // when no data like "," in Word2
            perHour += selectedprice.toString()
            try {
                // 5-6-7
                val aftersplit: java.util.ArrayList<String> =
                    selectedpric.split("-") as java.util.ArrayList<String>
                for (ii in 0 until aftersplit.size) {
                    value = value + aftersplit.get(ii).toInt()
                    count++
                }
                finalvalue = finalvalue + value.toString()
                Hours = Hours + count
            } catch (e: Exception) {
                // when one one data like"-"
                finalvalue = finalvalue + selectedprice
                Hours = Hours + "1"
            }
        }
        var selectedHours = ""
        if (Hours.contains(",")) {
            selectedHours = Hours.substring(1, Hours.length).toString()
        } else {
            selectedHours = Hours
        }

        var selectedperHour = ""
        if (selectedperHour.contains(",")) {
            selectedperHour = perHour.substring(0, perHour.length - 1).toString()
        } else {
            selectedperHour = perHour
        }
        val selectedfinalvalue = finalvalue.substring(1, finalvalue.length).toString()
        //  val selectedperHour = perHour.substring(0, perHour.length-1).toString()

        Log.e(
            "checkmylog",
            "--" + selectedfinalvalue + "=perhour=" + selectedperHour + "=time=" + selecteddat + "cardid" + cardid + "---hr-" + selectedHours
        )
        baseViewModel.book_Session(
            this,
            profile.get(0).id.toString(),
            "" + profile.get(0).time_slots.size.toString(),
            selecteddat,
            intent.getStringExtra("aboutdetail").toString(),
            intent.getStringExtra("selected_Session").toString(),
            selectedHours,
            selectedperHour,
            selectedfinalvalue,
            cardid,
            intent.getStringExtra("selecteddate").toString(),
            true
        )
        baseViewModel.getCommonResponse().observe(this, this)


    }

}