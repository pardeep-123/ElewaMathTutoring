package com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.TeacherOrTutor.availability.AvailablityActivity
import com.elewamathtutoring.Adapter.ClickCallBack
import com.elewamathtutoring.Adapter.TeacherOrTutor.EducationCertificateAdapter
import com.elewamathtutoring.Adapter.TeacherOrTutor.TeachingLevelAdapter
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.Location.MyNewMapActivity
import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.pawskeeper.Modecommon.Commontoall
import com.riseball.interface_base.Teachinglevel_interface
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_teaching_info.*
import kotlinx.android.synthetic.main.item_education_certificaate.*

import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class TeachingInfoActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable>,
    Teachinglevel_interface, ClickCallBack {
    @Inject
    lateinit var validator: Validator
    var profilelist = java.util.ArrayList<com.elewamathtutoring.Models.Login.Body>()
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    val context: Context = this
    var firstimage = ""
    var latitude = ""
    var longitude = ""
    var address = ""
    var certifiedChoose = 0
    var teachinglevel = ArrayList<String>()
    var CertifiedAs = ""
    var timeSlot = ""
    var availability = ""
    private val requestCodes = 11
    var list = ArrayList<TeachingLevelResponse.Body>()
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    val imageList = ArrayList<String>()
    var educationCertificateAdapter:EducationCertificateAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teaching_info)
        App.getinstance().getmydicomponent().inject(this)
        onClicks()
        Constants.scrollEditText(edSpeacialities)
        Constants.scrollEditText(edCancelationPolicy)
        // handel_add_Edit()
        spinnerChoose()
        // rv_teachingLevel.adapter = TeachingLevelAdapter(this, list, teachinglevel, this)
       educationCertificateAdapter = EducationCertificateAdapter(imageList, this)
                        rvUploadImage.adapter = educationCertificateAdapter

    }


    private fun handel_add_Edit() {
        if (intent.getStringExtra("key").equals("signup")) {
        } else {
            profilelist =
                (intent.getSerializableExtra("list_model") as java.util.ArrayList<com.elewamathtutoring.Models.Login.Body>?)!!
            edSpeacialities.setText(profilelist.get(0).specialties.toString())
            certifiedChoose = profilelist.get(0).isCertifiedOrtutor
            //  edInPersonRate.setText(profilelist.get(0).InPersonRate.toString())
            //  edVirtualRate.setText(profilelist.get(0).virtualRate.toString())
            edCancelationPolicy.setText(profilelist.get(0).cancellationPolicy.toString())
            latitude = profilelist.get(0).latitude.toString()
            longitude = profilelist.get(0).longitude.toString()
            availability = profilelist.get(0).availability.toString()
            timeSlot = profilelist.get(0).available_slots.toString()
            val num = profilelist.get(0).teachingLevel.toString()
            try {
                val str = num.split(",").toTypedArray()
                teachinglevel = str.toList() as ArrayList<String>
            } catch (e: Exception) {
                val str = num.split("").toTypedArray()
                teachinglevel = str.toList() as ArrayList<String>
            }
            //  teachinglevel = profilelist.get(0).teachingLevel.toString()
            getLocation(latitude, longitude)
            btnNext.text = "SAVE"
        }
    }
    private fun onClicks() {
        ivBack.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        edLocation.setOnClickListener(this)
        api()
    }
    private fun spinnerChoose() {
        val spinner1: Spinner = findViewById(R.id.spinnerChoose)
        var spinnerlist: ArrayList<String> = ArrayList()
        spinnerlist.add("")
        spinnerlist.add("Below high school")
        spinnerlist.add("High school diploma")
        spinnerlist.add("Bachlor's degree")
        spinnerlist.add("Bachlor's degree")
        spinnerlist.add("Master's degree")
        spinnerlist.add("PHD")
        val list: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.spinner_small,
            spinnerlist
        )
        list.setDropDownViewResource(R.layout.spinner_small)
        spinner1.setAdapter(list)
        try {
            spinner1.setSelection(certifiedChoose)
        } catch (e: Exception) {
        }
        spinner1.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View?,
                jj: Int,
                l: Long
            ) {

                Log.e("datacheckit", "----" + jj.toString())
                CertifiedAs = jj.toString()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                // text1.text = ""
            }
        })
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.btnNext -> {
                apiTeachingInfo()
            }
            R.id.edLocation -> {
                if (Helper.checkLocPermission(this@TeachingInfoActivity)) {
                    val intent = Intent(context, MyNewMapActivity::class.java)
                    startActivityForResult(intent, requestCodes)
                }
            }
        }
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is TeachingLevelResponse) {
                    list = ArrayList()
                    list.addAll(liveData.data.body)
                    rv_teachingLevel.adapter = TeachingLevelAdapter(this, list, teachinglevel, this)

                 /*   educationCertificateAdapter = EducationCertificateAdapter(imageList, this)
                    rvUploadImage.adapter = educationCertificateAdapter*/

                } else if (liveData.data is Commontoall) {
                    Helper.showSuccessToast(this, liveData.data.message)
                    finish()
                }


                if (liveData.data is TeachinInfoResponse) {

                    startActivity(Intent(this, AvailablityActivity::class.java))
                }
            }

            Status.ERROR -> {
                if (liveData.error is TeachingLevelResponse)
                    Helper.showSuccessToast(this, liveData.error.message)
            }
            else -> {
                if (liveData.error is TeachingLevelResponse)
                    Helper.showSuccessToast(this, liveData.error.message)
            }
        }
    }

    fun apiTeachingInfo() {
        baseViewModel.teachingInfoApi(this, imageList, CertifiedAs, etMajors.text.toString(),
            teachinglevel.toString(), edSpeacialities.text.toString(), edPrice.text.toString(), edCancelationPolicy.text.toString(), edLocation.text.toString(), latitude,longitude,true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    override fun onActivityResult(resrequestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(resrequestCode, resultCode, data)
        if (data != null) {
            if (resrequestCode == requestCodes) {
                address = data.getStringExtra("address").toString()
                latitude = data.getStringExtra("latitude").toString()
                longitude = data.getStringExtra("longitude").toString()
                edLocation.text = address
            }
        }
    }
    fun api() {
        baseViewModel.teacher_level(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
    override fun Teachinglevel(level: ArrayList<String>) {
        teachinglevel.clear()
        teachinglevel.addAll(level)
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
        edLocation.text = (addres)
    }

    override fun onItemClick(pos: Int, value: String) {
        when (value) {
            "gellery" -> {
                selectImage()
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun selectImage() {
        Album.image(this).multipleChoice().camera(true).columnCount(4).widget(
            Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build()
        )
            .onResult { result ->
                result.forEach {
                    imageList.add(it.path)
                    educationCertificateAdapter?.notifyDataSetChanged()
                }

            }.onCancel {
            }.start()
    }


}