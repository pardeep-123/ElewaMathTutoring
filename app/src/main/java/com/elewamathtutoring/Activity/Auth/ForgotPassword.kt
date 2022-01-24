package com.elewamathtutoring.Activity.Auth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.pawskeeper.Modecommon.Commontoall
import kotlinx.android.synthetic.main.activity_forgot_password.*
import javax.inject.Inject

class ForgotPassword : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    val context: Context =this
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    @Inject
    lateinit var validator: Validator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        App.getinstance().getmydicomponent().inject(this)
        allClickListeners()
    }
        fun allClickListeners(){
            ivBack.setOnClickListener(this)
            btn_forgetPassword.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            when(v.id){
                R.id.ivBack->{
                    onBackPressed()
                }
                R.id.btn_forgetPassword->{
                    finish()
                   /* if (validator.Emailvalidation(this, email_text.text.toString()))
                    {
                        baseViewModel.forgetPassword(this,  email_text.text.toString().trim(), true)
                        baseViewModel.getCommonResponse().observe(this, this)
                    }*/
                }
            }
        }
        override fun onChanged(liveData: RestObservable?) {
            when (liveData!!.status) {
                Status.SUCCESS -> {
                    if (liveData.data is Commontoall) {
                        Helper.showSuccessToast(this, liveData.data.message)
                        finish()
                    }
                    else
                    {
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