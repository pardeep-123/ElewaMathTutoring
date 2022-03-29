package com.elewamathtutoring.Fragment.ParentOrStudent.search

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.Chat.mathChat.MathChatActivity
import com.elewamathtutoring.Activity.Chat.mathChat.MathChatResponse
import com.elewamathtutoring.Activity.NotificationsActivity
import com.elewamathtutoring.Activity.ParentOrStudent.filter.FilterActivity
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.PostMathProblemActivity
import com.elewamathtutoring.Activity.ParentOrStudent.resources.ResoucesActivity
import com.elewamathtutoring.Activity.ParentOrStudent.settings.SettingActivity
import com.elewamathtutoring.Adapter.SearchHomeAdapter
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.CheckLocationActivity
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.riseball.interface_base.Teachinglevel_interface
import kotlinx.android.synthetic.main.activity_teaching_info.*
import kotlinx.android.synthetic.main.dialog_filter.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : CheckLocationActivity(), Observer<RestObservable>, Teachinglevel_interface {

    var teacherlevel = ArrayList<MathChatResponse.Body>()

    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    var selected_level = ArrayList<String>()
    var selected_certified = ArrayList<String>()
    var getdata_toselected_certified = ArrayList<String>()
    lateinit var ivSetting: ImageView
    lateinit var seekbar: SeekBar
    lateinit var tv_selectlocation: TextView
    lateinit var rv_filterOptions1: RecyclerView
    private var searchHomeAdapter: SearchHomeAdapter?=null
    lateinit var v: View
    var latitude = ""
    var longitude = ""
    var address = ""
    var viewType = ""
    var visitCount = ""
    var subjectId = ""
    var subjectName = ""
    private val requestCodes = 11

    override fun onAttach(context: Context) {
        super.onAttach(context)
        visitCount = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_search, container, false)

        return v
    }

    private val filterResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == 101) {
            Log.e("subjectssssssssss", result.data.toString())
            subjectName = result.data?.getStringExtra("name")!!
            subjectId = result.data?.getStringExtra("id")!!

            baseViewModel.getTeacherStudentList(requireActivity(), "2", subjectId, false)
            baseViewModel.getCommonResponse().observe(requireActivity(), this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getdata_toselected_certified.add("1")
        getdata_toselected_certified.add("2")
        getdata_toselected_certified.add("3")


        ivNotification.setOnClickListener {
            startActivity(Intent(context, NotificationsActivity::class.java))
        }
        rlMathChatRoom.setOnClickListener {
            startActivity(
                Intent(context, MathChatActivity::class.java)
                    .putExtra("user", "mathChatRoom")
            )
        }
        rlResources.setOnClickListener {
            startActivity(Intent(context, ResoucesActivity::class.java))
        }
        rlFilter.setOnClickListener {
            /*     val intent = Intent(context, FilterActivity::class.java)
                 intent.putExtra("editId",subjectId)
                 intent.putExtra("editName",subjectName)
                 filterResult.launch(intent)
     */
            val intent = Intent(context, FilterActivity::class.java)
            intent.putExtra("editId", subjectId)
            intent.putExtra("editName", subjectName)
            filterResult.launch(intent)
            //   startActivityForResult(intent, 101)
        }

        rlMathProblem.setOnClickListener {
            val intent = Intent(requireContext(), PostMathProblemActivity::class.java)
            startActivity(intent)
        }



        ivSetting = view.findViewById(R.id.ivSetting)
        ivSetting.setOnClickListener {
            val intent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(intent)
        }

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                filterServices(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        apiTeacherList("2")
        viewType = "1"
    }

    override fun onPermissionGranted() {
        Log.e("checkmylog", "ooo")
    }

    override fun onLocationGet(latitu: String?, longitu: String?) {
        latitude = latitu.toString()
        longitude = longitu.toString()
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        Log.e("checkmylog", "cccc")
        val addresses: List<Address> =
            geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
        val cityName = addresses[0].locality
        tvloc.setText(cityName)

        Log.e("onFailureSearch", "onFailureSearch:,l,;;kl " + latitude + "---" + longitude)
    }

    override fun onActivityResult(resrequestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(resrequestCode, resultCode, data)
        if (data != null) {
            if (resrequestCode == requestCodes) {
                address = data.getStringExtra("address").toString()
                latitude = data.getStringExtra("latitude").toString()
                longitude = data.getStringExtra("longitude").toString()
                tv_selectlocation.text = address
            } /*else if (resrequestCode == 101) {
                val name = data.getStringExtra("id").toString()
//                visitCount=  data.getStringExtra("visitCount").toString()
                baseViewModel.getTeacherStudentList(requireActivity(), "2", name, false)
                baseViewModel.getCommonResponse().observe(requireActivity(), this)

            }*/
        }
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is MathChatResponse) {
//                    teacherlevel = ArrayList()
                    teacherlevel.clear()
                    teacherlevel.addAll(liveData.data.body)

//                    tvloc.setText(teacherlevel[0].address)
                    when_nodatavideo.visibility = View.GONE
                    searchHomeAdapter = SearchHomeAdapter(requireContext(), teacherlevel)
                    recycler_Homesearch.adapter = searchHomeAdapter
                } else {

                }
            }
            Status.ERROR -> {
                if (liveData.error is MathChatResponse) {
                    Helper.showSuccessToast(requireContext(), liveData.error.message)
                }
            }
            else -> {
                if (liveData.error is MathChatResponse)
                    Helper.showSuccessToast(requireContext(), liveData.error.message)
            }
        }
    }

    override fun Teachinglevel(level: ArrayList<String>) {
        selected_level.clear()
        selected_level.addAll(level)
    }

    fun cartified_as(cetcfied: ArrayList<String>) {
        selected_certified.clear()
        selected_certified.addAll(cetcfied)
    }

    fun apiTeacherList(userType: String) {
        baseViewModel.getTeacherStudentList(requireActivity(), userType, subjectId, true)
        baseViewModel.getCommonResponse().observe(requireActivity(), this)
    }

    fun teachinglevel() {
        baseViewModel.teacher_level(requireActivity(), false)
        baseViewModel.getCommonResponse().observe(requireActivity(), this)
    }

    override fun onResume() {
        super.onResume()
//        apiTeacherList("2")
//        viewType = "1"
    }

    private fun filterServices(text: String) {
        //new array list that will hold the filtered data
        val filterServicesList = ArrayList<MathChatResponse.Body>()
        //looping through existing elements
        for (s in teacherlevel) {
            //if the existing elements contains the search input
            if (s.name.lowercase().contains(text.lowercase()) || s.teachingLevel.lowercase()
                    .contains(text.lowercase())
            ) {
                //adding the element to filtered list
                filterServicesList.add(s)
            }
        }
        if (filterServicesList.size > 0) {
            when_nodatavideo.visibility = View.GONE
        } else {
            when_nodatavideo.visibility = View.VISIBLE
        }
        //calling a method of the adapter class and passing the filtered list
        if(!teacherlevel.isNotEmpty()){
            searchHomeAdapter!!.notifyData(filterServicesList)
        }


    }

}