package com.elewamathtutoring.Fragment.TeacherOrTutor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.elewamathtutoring.Activity.NotificationsActivity
import com.elewamathtutoring.Activity.SettingActivity
import com.elewamathtutoring.Adapter.TeacherOrTutor.Hadder_sessionsadapter
import com.elewamathtutoring.Adapter.TeacherOrTutor.SessionsAdapter
import com.elewamathtutoring.Models.ListView.Body
import com.elewamathtutoring.Models.ListView.Model_myschdeullist
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_schedule_tab.*
import kotlinx.android.synthetic.main.fragment_schedule_tab.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ScheduleTabFragment : Fragment(), OnSelectDateListener, Observer<RestObservable> {
    lateinit var v: View
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    var selectedTab = "ListView"
    var today = ArrayList<Body>()
    var title = ArrayList<String>()
    var upcomming = ArrayList<Body>()
    var apitype="withoutdate"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_schedule_tab, container, false)
        onClicks()
        calenderView()
        return v
    }


    private fun calenderView() {
        v.rootView.calenderView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val clickedDayCalendar = eventDay.calendar

                val dateParser = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
                val date = dateParser.parse(clickedDayCalendar.time.toString())
                val dateFormatter = SimpleDateFormat("YYYY-MM-dd")
                val sdfDestinationMM = SimpleDateFormat("MMMM")
                val sdfDestinationMMonth = SimpleDateFormat("MM")
                val sdfDestinationdd = SimpleDateFormat("dd")
                val sdfDestinationyyyy = SimpleDateFormat("yyyy")
                val Monthdate = sdfDestinationMM.format(date)
                v.rootView.calenderDate.text = Monthdate.toString() + " " + sdfDestinationdd.format(date)
                        .toString() + ", " + sdfDestinationyyyy.format(date).toString()

               calenderClickApi(sdfDestinationyyyy.format(date).toString() + "-" + sdfDestinationMMonth.format(date).toString() + "-" + sdfDestinationdd.format(date).toString())

            }
        })

        DatePickerBuilder(requireContext(), this)
            .pagesColor(R.color.textcolor)
    }

    private fun calenderClickApi(date: String)
    {
        if(date.equals(""))
        {
             apitype="withoutdate"
        }
        else
        {
             apitype="withdate"
        }
        baseViewModel.listViewSession(requireActivity(), true, date)
        baseViewModel.getCommonResponse().observe(requireActivity(), this)
    }


    private fun onClicks() {
      /*  v.rootView.ivNotification.setOnClickListener {
            startActivity(Intent(context, NotificationsActivity::class.java))
        }*/
        v.rootView.ivSetting.setOnClickListener {
            startActivity(Intent(context, SettingActivity::class.java))
        }
        v.rootView.schedule_listView.setOnClickListener {

            calenderView_rl.visibility = View.GONE
            listView_rl.visibility = View.VISIBLE
            tvListView.setTextColor(ContextCompat.getColor(requireContext(), R.color.textcolor))
            tvCalenderView.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))

            barListView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.textcolor
                )
            )
            barCalenderView.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.text
                )
            )

            selectedTab = "ListView"
        }
        v.rootView.schedule_calenderView.setOnClickListener {

            calenderView_rl.visibility = View.VISIBLE
            listView_rl.visibility = View.GONE

            tvListView.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
            tvCalenderView.setTextColor(ContextCompat.getColor(requireContext(), R.color.textcolor))

            barListView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.text))
            barCalenderView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.textcolor))
            selectedTab = "CalenderView"
            val c = Calendar.getInstance().time
            val df = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
            calenderClickApi(df.format(c).toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        calenderClickApi("")
    }

    override fun onSelect(calendar: List<Calendar>) {

    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is Model_myschdeullist) {
                    if(apitype.equals("withdate"))
                    {
                        if(liveData.data.body.size!=0)
                        {
                            tv_whennodata.visibility=View.GONE
                            setCalenderAdapter(liveData.data.body as ArrayList<Body>)
                        }
                        else
                        {
                            tv_whennodata.visibility=View.VISIBLE
                        }
                    }
                    else
                    {

                    upcomming.clear()
                    today.clear()
                    title.clear()
                    val c = Calendar.getInstance().time
                    val df = SimpleDateFormat("EEE, MMM yyyy", Locale.getDefault())
                    val formattedDate = df.format(c)

                    for (i in 0 until liveData.data.body.size) {
                        // date
                        if(Constants.ConvertTimeStampToDate(liveData.data.body.get(i).createdAt.toLong(), "EEE, MMM yyyy").toString().equals(formattedDate))
                        {
                            Log.e("checkbody","----data")
                            today.add(liveData.data.body.get(i))
                        }
                        else
                        {
                            Log.e("checkbody","----upcomming")
                            upcomming.add(liveData.data.body.get(i))
                        }
                    }

                    if(today.size!=0)
                    {
                        title.add("TODAY'S SESSIONS")
                    }
                    if(today.size==0&&upcomming.size==0)
                    {
                        tv_whennodata.visibility=View.VISIBLE
                    }
                    else
                    {
                        tv_whennodata.visibility=View.GONE
                    }

                    if(upcomming.size!=0)
                    {
                        title.add("UPCOMING SESSIONS")
                    }
                        Log.e("checkbody","----upcomming"+upcomming.size+"---"+today.size)
                        v.rootView.rv_listView.adapter = Hadder_sessionsadapter(requireContext(), today,upcomming,title)
                    }
                }
            }
            Status.ERROR -> {
                if (liveData.error is Model_myschdeullist)
                    Helper.showSuccessToast(requireContext(), liveData.error.message)
            }
            else -> {
            }
        }
    }

    private fun setCalenderAdapter(listSession: ArrayList<Body>) {
        v.rootView.rv_calenderViewList.adapter = SessionsAdapter(requireContext(), listSession)
    }


}