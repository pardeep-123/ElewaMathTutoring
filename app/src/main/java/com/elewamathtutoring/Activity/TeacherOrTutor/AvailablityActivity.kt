package com.elewamathtutoring.Activity.TeacherOrTutor

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Adapter.TeacherOrTutor.DatesAvailableAdapter
import com.elewamathtutoring.Adapter.TeacherOrTutor.TimeSlotAvailableAdapter
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.Model.DatesAvailableModel
import com.elewamathtutoring.Models.Login.Body
import com.elewamathtutoring.Models.Login.Model_login
import com.elewamathtutoring.Models.Time_slots.Model_timeslots
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.Util.helper.extensions.savePrefrence
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.pawskeeper.Modecommon.Commontoall
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.activity_availablity.*
import kotlinx.android.synthetic.main.activity_availablity.ivBack
import kotlinx.android.synthetic.main.activity_teaching_info.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class AvailablityActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    @Inject
    lateinit var validator: Validator
    var profilelist= java.util.ArrayList<com.elewamathtutoring.Models.Login.Body>()
var address=""
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    val context: Context =this
    var list = ArrayList<DatesAvailableModel>()
    var timeSlotList = ArrayList<com.elewamathtutoring.Models.Time_slots.Body>()
    var Array_date=ArrayList<String>()
    var Selctedarray_date=ArrayList<String>()
    var Selctedarray_time=ArrayList<String>()
    var Array_time=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_availablity)
        App.getinstance().getmydicomponent().inject(this)
        baseViewModel.get_time_slots(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
        if(intent.getStringExtra("key").equals("nonedit"))
        {

        }

      /*  else
        {
            profilelist = (intent.getSerializableExtra("list_model") as java.util.ArrayList<Body>?)!!

            getLocation(profilelist.get(0).latitude,profilelist.get(0).longitude)
            val data = profilelist.get(0).availability.toString()
            val words: ArrayList<String> = data.split(",") as ArrayList<String>
            Selctedarray_date=words


            val data2 = profilelist.get(0).available_slots.toString()
            val words2: ArrayList<String> = data2.split(",") as ArrayList<String>
            Selctedarray_time=words2
            btnConfirmSignUp.text="SAVE"
        }*/

        onClicks()
        setAdapters()

    }

    private fun setAdapters()
    {
        list.add(DatesAvailableModel("Monday"))
        list.add(DatesAvailableModel("Tuesday"))
        list.add(DatesAvailableModel("Wednesday"))
        list.add(DatesAvailableModel("Thursday"))
        list.add(DatesAvailableModel("Friday"))
        list.add(DatesAvailableModel("Saturday"))
        list.add(DatesAvailableModel("Sunday"))
        rv_datesAvailable.adapter = DatesAvailableAdapter(list, Selctedarray_date,this@AvailablityActivity)
     /*   rvFreeSlot.adapter = DatesAvailableAdapter(list, Selctedarray_date,this@AvailablityActivity)*/
    }

    private fun onClicks() {
        btnConfirmSignUp.setOnClickListener(this)
        ivBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnConfirmSignUp -> {

                if(intent.getStringExtra("key").equals("available"))
                {
finish()
                }
                else {
                    startActivity(Intent(this, MainTeacherActivity::class.java))
                    finishAffinity()
                }
          /*      if (validator.Teacherdelectdatetime(this, Array_date, Array_time)) {

                    if (intent.getStringExtra("key").equals("nonedit")) {

                        if(getPrefrence(Constants.Social_login, "").equals("True"))
                        {
                            baseViewModel.TeacherSocialSignup(this, intent.getStringExtra("Image").toString(),  intent.getStringExtra("socialId").toString(),
                                intent.getStringExtra("socialtype").toString(),
                                intent.getStringExtra("Name").toString(),
                                intent.getStringExtra("email").toString(),
                                intent.getStringExtra("password").toString(),
                                intent.getStringExtra("about_teacher").toString(),
                                intent.getStringExtra("teacher_history").toString(),
                                intent.getStringExtra("teachinglevel").toString(),
                                intent.getStringExtra("specialties").toString(),
                                intent.getStringExtra("inPersonRate").toString(),
                                intent.getStringExtra("cancelPolicy").toString(),
                                intent.getStringExtra("VirtualRate").toString(),
                                intent.getStringExtra("CertifiedAs").toString(),
                                intent.getStringExtra("address").toString(),
                                intent.getStringExtra("latitude").toString(),
                                intent.getStringExtra("longitude").toString(),
                                Array_date.toString().replace("[", "").replace("]", "")
                                    .replace(" ", ""),
                                Array_time.toString().replace("[", "").replace("]", "")
                                    .replace(" ", ""), true
                            )
                        }
                        else {

                            baseViewModel.signas_teacher(this, intent.getStringExtra("Image").toString(),
                                intent.getStringExtra("Name").toString(),
                                intent.getStringExtra("email").toString(),
                                intent.getStringExtra("password").toString(),
                                intent.getStringExtra("about_teacher").toString(),
                                intent.getStringExtra("teacher_history").toString(),
                                intent.getStringExtra("teachinglevel").toString(),
                                intent.getStringExtra("specialties").toString(),
                                intent.getStringExtra("inPersonRate").toString(),
                                intent.getStringExtra("cancelPolicy").toString(),
                                intent.getStringExtra("VirtualRate").toString(),
                                intent.getStringExtra("CertifiedAs").toString(),
                                intent.getStringExtra("address").toString(),
                                intent.getStringExtra("latitude").toString(),
                                intent.getStringExtra("longitude").toString(),
                                Array_date.toString().replace("[", "").replace("]", "")
                                    .replace(" ", ""),
                                Array_time.toString().replace("[", "").replace("]", "")
                                    .replace(" ", ""), true
                            )
                        }
                        baseViewModel.getCommonResponse().observe(this, this)
                    }
                    else
                    {

                            baseViewModel.EditTeacherProfileProfile(this,
                                profilelist.get(0).teachingLevel,   profilelist.get(0).specialties,profilelist.get(0).InPersonRate.toString()
                                ,profilelist.get(0).cancellationPolicy
                                ,profilelist.get(0).virtualRate.toString()
                                ,profilelist.get(0).isCertifiedOrtutor.toString()
                                ,address
                                ,profilelist.get(0).latitude.toString()
                                ,profilelist.get(0).longitude.toString(),
                                Array_date.toString().replace("[", "").replace("]", "").replace(" ", ""),
                                Array_time.toString().replace("[", "").replace("]", "").replace(" ", ""), true)
                            baseViewModel.getCommonResponse().observe(this, this)
                    }
                }*/
            }
            R.id.ivBack -> {
                finish()
            }
        }
    }
    fun Selected_date(date: ArrayList<String>)
    {
        Array_date.clear()
        Array_date.addAll(date)
    }
    fun Selected_time(time: ArrayList<String>)
    {
        Array_time.clear()
        Array_time.addAll(time)
    }
    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is Model_login) {
                    Helper.showSuccessToast(this, liveData.data.message)
                    Log.e(
                        "DEVICETOCKEN",
                        "" + liveData.data.body.token + " dT--" + liveData.data.body.deviceToken
                    )
                    savePrefrence(Constants.AUTH_KEY, liveData.data.body.token)
                    savePrefrence(Constants.USER_ID, liveData.data.body.id.toString())
                    Constants.USER_IDValue=liveData.data.body.id.toString()
                    savePrefrence(
                        Constants.notificationStatus,
                        liveData.data.body.notificationStatus.toString()
                    )
                    savePrefrence(Constants.Social_login, "False")
                    savePrefrence(Constants.user_type, liveData.data.body.userType.toString())
                    startActivity(Intent(this, MainTeacherActivity::class.java))
                    finishAffinity()
                    /*if (liveData.data.body.userType == 1) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(this, SubscriptionsActivity::class.java))
                        finish()
                    }*/
                }
                else  if(liveData.data is Commontoall)
                {
                    Helper.showSuccessToast(this, liveData.data.message)
                    finish()
                }
                else if (liveData.data is Model_timeslots) {
                    timeSlotList.addAll(liveData.data.body)
                    rv_timeSlotsAvailable.adapter = TimeSlotAvailableAdapter(
                        timeSlotList,Selctedarray_time,
                        this@AvailablityActivity
                    )
                } else {
                }
            }

            Status.ERROR -> {
                if (liveData.error is Model_login) {
                    Helper.showSuccessToast(this, liveData.error.message)
                } else if (liveData.error is Model_timeslots) {

                }
            }
            else -> {

            }
        }
    }
    fun getLocation(latitude: String, longitude: String) {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())
        addresses = geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
        // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        val addres: String = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        address=addres

    }

}