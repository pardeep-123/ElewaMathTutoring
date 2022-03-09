package com.elewamathtutoring.Activity.ParentOrStudent.session

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.elewamathtutoring.Activity.ParentOrStudent.teacherDetail.TeacherDetailResponse
import com.elewamathtutoring.Activity.ScheduleASession2Activity
import com.elewamathtutoring.Activity.TeacherOrTutor.request.RequestDetailResponse
import com.elewamathtutoring.Adapter.ChooseTimeAdapter
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.helper.Helper
import kotlinx.android.synthetic.main.activity_schedule_a_session.*
import kotlinx.android.synthetic.main.activity_schedule_a_session.ivBack
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ScheduleASessionActivity : AppCompatActivity(), View.OnClickListener,
    ChooseTimeAdapter.TimeSlot {
    private var sunday: Int = 0
    private var monday: Int = 0
    private var tuesday: Int = 0
    private var wednesday: Int = 0
    private var thursday: Int = 0
    private var friday: Int = 0
    private var saturday: Int = 0
    var profile = ArrayList<TeacherDetailResponse.Body>()
    var timeSlots = ArrayList<TeacherDetailResponse.Body.TimeSlot>()
    var teacherdetails = ArrayList<RequestDetailResponse.Body>()
    var selectedtme = "false"
    var selectedDate = ""
    var currentdateDate = ""
    var Currentdate_isdisable_date = false
    val calendarsarray: ArrayList<Calendar> = ArrayList()

    val timeList : ArrayList<String> = ArrayList()
    var timeString = ""
    var hour = ""
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
        val words2: ArrayList<String> = profile[0].available_slots.split(",") as ArrayList<String>
        sunday = if (words2.contains("1")) 0 else Calendar.SUNDAY
        monday = if (words2.contains("2")) 0 else Calendar.MONDAY
        tuesday = if (words2.contains("3")) 0 else Calendar.TUESDAY
        wednesday = if (words2.contains("4")) 0 else Calendar.WEDNESDAY
        thursday = if (words2.contains("5")) 0 else Calendar.THURSDAY
        friday = if (words2.contains("6")) 0 else Calendar.FRIDAY
        saturday = if (words2.contains("7")) 0 else Calendar.SATURDAY
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
        } while (cal[Calendar.YEAR] === year)

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
        setCalenderView()
        mCalendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                //Tue Aug 31 00:00:00 GMT+05:30 2021
                val dateParserr = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
                val Mydate = dateParserr.parse(eventDay.calendar.getTime().toString())
                val dateFormatterz = SimpleDateFormat("dd-MM-yyyy")
                selectedDate = dateFormatterz.format(Mydate)
            }
        })
    }
    private fun onClicks() {
        btnConfirmSession.setOnClickListener(this)
        ivBack.setOnClickListener(this)
    }
    private fun setChooseTimeAdapter() {
        rv_chooseTime.adapter = ChooseTimeAdapter(this, profile[0].time_slots,this)
    }
    //time": "3-4-5-6,9"
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnConfirmSession -> {
                val intent = Intent(this, ScheduleASession2Activity::class.java)
                intent.putExtra("teacher_detail", profile)
                intent.putExtra("selecteddate", selectedDate)
                startActivity(intent)
                /*   for (i in 0 until profile.get(0).time_slots.size) {
                       if (profile.get(0).time_slots.get(i).check == true) {
                           selectedtme = "true"
                       }
                   }*/
                if (selectedDate.equals("")) {
                    Helper.showErrorAlert(this, "Please select date.")
                } else if (Currentdate_isdisable_date == true && currentdateDate.equals(selectedDate)) {
                    Helper.showErrorAlert(this, "Please select date.")
                } else if (selectedtme.equals("false")) {
                    Helper.showErrorAlert(this, "Please select atlest one time frame.")
                } else {
                    val intent = Intent(this, ScheduleASession2Activity::class.java)
                    intent.putExtra("teacher_detail", profile)
                    intent.putExtra("selecteddate", selectedDate)
                    startActivity(intent)
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
      //  Toast.makeText(this,timeString,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,hour,Toast.LENGTH_SHORT).show()
    }
}