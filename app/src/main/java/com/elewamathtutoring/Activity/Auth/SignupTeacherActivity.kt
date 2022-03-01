package com.elewamathtutoring.Activity.Auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.Auth.signup.TeacherSignUpResponse
import com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfo.TeachingInfoActivity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.Util.helper.extensions.savePrefrence
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import com.elewamathtutoring.network.RestObservable
import kotlinx.android.synthetic.main.activity_signup_teacher.*
import kotlinx.android.synthetic.main.activity_signup_teacher.btnNext
import kotlinx.android.synthetic.main.activity_signup_teacher.ivBack
import java.util.*
import javax.inject.Inject

class SignupTeacherActivity : AppCompatActivity(), View.OnClickListener , Observer<RestObservable>{
    var name = ""
    var email = ""
    var password = ""

    @Inject
    lateinit var validator: Validator
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }

    private var mAlbumFiles = ArrayList<AlbumFile>()
    private var firstImage = ""
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_teacher)
        App.getinstance().getmydicomponent().inject(this)
        context = this
        ivBack.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        add_img.setOnClickListener(this)
        if(intent.getStringExtra("signup")!!.equals("teacher")) {
            name = intent.getStringExtra("name").toString()
            email = intent.getStringExtra("email").toString()
            password = intent.getStringExtra("password").toString()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext -> {
                if (firstImage.isEmpty()) {
                    Helper.showErrorAlert(this, context.getString(R.string.error_empty_image))
                } else if (cs_message.text.toString().isEmpty()) {
                    Helper.showErrorAlert(this, "Please enter about you.")
                } else if (et_teaching.text.toString().isEmpty()) {
                    Helper.showErrorAlert(this, "Please enter about your teaching history.")
                }
                else{
                    apiSignupTutor()
                }
            }
            R.id.ivBack -> {
                finish()
            }
            R.id.add_img -> {
                selectImage()
            }
        }
    }

    private fun selectImage() {
        Album.image(this).singleChoice().camera(true).columnCount(4).widget(
            Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build()
        )
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivImage)
                firstImage = result[0].path
            }.onCancel {
            }.start()
    }
    fun apiSignupTutor(){
            baseViewModel.signUpTeacherApi(this,firstImage,name,email,password,cs_message.text.toString(),et_teaching.text.toString(),true)
            baseViewModel.getCommonResponse().observe(this, this)
    }
    override fun onChanged(it: RestObservable?) {
        when (it!!.status) {
            Status.SUCCESS -> {
                if (it.data is TeacherSignUpResponse) {
                    savePrefrence(Constants.AUTH_KEY, it.data.body.token)
                    savePrefrence(Constants.USER_ID, it.data.body.id.toString())
                    if (getPrefrence(Constants.user_type, "").equals("2")) {
                        startActivity(
                            Intent(this, TeachingInfoActivity::class.java)
                                .putExtra("signup", "teacher")
                        )
                    }
                }
            }
            Status.ERROR -> {
                if (it.error is TeacherSignUpResponse)
                    Helper.showSuccessToast(this, it.error.message)
            }
            else -> {
            }
        }
    }
}