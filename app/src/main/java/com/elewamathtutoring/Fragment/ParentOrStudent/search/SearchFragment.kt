package com.elewamathtutoring.Fragment.ParentOrStudent.search

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.Chat.mathChat.MathChatActivity
import com.elewamathtutoring.Activity.Chat.mathChat.MathChatResponse
import com.elewamathtutoring.Activity.NotificationsActivity
import com.elewamathtutoring.Activity.ParentOrStudent.FilterActivity
import com.elewamathtutoring.Activity.ParentOrStudent.resources.ResoucesActivity
import com.elewamathtutoring.Activity.ParentOrStudent.settings.SettingActivity
import com.elewamathtutoring.Adapter.FilterOptions2Adapter
import com.elewamathtutoring.Adapter.ParentOrStudent.FilterOptionsAdapter
import com.elewamathtutoring.Adapter.SearchHomeAdapter
import com.elewamathtutoring.Model.FilterOptions2Model
import com.elewamathtutoring.Model.FilterOptionsModel
import com.elewamathtutoring.Models.Search.Model_search
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.CheckLocationActivity
import com.elewamathtutoring.Util.Location.MyNewMapActivity
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


class SearchFragment : CheckLocationActivity()  , Observer<RestObservable>, Teachinglevel_interface {
    var teacherlevel = ArrayList<MathChatResponse.Body>()
    var Search_teacher = ArrayList<com.elewamathtutoring.Models.Search.Body>()
    var Search_data = ArrayList<com.elewamathtutoring.Models.Search.Body>()
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    var selected_level = ArrayList<String>()
    var selected_certified = ArrayList<String>()
    var getdata_toselected_certified = ArrayList<String>()
    var getdata_toselected_level = ArrayList<String>()
    lateinit var ivSetting: ImageView
    lateinit var seekbar: SeekBar
    lateinit var tv_selectlocation: TextView
    lateinit var rv_filterOptions1: RecyclerView
    lateinit var searchHomeAdapter: SearchHomeAdapter
    lateinit var v: View
    var latitude = ""
    var temp=0
    var longitude = ""
    var address = ""
    var Searchtext = ""
    var maxdistance = 500
    var getdata_tomaxdistance = 0
    var Filter_address = ""
    var Filter_lat = ""
    var Filter_long = ""
    var viewType = ""
    var currentPage = 0
    private val requestCodes = 11
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_search, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getdata_toselected_certified.add("1")
        getdata_toselected_certified.add("2")
        getdata_toselected_certified.add("3")

   /*     searchHomeAdapter = SearchHomeAdapter(requireContext())
        recycler_Homesearch.adapter = searchHomeAdapter*/
       // checkPermissionLocation(requireActivity())
       /* rl2.setOnClickListener {
            teachinglevel()
            filterDialog()
        }*/
        ivNotification.setOnClickListener {
            startActivity(Intent(context, NotificationsActivity::class.java))
        }
        rlMathChatRoom.setOnClickListener {
            startActivity(Intent(context, MathChatActivity::class.java)
                .putExtra("user","mathChatRoom"))
        }
        rlResources.setOnClickListener {
            startActivity(Intent(context, ResoucesActivity::class.java))
        }
        rlFilter.setOnClickListener {
            startActivity(Intent(context, FilterActivity::class.java))
        }
        ivSetting = view.findViewById(R.id.ivSetting)
        ivSetting.setOnClickListener {
         val intent = Intent(requireContext(), SettingActivity::class.java)
         startActivity(intent)
        }

