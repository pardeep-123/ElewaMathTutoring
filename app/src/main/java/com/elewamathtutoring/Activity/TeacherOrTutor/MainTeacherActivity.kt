package com.elewamathtutoring.Activity.TeacherOrTutor

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.elewamathtutoring.Activity.Chat.MessagesTabFragment
import com.elewamathtutoring.Fragment.TeacherOrTutor.TeacherProfileTabFragment
import com.elewamathtutoring.Fragment.TeacherOrTutor.request.RequestsTabFragment
import com.elewamathtutoring.Fragment.TeacherOrTutor.bookings.ScheduleTabFragment
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_main_teacher.*

class MainTeacherActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_teacher)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT;
        llScheduleTab.setOnClickListener(this)
        llRequestsTab.setOnClickListener(this)
        llMessagesTab.setOnClickListener(this)
        llProfileTab.setOnClickListener(this)
        switchFragment(R.id.container2, ScheduleTabFragment())
    }

    override fun onClick(v: View?) {
        val f = supportFragmentManager.findFragmentById(R.id.container2)
        when (v!!.id) {

            R.id.llScheduleTab -> {
                ivSchedule.setImageResource(R.drawable.booking)
                ivRequests.setImageResource(R.drawable.requests_unselected)
                ivMessages.setImageResource(R.drawable.messages_unselected)
                ivProfile.setImageResource(R.drawable.profile_unselected)

                tvScheduleTab.setTextColor(ContextCompat.getColor(this, R.color.textcolor))
                tvRequestsTab.setTextColor(ContextCompat.getColor(this, R.color.text))
                tvMessagesTab.setTextColor(ContextCompat.getColor(this, R.color.text))
                tvProfileTab.setTextColor(ContextCompat.getColor(this, R.color.text))
                if (f !is ScheduleTabFragment) {
                    switchFragment(R.id.container2, ScheduleTabFragment())
                }
            }
            R.id.llRequestsTab -> {
                ivSchedule.setImageResource(R.drawable.calender_unselected)
                ivRequests.setImageResource(R.drawable.req_app)
                ivMessages.setImageResource(R.drawable.messages_unselected)
                ivProfile.setImageResource(R.drawable.profile_unselected)
                tvScheduleTab.setTextColor(ContextCompat.getColor(this, R.color.text))
                tvRequestsTab.setTextColor(ContextCompat.getColor(this, R.color.textcolor))
                tvMessagesTab.setTextColor(ContextCompat.getColor(this, R.color.text))
                tvProfileTab.setTextColor(ContextCompat.getColor(this, R.color.text))
                if (f !is RequestsTabFragment) {
                    switchFragment(R.id.container2, RequestsTabFragment())
                }
            }
            R.id.llMessagesTab -> {
                ivSchedule.setImageResource(R.drawable.calender_unselected)
                ivRequests.setImageResource(R.drawable.requests_unselected)
                ivMessages.setImageResource(R.drawable.msg_app)
                ivProfile.setImageResource(R.drawable.profile_unselected)
                tvScheduleTab.setTextColor(ContextCompat.getColor(this, R.color.text))
                tvRequestsTab.setTextColor(ContextCompat.getColor(this, R.color.text))
                tvMessagesTab.setTextColor(ContextCompat.getColor(this, R.color.textcolor))
                tvProfileTab.setTextColor(ContextCompat.getColor(this, R.color.text))
                if (f !is MessagesTabFragment)
                {
                    switchFragment(R.id.container2, MessagesTabFragment())
                }
            }
            R.id.llProfileTab -> {
                ivSchedule.setImageResource(R.drawable.calender_unselected)
                ivRequests.setImageResource(R.drawable.requests_unselected)
                ivMessages.setImageResource(R.drawable.messages_unselected)
                ivProfile.setImageResource(R.drawable.menu)
                tvScheduleTab.setTextColor(ContextCompat.getColor(this, R.color.text))
                tvRequestsTab.setTextColor(ContextCompat.getColor(this, R.color.text))
                tvMessagesTab.setTextColor(ContextCompat.getColor(this, R.color.text))
                tvProfileTab.setTextColor(ContextCompat.getColor(this, R.color.textcolor))
                if (f !is TeacherProfileTabFragment) {
                    switchFragment(R.id.container2, TeacherProfileTabFragment())
                }
            }
        }
    }
    protected fun switchFragment(main_frame: Int, fragment: Fragment?) {
        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(main_frame, fragment!!)
        fragmentTransaction.commit()
    }
}