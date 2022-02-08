package com.elewamathtutoring.Activity.Auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.PrivacyPolicy
import com.elewamathtutoring.Activity.TeacherOrTutor.AboutYouActivity
import com.elewamathtutoring.MainActivity
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
import kotlinx.android.synthetic.main.activity_sign_up.*
import javax.inject.Inject

class SignUp : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    @Inject
    lateinit var validator: Validator
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        App.getinstance().getmydicomponent().inject(this)
        ivBack.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        ivOf.setOnClickListener(this)
        ivOn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext -> {
                if( intent.getStringExtra("Name").equals("Tutor")){
                    startActivity(Intent(this, SignupTeacherActivity::class.java))
                }else{
                    startActivity(Intent(this, MainActivity::class.java))
                }
//                    if (validator.signUpValid(this,  edtName.text.toString(), edtEmail.text.toString(),editPassword.text.toString(),editConfirmPassword.text.toString())) {
//                        baseViewModel.checkEmail(this,  edtEmail.text.toString(), true)
//                        baseViewModel.getCommonResponse().observe(this, this)
//
//                    }
            }
            R.id.ivBack -> {
                onBackPressed()
            }
            R.id.tvTerms -> {
                startActivity(Intent(this, PrivacyPolicy::class.java))
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
                if (liveData.data is Commontoall) {
                    if( getPrefrence(Constants.user_type, "").equals("1")){
                        startActivity(Intent(this, SignupTeacherActivity::class.java))
                    }else{
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                   /* if (getPrefrence(Constants.user_type, "").equals("1")) {
                        intent = Intent(this, DescSignupScreen::class.java)
                        // student
                    } else {
                        // Teacher
                        intent = Intent(this, AboutYouActivity::class.java)
                        intent.putExtra("key", "signup")
                    }*/
                 /*   intent.putExtra("Name", edtName.text.toString())
                    intent.putExtra("email", edtEmail.text.toString())
                    intent.putExtra("password", editPassword.text.toString())
                    startActivity(intent)*/
                } else {
                }
            }

            Status.ERROR -> {
                if (liveData.error is Commontoall)
                    Helper.showSuccessToast(this, liveData.error.message)
            }
            else -> {
            }
        }

    }
}