       /* edtSearch.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        Searchtext = s.toString()
                        if (Searchtext.length == 0) {
                            recycler_searchdata.visibility = View.GONE
                        }
                        else {
                            recycler_searchdata.visibility = View.VISIBLE
                            searchapi("20", "1", selected_certified.toString().replace("[", "").replace(
                                    "]", "").replace(" ", ""), maxdistance.toString(), Searchtext, "", latitude, longitude,false)
                        }
                    }
                })


                idNestedSVideo.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                        currentPage++
                        Log.e("getDataFromAPI", "----" + currentPage.toString())
                        searchapi("20", "1", selected_certified.toString().replace("[", "").replace("]", "").replace(" ", ""), maxdistance.toString(), Searchtext, "", latitude, longitude,true)
                    }
                })*/
    }

    override fun onPermissionGranted() {
        Log.e("checkmylog","ooo")
    }

    override fun onLocationGet(latitu: String?, longitu: String?) {
        latitude = latitu.toString()
        longitude = longitu.toString()
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        Log.e("checkmylog","cccc")

        if(temp==0)
        {
            searchapi("20", "1", selected_certified.toString().replace("[", "").replace("]", "").replace(" ", ""),
                maxdistance.toString(), Searchtext,"", latitude,            longitude, true)
            temp++
        }

        val addresses: List<Address> = geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
        val cityName = addresses[0].locality
        tvloc.setText(cityName)

        Log.e("onFailureSearch", "onFailureSearch:,l,;;kl " + latitude + "---" + longitude)
    }

    private fun filterDialog() {
        val filterDialog = Dialog(requireContext())
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        filterDialog.setContentView(R.layout.dialog_filter)

        filterDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)

        filterDialog.window!!.setGravity(Gravity.CENTER)
        filterDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        filterDialog.setCancelable(true)
        filterDialog.setCanceledOnTouchOutside(true)
        rv_filterOptions1=filterDialog.findViewById(R.id.rv_filterOptions1)
        tv_selectlocation = filterDialog.findViewById(R.id.tv_selectlocation)
        seekbar = filterDialog.findViewById(R.id.seekbar)
        seekbar.setProgress(getdata_tomaxdistance)
        tv_selectlocation.text=Filter_address
        tv_selectlocation.setOnClickListener {
            if (Helper.checkLocPermission(requireActivity())) {
                val intent = Intent(context, MyNewMapActivity::class.java)
                startActivityForResult(intent, requestCodes)
            }
        }
        
        seekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                maxdistance = progress
                // write custom code for progress is changed
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        filterDialog.cut.setOnClickListener { filterDialog.dismiss() }
        filterDialog.button_search.setOnClickListener {
             Filter_address = address
             Filter_lat = latitude
             Filter_long = longitude
            getdata_tomaxdistance = maxdistance
            getdata_toselected_certified.clear()
            getdata_toselected_level.clear()

            getdata_toselected_certified.addAll(selected_certified)
            getdata_toselected_level.addAll(selected_level)

            searchapi("20", "1", selected_certified.toString().replace("[", "").replace("]", "").replace(" ", ""),
                maxdistance.toString(), Searchtext,
                selected_level.toString().replace("[", "").replace("]", "").replace(" ", ""),
                latitude, longitude, true)
            filterDialog.cancel()
        }

        setFilterOptions2Adapter(filterDialog)
        setFilterOptionsAdapter(filterDialog)
        Dialog_seekbar(filterDialog)
        filterDialog.show()
    }

    private fun setFilterOptionsAdapter(filterDialog: Dialog) {
        val filterOptionsList = ArrayList<FilterOptionsModel>()
        filterOptionsList.add(FilterOptionsModel("In-Person"))
        filterOptionsList.add(FilterOptionsModel("Virtual Learning"))
        filterDialog.rv_filterOptions.adapter = FilterOptionsAdapter(requireContext(), filterOptionsList)
    }

    private fun setFilterOptions2Adapter(filterDialog: Dialog){
        val filterOptionsList = ArrayList<FilterOptions2Model>()
        filterOptionsList.add(FilterOptions2Model("Certified Teacher"))
        filterOptionsList.add(FilterOptions2Model("Tutor"))
        filterOptionsList.add(FilterOptions2Model("Specialist"))
        filterDialog.rv_filterOptions2.adapter = FilterOptions2Adapter(requireContext(), filterOptionsList, this@SearchFragment,getdata_toselected_certified)
    }


    private fun Dialog_seekbar(filterDialog: Dialog) {
        filterDialog.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                val p: String = progress.toString()
                maxdistance=p.toInt()
                filterDialog.tvDistance.text = p + " miles"

            }
        })
    }

    override fun onActivityResult(resrequestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(resrequestCode, resultCode, data)
        if (data != null) {
            if (resrequestCode == requestCodes) {
                address = data.getStringExtra("address").toString()
                latitude = data.getStringExtra("latitude").toString()
                longitude = data.getStringExtra("longitude").toString()
                tv_selectlocation.text =address
            }
        }
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is MathChatResponse) {
                   // teacherlevel = ArrayList()
                    teacherlevel.addAll(liveData.data.body)

                    recycler_Homesearch.adapter = SearchHomeAdapter(requireContext(), teacherlevel)
                   // rv_filterOptions1.adapter = TeachingLevelAdapter(requireContext(), teacherlevel, getdata_toselected_level,this)
                }
                /*else if (liveData.data is Model_search) {
                    Search_teacher.clear()
                    Search_teacher.addAll(listOf(liveData.data.body))

                    if(Searchtext.isEmpty())
                    {
                        *//*recycler_Homesearch.setLayoutManager(GridLayoutManager(context, 2))
                        searchHomeAdapter = SearchHomeAdapter(requireContext(), Search_teacher)
                        recycler_Homesearch.adapter = searchHomeAdapter*//*
                    }
                    else
                    {
                        if(Search_teacher.get(0).data.size==0)
                        {
                            recycler_searchdata.visibility=View.GONE
                        }
                        else
                        {
                            Search_data.clear()
                            Search_data.addAll(listOf(liveData.data.body))
                            recycler_searchdata.visibility=View.VISIBLE
                            recycler_searchdata.adapter= Teaching_searchresultAdapter(requireContext(), Search_data)
                        }
                    }
                }*/
                else
                {
                }
            }
            Status.ERROR -> {
                if (liveData.error is MathChatResponse) {
                    Helper.showSuccessToast(requireContext(), liveData.error.message)
                } else if (liveData.error is Model_search) {
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
        baseViewModel.getTeacherStudentList(requireActivity(), userType, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

  fun  searchapi(limit: String, page: String, CertifiedAs: String, maximumDistance: String, searchText: String,
      teachingLevel: String, lat: String, lng: String, b: Boolean, ) {
        baseViewModel.seach_teachers(requireActivity(), limit, page, CertifiedAs, maximumDistance, searchText, teachingLevel, lat, lng, b)
        baseViewModel.getCommonResponse().observe(requireActivity(), this)

    }

    fun teachinglevel() {
        baseViewModel.teacher_level(requireActivity(), false)
        baseViewModel.getCommonResponse().observe(requireActivity(), this)
    }

    override fun onResume() {
        super.onResume()
        apiTeacherList("1")
        viewType = "1"
    }
}