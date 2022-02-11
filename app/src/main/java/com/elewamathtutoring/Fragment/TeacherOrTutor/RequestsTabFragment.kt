package com.elewamathtutoring.Fragment.TeacherOrTutor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.ParentOrStudent.settings.SettingActivity
import com.elewamathtutoring.Adapter.ParentOrStudent.SchedulePendingAdapter
import com.elewamathtutoring.Adapter.TeacherOrTutor.RequestAdapter
import com.elewamathtutoring.Models.TeacherRequestsList.Body
import com.elewamathtutoring.Models.TeacherRequestsList.Model_TeacherRequestList
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_requests_tab.*
import kotlinx.android.synthetic.main.fragment_requests_tab.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RequestsTabFragment : Fragment(), View.OnClickListener, Observer<RestObservable> {
    lateinit var v: View
    var Pastreq= ArrayList<Body>()
    var Newreq= ArrayList<Body>()
    var Inwaiting= ArrayList<Body>()
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_requests_tab, container, false)
        onClicks()
        return v
    }
    private fun api() {
        baseViewModel.PastTeacher(requireActivity(), "0,2,3,4,5,6", true)
        baseViewModel.getCommonResponse().observe(requireActivity(), this)
    }
    override fun onResume() {
        super.onResume()
        v.rootView.rv_newRequests.adapter = RequestAdapter(requireContext())
       // api()
    }
    private fun onClicks() {
        v.rootView.ivSetting.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivSetting -> {
                startActivity(Intent(context, SettingActivity::class.java))
            }
        }
    }
    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is Model_TeacherRequestList) {
                    Pastreq.clear()
                    Inwaiting.clear()
                    Newreq.clear()
                    for (i in 0 until liveData.data.body.size) {
                        if (liveData.data.body.get(i).status != 0) {
                            Pastreq.add(liveData.data.body[i])
                        } else {
                            val c = Calendar.getInstance().time
                            val df = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
                            val date1: Date
                            val date2: Date
                            val dates = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                            date1 = dates.parse(df.format(c).toString())
                            date2 = dates.parse(Constants.ConvertTimeStampToDate(liveData.data.body.get(i).createdAt.toLong(),"yyyy-MM-dd hh:mm:ss"))
                            val difference = Math.abs(date2.time - date1.time)
                            val differenceDates = difference / (24 * 60 * 60 * 1000)
                            val hours = difference / (1000 * 60 * 60)
                            val diffHours = java.lang.Long.toString(differenceDates)
                            Log.e("checkEndDate", "dayDifference--  "+df.format(c).toString()+"--"+Constants.ConvertTimeStampToDate(liveData.data.body.get(i).createdAt.toLong(),"yyyy-MM-dd hh:mm:ss")+"----"+ hours)
                            if (hours > 24) {
                                Log.e("checkmydates", "=====" + differenceDates.toString())
                                Inwaiting.add(liveData.data.body[i])
                            } else {
                                Log.e("checkmydates", "==XXNEW===" + differenceDates.toString())
                                Newreq.add(liveData.data.body[i])
                            }
                        }
                    }
                    if (Pastreq.size == 0) {
                        tvPastRequests.visibility = View.GONE
                        rv_PastRequests.visibility = View.GONE
                    } else {
                        tvPastRequests.visibility = View.VISIBLE
                        rv_PastRequests.visibility = View.VISIBLE
                    }
                    if (Inwaiting.size == 0) {
                        recy_watingonans.visibility = View.GONE
                        tv_wating.visibility = View.GONE
                    } else {
                        recy_watingonans.visibility = View.VISIBLE
                        tv_wating.visibility = View.VISIBLE
                    }
                    if (Newreq.size == 0) {
                        tvNewRequests.visibility = View.GONE
                        rv_newRequests.visibility = View.GONE
                    } else {
                        tvNewRequests.visibility = View.VISIBLE
                        rv_newRequests.visibility = View.VISIBLE
                    }
                    if (Newreq.size == 0 && Inwaiting.size == 0 && Pastreq.size == 0) {
                        tv_whennodata.visibility = View.VISIBLE
                    } else {
                        tv_whennodata.visibility = View.GONE
                    }
                   // v.rootView.rv_newRequests.adapter = SchedulePendingAdapter(requireContext(), Newreq)
                    /*v.rootView.rv_PastRequests.adapter = SchedulePendingAdapter(
                        requireContext(),
                        Pastreq
                    )
                    v.rootView.recy_watingonans.adapter = SchedulePendingAdapter(
                        requireContext(),
                        Inwaiting
                    )*/
                }
            }
            Status.ERROR -> {
                if (liveData.error is Model_TeacherRequestList) {
                    Helper.showSuccessToast(requireContext(), liveData.error.message)
                }
            }
            else -> {
            }
        }
    }
}