package com.elewamathtutoring.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.ParentOrStudent.settings.SettingActivity
import com.elewamathtutoring.Adapter.ParentOrTeacher.UpcomingSessionsAdapter

import com.elewamathtutoring.Models.My_Schedule.Model_schedule
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import kotlinx.android.synthetic.main.fragment_schedule.view.ivSetting

class ScheduleFragment : Fragment(), Observer<RestObservable> {
    lateinit var v: View
    var pendinglist = ArrayList<com.elewamathtutoring.Models.My_Schedule.Body>()
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_schedule, container, false)
        onClicks()
        v.rootView.rv_today.adapter = UpcomingSessionsAdapter(requireContext())
        return v
    }
    private fun onClicks() {
       // api()
        // v.rootView.ivNotification.setOnClickListener { startActivity(Intent(context,NotificationsActivity::class.java)) }
        v.rootView.ivSetting.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    SettingActivity::class.java
                )
            )
        }
    }

    private fun api() {
        baseViewModel.parentSchedulingList(requireActivity(), true)
        baseViewModel.getCommonResponse().observe(requireActivity(), this)
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
               /* if (liveData.data is Model_schedule) {
                    pendinglist.addAll(listOf(liveData.data.body))

                    if (liveData.data.body.Pending_sessions.size != 0) {
                        v.rootView.rv_SchedulePending.adapter =
                            UpcomingSessionsAdapter(requireContext(), pendinglist, 1)
                    } else {
                        tv_pending.visibility = View.GONE
                    }

                    if (liveData.data.body.Upcoming_sessions.size != 0) {
                        v.rootView.rv_UpcomingSessions.adapter =
                            UpcomingSessionsAdapter(requireContext(), pendinglist, 2)
                    } else {
                        tvUpcomingSessions.visibility = View.GONE
                    }
                    if (liveData.data.body.Today_sessions.size != 0) {
                        v.rootView.rv_today.adapter = UpcomingSessionsAdapter(requireContext(), pendinglist, 3)
                    } else {
                        tvUpcomingtoday.visibility = View.GONE
                    }
                    if (liveData.data.body.Today_sessions.size == 0 && liveData.data.body.Upcoming_sessions.size == 0 && liveData.data.body.Pending_sessions.size == 0) {
                        tv_whennodata.visibility = View.VISIBLE
                    } else {
                        tv_whennodata.visibility = View.GONE
                    }
                }*/
            }
            Status.ERROR -> {
                if (liveData.error is Model_schedule) {
                    Helper.showSuccessToast(requireContext(), liveData.error.message)
                }
            }
            else -> {
            }
        }
    }

}