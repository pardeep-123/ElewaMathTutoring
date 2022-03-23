package com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.TeacherOrTutor.availability.AvailablityActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.edit.EditResponse
import com.elewamathtutoring.Adapter.ClickCallBack
import com.elewamathtutoring.Adapter.TeacherOrTutor.EducationCertificateAdapter
import com.elewamathtutoring.Adapter.TeacherOrTutor.TeachingLevelAdapter
import com.elewamathtutoring.Model.EducationLevel
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.Location.MyNewMapActivity
import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.riseball.interface_base.Teachinglevel_interface
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_teaching_info.*
import kotlinx.android.synthetic.main.item_education_certificaate.*
import kotlinx.android.synthetic.main.spinner_small.*

import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

import com.elewamathtutoring.Activity.Chat.chatModel.User
import com.elewamathtutoring.Activity.ParentOrStudent.filter.FilterActivity
import com.elewamathtutoring.Model.ImageModel


class TeachingInfoActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable>,
    Teachinglevel_interface, ClickCallBack {
    @Inject
    lateinit var validator: Validator
    var profilelist: ArrayList<EditResponse.Body> = ArrayList()
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    val context: Context = this
    var spinnerlist: ArrayList<EducationLevel> = ArrayList()

    var firstimage = ""
    var latitude = ""
    var longitude = ""
    var address = ""
    var certifiedChoose = 0
    var teachinglevel = ArrayList<String>()
    var teachingValue = ""
    var nameList = ArrayList<String>()
    var idList = ArrayList<String>()
    var CertifiedAs = ""
    var timeSlot = ""
    var availability = ""
    var subjectName = ""
    var subjectId = ""
    private val requestCodes = 11
    var list = ArrayList<TeachingLevelResponse.Body>()
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    val imageList = ArrayList<ImageModel>()
    var educationCertificateAdapter: EducationCertificateAdapter? = null

    private val filterResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == 101) {
            Log.e("subjectssssssssss", result.data.toString())
            subjectName = result.data?.getStringExtra("name")!!
            subjectId = result.data?.getStringExtra("id")!!

            edSpeacialities.setText(subjectName)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teaching_info)
        App.getinstance().getmydicomponent().inject(this)
        onClicks()
        Constants.scrollEditText(edSpeacialities)
        Constants.scrollEditText(edCancelationPolicy)

        spinnerChoose()
        educationCertificateAdapter = EducationCertificateAdapter(imageList, this)
        rvUploadImage.adapter = educationCertificateAdapter
        handel_add_Edit()
    }

    private fun handel_add_Edit() {
        if (intent.getStringExtra("signup")!!.equals("editrofile")) {
            profilelist = intent.extras?.get("list_model") as ArrayList<EditResponse.Body>

            // set arraylist of name and id's
            profilelist[0].subjects.forEach {
                nameList.add(it.name)
                idList.add(it.id.toString())
            }
            subjectName = TextUtils.join(",",nameList)
            subjectId = TextUtils.join(",",idList)
             edSpeacialities.setText(subjectName)
            certifiedChoose = profilelist[0].isCertifiedOrtutor!!
            etMajors.setText(profilelist[0].majors)
            edPrice.setText(profilelist[0].hourlyPrice.toString())
            edCancelationPolicy.setText(profilelist[0].cancellationPolicy)
            latitude = profilelist.get(0).latitude
            longitude = profilelist.get(0).longitude
            availability = profilelist.get(0).availability
            timeSlot = profilelist.get(0).availableSlots
            val num = profilelist[0].teachingLevelString
            try {
                val str = num?.split(",")?.toTypedArray()
                teachinglevel = str?.toList() as ArrayList<String>
            } catch (e: Exception) {
                val str = num?.split("")?.toTypedArray()
                teachinglevel = str?.toList() as ArrayList<String>
            }
            getLocation(latitude, longitude)
            btnNext.text = "SAVE"
            profilelist[0].certificateImages?.forEach { its ->
                imageList.add(ImageModel().also {
                    it.image = its.images
                    it.isGalleryAdded = false
                })
            }
            val data =
                spinnerlist.filter { it.educationName.toString() == profilelist[0].educationLevel }
            spinnerChoose.setSelection(spinnerlist.indexOf(data[0]))
            educationCertificateAdapter!!.notifyDataSetChanged()
        }
    }

    private fun onClicks() {
        ivBack.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        edLocation.setOnClickListener(this)
        edSpeacialities.setOnClickListener(this)
        apiTeacher_level()
    }

    private fun spinnerChoose() {
        val spinner1: Spinner = findViewById(R.id.spinnerChoose)

        spinnerlist.add(0, EducationLevel(""))
        spinnerlist.add(1, EducationLevel("Below high school"))
        spinnerlist.add(2, EducationLevel("High school diploma"))
        spinnerlist.add(3, EducationLevel("Bachlor's degree"))
        spinnerlist.add(4, EducationLevel("Bachlor's degree"))
        spinnerlist.add(5, EducationLevel("Master's degree"))
        spinnerlist.add(6, EducationLevel("PHD"))
        val list: ArrayAdapter<EducationLevel> =
            ArrayAdapter<EducationLevel>(this, R.layout.spinner_small, spinnerlist)
        list.setDropDownViewResource(R.layout.spinner_small)
        spinner1.setAdapter(list)
        try {
            spinner1.setSelection(certifiedChoose)
            val data =
                spinnerlist.filter { it.educationName.toString() == profilelist[0].educationLevel }
            spinner1.setSelection(spinnerlist.indexOf(data[0]))
        } catch (e: Exception) {
        }
        spinner1.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View?,
                jj: Int,
                l: Long
            ) {
                val selectedObject = spinner1.selectedItem as EducationLevel
//                Log.e("datacheckit", "----" + jj.toString())
//                CertifiedAs = jj.toString()
                Log.e("datacheckit", "----" + selectedObject.educationName.toString())
                CertifiedAs = selectedObject.educationName
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.edSpeacialities -> {
                if(intent.getStringExtra("signup")!!.equals("editrofile")){
                    val intent = Intent(context, FilterActivity::class.java)
                    intent.putExtra("editId",subjectId)
                    intent.putExtra("editName",subjectName)
                    filterResult.launch(intent)

                }else {
                    val intent = Intent(context, FilterActivity::class.java)
                    intent.putExtra("editId",subjectId)
                    intent.putExtra("editName",subjectName)
                    filterResult.launch(intent)
                }
                //      startActivityForResult(intent, 101)
            }
            R.id.btnNext -> {
                if (intent.getStringExtra("signup").equals("editrofile")) {
                    baseViewModel.EditTeacherProfileProfile(
                        this,
                        imageList,
                        teachingValue,
                        CertifiedAs,
                        etMajors.text.toString(),
                        subjectId,
                        edCancelationPolicy.text.toString(),
                        edPrice.text.toString(),
                        address,
                        latitude,
                        longitude,
                        true
                    )
                    baseViewModel.getCommonResponse().observe(this, this)
                } else {
                    apiTeachingInfo()
                }
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
                } else if (liveData.data is EditTeachingInfoResponse) {
                    Helper.showSuccessToast(this, liveData.data.message)
                    finish()
                } else if (liveData.data is TeachinInfoResponse) {
                    startActivity(
                        Intent(this, AvailablityActivity::class.java)
                            .putExtra("signup", "teacher")
                    )
                    finishAffinity()
                }
            }
            Status.ERROR -> {
                if (liveData.error is TeachingLevelResponse)
                    Helper.showSuccessToast(this, liveData.error.message)
            }
            else -> {
                if (liveData.error is EditTeachingInfoResponse)
                    Helper.showSuccessToast(this, liveData.error.message)
            }
        }
    }

    fun apiTeachingInfo() {
        baseViewModel.teachingInfoApi(
            this,
            imageList,
            CertifiedAs,
            etMajors.text.toString(),
            teachingValue,
            subjectId,
            edPrice.text.toString(),
            edCancelationPolicy.text.toString(),
            edLocation.text.toString(),
            latitude,
            longitude,
            true
        )
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

    fun apiTeacher_level() {
        baseViewModel.teacher_level(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    override fun Teachinglevel(level: ArrayList<String>) {
        teachinglevel.clear()
        teachinglevel.addAll(level)
        teachingValue = TextUtils.join(",",level)

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
                result.forEach { its ->
                    imageList.add(ImageModel().also {
                        it.image = its.path
                        it.isGalleryAdded = true
                    })
                    educationCertificateAdapter?.notifyDataSetChanged()
                }
            }.onCancel {
            }.start()
    }
}