package com.elewamathtutoring.Activity.Auth.signup

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.Auth.SignupTeacherActivity
import com.elewamathtutoring.Activity.ParentOrStudent.privacy.PrivacyPolicy
import com.elewamathtutoring.Activity.TeacherOrTutor.MainTeacherActivity
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.SharedPrefUtil
import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.Util.helper.extensions.savePrefrence
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.HashMap
import javax.inject.Inject
///1 for student and 2 for tutor
class SignUp : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    @Inject
    lateinit var validator: Validator
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    lateinit var shared: SharedPrefUtil
    var token = ""
    var newPassword = false
    var confirmPassword = false
    var message3 = "Terms of Service"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        shared = SharedPrefUtil(this)
        getFirebaseToken()
        App.getinstance().getmydicomponent().inject(this)
        ivBack.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        tvTerms.setOnClickListener(this)
        ivOf.setOnClickListener(this)
        ivOn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext -> {
                /* if( intent.getStringExtra("Name").equals("Tutor")){
                     startActivity(Intent(this, SignupTeacherActivity::class.java))
                 }else{
                     startActivity(Intent(this, MainActivity::class.java))
                 }*/
                if (getPrefrence(Constants.user_type, "").equals("1")) {
                    if (validator.signUpValid(this, edtName.text.toString(), edtEmail.text.toString(), editPassword.text.toString(), editConfirmPassword.text.toString())) {
                        if(ivOf.visibility == View.VISIBLE){
                            Helper.showErrorAlert(this, "Please select terms & conidtions")
                        }else{
                            baseViewModel.signUpApi(this,  edtName.text.toString(),edtEmail.text.toString(), editPassword.text.toString(),"1", true,"1",token)
                            baseViewModel.getCommonResponse().observe(this, this)
                        }


                    }
                } else if (getPrefrence(Constants.user_type, "").equals("2")){
                    if (validator.signUpValid(this, edtName.text.toString(), edtEmail.text.toString(), editPassword.text.toString(), editConfirmPassword.text.toString())) {
                        if(ivOf.visibility == View.VISIBLE){
                            Helper.showErrorAlert(this, "Please select terms & conidtions")
                        }else{
                            startActivity(
                                Intent(this, SignupTeacherActivity::class.java)
                                    .putExtra("signup","teacher")
                                    .putExtra("name", edtName.text.toString())
                                    .putExtra("email", edtEmail.text.toString())
                                    .putExtra("password", editPassword.text.toString())
                            )
                        }
                    }
                }
            }

            /*R.id.tvShow1 -> {
                if (newPassword) {
                    etPassword.setTransformationMethod(PasswordTransformationMethod())
                    etPassword.setSelection(etPassword.text!!.length)
                    newPassword = false
                    tvShow1.setText("Show")
                } else {
                    etPassword.setTransformationMethod(null)
                    etPassword.setSelection(etPassword.text!!.length)
                    newPassword = true
                    tvShow1.setText("Hide")
                }
            }*/
            R.id.ivBack -> {
                onBackPressed()
            }
            R.id.tvTerms -> {
                startActivity(Intent(this, PrivacyPolicy::class.java)
                        .putExtra("key", message3)
                        .putExtra("type", "2"))
            }
            R.id.ivOf -> {
                ivOn.visibility = View.VISIBLE
                ivOf.visibility = View.GONE
            }
            R.id.ivOn -> {
                ivOn.visibility = View.GONE
                ivOf.visibility = View.VISIBLE
            }
        }
    }
    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is SignUpResponse) {
                    if (getPrefrence(Constants.user_type, "").equals("1")) {
                        savePrefrence("userType", "1")
                        shared.isLogin = true
                        savePrefrence(Constants.AUTH_KEY, liveData.data.body.token)
                        savePrefrence(Constants.USER_ID, liveData.data.body.id.toString())
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity()
                    } else {
                        savePrefrence("userType", "2")
                        savePrefrence(Constants.AUTH_KEY, liveData.data.body.token)
                        startActivity(Intent(this, MainTeacherActivity::class.java))
                        finishAffinity()
                    }
                 /*
                    intent.putExtra("name", edtName.text.toString())
                        intent.putExtra("email", edtEmail.text.toString())
                        intent.putExtra("password", editPassword.text.toString())
                        startActivity(intent)*/
                } else {
                }
            }
            Status.ERROR -> {
                if (liveData.error is SignUpResponse)
                    Helper.showSuccessToast(this, liveData.error.message)
            }
            else -> {
            }
        }
    }

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("Login", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            token = task.result.toString()
            Log.e("Fetching FCM ", token)
        })
    }

}