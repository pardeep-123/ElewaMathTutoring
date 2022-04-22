package com.elewamathtutoring.Activity.ParentOrStudent.session

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.elewamathtutoring.Activity.ParentOrStudent.teacherDetail.TeacherDetailResponse
import com.elewamathtutoring.Activity.TeacherOrTutor.request.RequestDetailResponse
import com.elewamathtutoring.Adapter.ChooseTimeAdapter
import com.elewamathtutoring.Adapter.FreeAdapter
import com.elewamathtutoring.R

import com.elewamathtutoring.Util.helper.Helper
import kotlinx.android.synthetic.main.activity_schedule_a_session.*
import kotlinx.android.synthetic.main.activity_schedule_a_session.ivBack
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ScheduleASessionActivity : AppCompatActivity(), View.OnClickListener,
    ChooseTimeAdapter.TimeSlot {
    private var isClick = false
    private var sunday: Int = 0
    private var monday: Int = 0
    private var tuesday: Int = 0
    private var wednesday: Int = 0
    private var thursday: Int = 0
    private var friday: Int = 0
    private var saturday: Int = 0
    var profile = ArrayList<TeacherDetailResponse.Body>()
    var selectedtme = "false"
    var selectedDate = ""
    var currentdateDate = ""
    var Currentdate_isdisable_date = false
    val calendarsarray: ArrayList<Calendar> = ArrayList()
    var finalDateAndTimeConvertToTimeStamp = 0L
    val timeList : ArrayList<String> = ArrayList()
    var timeString = ""
    var hour = ""
    lateinit var freeAdapter:FreeAdapter
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_a_session)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT;
        profile = ((intent.getSerializableExtra("teacher_detail") as ArrayList<TeacherDetailResponse.Body>?)!!)
        setChooseTimeAdapter()
        onClicks()
        val disable: MutableList<Date> = ArrayList()
        val calendar = Calendar.getInstance()
        val min = Calendar.getInstance()
        min.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1)
        mCalendarView.setMinimumDate(min)

        if(profile.isNotEmpty()){
            var words2: ArrayList<String> = ArrayList<String>()
            if (profile[0].availability.contains(","))
           words2  = profile[0].availability.split(",") as ArrayList<String>
          else
             words2.add(profile[0].availability)
            monday = if (words2.contains("1")) 0 else Calendar.MONDAY
            tuesday = if (words2.contains("2")) 0 else Calendar.TUESDAY
            wednesday = if (words2.contains("3")) 0 else Calendar.WEDNESDAY
            thursday = if (words2.contains("4")) 0 else Calendar.THURSDAY
            friday = if (words2.contains("5")) 0 else Calendar.FRIDAY
            saturday = if (words2.contains("6")) 0 else Calendar.SATURDAY
            sunday = if (words2.contains("7")) 0 else Calendar.SUNDAY
            val cal = Calendar.getInstance()
            cal[Calendar.DAY_OF_MONTH] = 12
            val year = cal[Calendar.YEAR]
            do {
                val dayOfWeek = cal[Calendar.DAY_OF_WEEK]
                if (dayOfWeek == sunday || dayOfWeek == monday ||
                    dayOfWeek == tuesday || dayOfWeek == wednesday || dayOfWeek == thursday || dayOfWeek == friday
                    || dayOfWeek == saturday
                ) {
                    disable.add(cal.time)
                }
                cal.add(Calendar.DAY_OF_MONTH, 1)
            }
            while (cal[Calendar.YEAR] === year)
            val fmt = SimpleDateFormat("dd-MM-yyyy")
            for (date in disable) {
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                val date = sdf.parse(fmt.format(date))
                val cal = Calendar.getInstance()
                cal.time = date
                val calendar = Calendar.getInstance()
                val datee = calendar.time
                val format = SimpleDateFormat("dd-MM-yyyy")
                Log.e("checkmy", "---" + fmt.format(date) + "ccc-----" + format.format(datee))
                if (fmt.format(date).equals(format.format(datee).toString())) {
                    currentdateDate = format.format(datee).toString()
                    Currentdate_isdisable_date = true
                    Log.e("checkmy", "efdfhjbvhfbvhufbvhf")
                } else {
                    calendarsarray.add(cal)
                }
        }

            // check if free slot is available or not
            if (profile[0].free_slots==null) {
                tvSlot.visibility = View.GONE
                cardFreeSlot.visibility = View.GONE
            }else{
                tvSlot.visibility = View.VISIBLE
                cardFreeSlot.visibility = View.VISIBLE
                dayofweekSlot.text = profile[0].free_slots?.startTime+ "-" + profile[0].free_slots?.endTime
            }

        }
        setCalenderView()
        mCalendarView.setOnDayClickListener(object : OnDayClickListener {
            @SuppressLint("SimpleDateFormat")
            override fun onDayClick(eventDay: EventDay) {
                //Tue Aug 31 00:00:00 GMT+05:30 2021
                val dateParserr = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
                val Mydate = dateParserr.parse(eventDay.calendar.getTime().toString())
                val dateFormatterz = SimpleDateFormat("dd-MM-yyyy")
                val localTime = eventDay.calendar.time.time.div(1000)
                val gmtTime = localTime+19800
                selectedDate = gmtTime.toString()
//                selectedDate = dateFormatterz.format(Mydate)

                //finalDateAndTimeConvertToTimeStamp = CommonMethods.time_to_timestamp(selectedDate, "yyyy-MM-dd" +19080)
            }
        })

        // set click listner on free slots cards
        cardFreeSlot.setOnClickListener {
            isClick = !isClick
            if (isClick){
                dayofweekSlot.setTextColor(ContextCompat.getColor(this,R.color.white))
                rlFreeSlot.background = ContextCompat.getDrawable(this,R.drawable.background_blue)
            }else{
                dayofweekSlot.setTextColor(ContextCompat.getColor(this,R.color.black))
                rlFreeSlot.background = null

            }
        }
    }

    private fun onClicks() {
        btnConfirmSession.setOnClickListener(this)
        ivBack.setOnClickListener(this)
    }

    private fun setChooseTimeAdapter() {
        if(profile.isNotEmpty()){
            rv_chooseTime.adapter = ChooseTimeAdapter(this, profile[0].time_slots,this)

        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnConfirmSession -> {
                if (selectedDate.equals("")) {
                    Helper.showErrorAlert(this, "Please select date.")
                } else if (Currentdate_isdisable_date == true && currentdateDate.equals(selectedDate)) {
                    Helper.showErrorAlert(this, "Please select date.")
                } else if (timeString=="") {
                    Helper.showErrorAlert(this, "Please select atlest one time frame.")
                } else {
                    if (profile.isNotEmpty()) {
                        val intent = Intent(this, ScheduleASession2Activity::class.java)
                        intent.putExtra("teacher_detail", profile)
                        intent.putExtra("selecteddate", selectedDate)
                        intent.putExtra("time", timeString)
                        intent.putExtra("hour", hour)
                        startActivity(intent)
                    }
                }
            }
            R.id.ivBack -> {
                finish()
            }
        }
    }

    fun setCalenderView() {
        mCalendarView.setDisabledDays(calendarsarray)
    }

    override fun ondate(timeId: String) {
        if (timeList.contains(timeId)){
            timeList.remove(timeId)
        }else{
            timeList.add(timeId)
        }
        timeString = TextUtils.join(",",timeList)
        hour = timeList.size.toString()
    }
}