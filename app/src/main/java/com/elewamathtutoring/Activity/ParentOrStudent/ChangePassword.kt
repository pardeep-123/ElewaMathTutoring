package com.elewamathtutoring.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
import kotlinx.android.synthetic.main.activity_change_password.*
import javax.inject.Inject

class ChangePassword : AppCompatActivity() , View.OnClickListener, Observer<RestObservable> {
    @Inject
    lateinit var validator: Validator
    val context: Context =this
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        App.getinstance().getmydicomponent().inject(this)

        btnChangePassword.setOnClickListener (this)
        ivBack.setOnClickListener (this)
        //  clickHandel(
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivBack -> {
                onBackPressed()
            }
            R.id.btnChangePassword -> {
                finish()
                /*if (validator.Change_Password(this, cs_oldpassword.text.toString(),cs_newpassword.text.toString(),confirm_passw.text.toString())) {
                    baseViewModel.Change_Password(this,cs_oldpassword.text.toString(),cs_newpassword.text.toString(),true)
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