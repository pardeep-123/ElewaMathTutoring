package com.elewamathtutoring.Activity.TeacherOrTutor.availability

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.TeacherOrTutor.MainTeacherActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.edit.EditResponse
import com.elewamathtutoring.Adapter.TeacherOrTutor.DatesAvailableAdapter
import com.elewamathtutoring.Adapter.TeacherOrTutor.TimeSlotAvailableAdapter
import com.elewamathtutoring.Model.DatesAvailableModel
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.SharedPrefUtil
import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.Util.helper.extensions.savePrefrence
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_availablity.*

import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class AvailablityActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    lateinit var dialog: Dialog
    @Inject
    lateinit var validator: Validator
    var profilelist = java.util.ArrayList<EditResponse.Body>()
    var address = ""
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    val context: Context = this
    var list = ArrayList<DatesAvailableModel>()
    var timeSlotList = ArrayList<TimeSlotsResponse.Body>()
    var Array_date = ArrayList<String>()
    var Selctedarray_date = ArrayList<String>()
    var Selctedarray_time = ArrayList<String>()
    var Array_time = ArrayList<String>()
    lateinit var shared: SharedPrefUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_availablity)
        App.getinstance().getmydicomponent().inject(this)
        apiTimeSlot()

        if (intent.extras!=null) {
            if (intent.getStringExtra("signup").equals("editrofile")) {
                profilelist = (intent.getSerializableExtra("list_model") as java.util.ArrayList<EditResponse.Body>?)!!
//              getLocation(profilelist.get(0).latitude,profilelist.get(0).longitude)
                val data = profilelist.get(0).availability.toString()
                val words: ArrayList<String> = data.split(",") as ArrayList<String>
                Selctedarray_date = words

                val data2 = profilelist.get(0).available_slots.toString()
                val words2: ArrayList<String> = data2.split(",") as ArrayList<String>
                Selctedarray_time = words2
                btnConfirmSignUp.text = "SAVE"
            }
        }
        onClicks()
        setAdapters()
    }
    private fun setAdapters() {
        list.add(DatesAvailableModel("Monday"))
        list.add(DatesAvailableModel("Tuesday"))
        list.add(DatesAvailableModel("Wednesday"))
        list.add(DatesAvailableModel("Thursday"))
        list.add(DatesAvailableModel("Friday"))
        list.add(DatesAvailableModel("Saturday"))
        list.add(DatesAvailableModel("Sunday"))
        rv_datesAvailable.adapter = DatesAvailableAdapter(list, Selctedarray_date, this@AvailablityActivity)
    }

    private fun onClicks() {
        btnConfirmSignUp.setOnClickListener(this)
        ivBack.setOnClickListener(this)
    }

    fun apiTimeSlot() {
        baseViewModel.get_time_slots(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    fun dialogAdmin() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_teacher_signupsuccessfully)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.show()
        val btnOK = dialog.findViewById<Button>(R.id.btnOK)
        btnOK.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, MainTeacherActivity::class.java))
            finishAffinity()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnConfirmSignUp -> {
                if (validator.Teacherdelectdatetime(this, Array_date, Array_time)) {
                    if (intent.getStringExtra("signup").equals("teacher")) {
                        baseViewModel.TeacherAvailability(this,
                            Array_date.toString().replace("[", "").replace("]", "")
                                .replace(" ", ""),
                            Array_time.toString().replace("[", "").replace("]", "")
                                .replace(" ", ""), true
                        )
                        baseViewModel.getCommonResponse().observe(this, this)
                    } else {
                        baseViewModel.EditTeacherAvailablity(
                            this,
                            Array_date.toString().replace("[", "").replace("]", "")
                                .replace(" ", ""),
                            Array_time.toString().replace("[", "").replace("]", "")
                                .replace(" ", ""), true
                        )
                        baseViewModel.getCommonResponse().observe(this, this)
                    }
                }
            }
            R.id.ivBack -> {
                finish()
            }
        }
    }

    fun Selected_date(date: ArrayList<String>) {
        Array_date.clear()
        Array_date.addAll(date)
    }

    fun Selected_time(time: ArrayList<String>) {
        Array_time.clear()
        Array_time.addAll(time)
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is AvailabilityResponse) {
                    Helper.showSuccessToast(this, liveData.data.message)
                   // savePrefrence(Constants.AUTH_KEY, liveData.data.body.token.toString())

                    savePrefrence(Constants.USER_ID, liveData.data.body.id.toString())
                    Constants.USER_IDValue = liveData.data.body.id.toString()
                    savePrefrence(Constants.notificationStatus, liveData.data.body.notificationStatus.toString())
                    savePrefrence(Constants.Social_login, "False")
                    savePrefrence(Constants.user_type, liveData.data.body.userType.toString())

                    dialogAdmin()

                } else if (liveData.data is EditResponse) {
                    Helper.showSuccessToast(this, liveData.data.message)
                    finish()
                } else if (liveData.data is EditAvailabilityResponse) {
                    Helper.showSuccessToast(this, liveData.data.message)
                    finish()
                }
                 if (liveData.data is TimeSlotsResponse) {
                    timeSlotList.addAll(liveData.data.body)
                    rv_timeSlotsAvailable.adapter = TimeSlotAvailableAdapter(timeSlotList, Selctedarray_time, this@AvailablityActivity)
                }
            }
            Status.ERROR -> {
                if (liveData.error is AvailabilityResponse) {
                    Helper.showSuccessToast(this, liveData.error.message)
                } else if (liveData.error is EditResponse) {

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
        val addres: String =
            addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        address = addres
    }
}