package com.elewamathtutoring.Activity.ParentOrStudent.settings

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.Auth.SignUpAs
import com.elewamathtutoring.Activity.ParentOrStudent.privacy.PrivacyPolicy
import com.elewamathtutoring.Activity.ParentOrStudent.resources.changepassword.ChangePassword
import com.elewamathtutoring.Activity.PaymentInfoActivity
import com.elewamathtutoring.Activity.SendFeedback
import com.elewamathtutoring.Activity.WithdrawalActivity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.SharedPrefUtil
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.Util.helper.extensions.clearPrefrences
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.Util.helper.extensions.savePrefrence
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.pawskeeper.Modecommon.Commontoall
import com.pawskeeper.Modecommon.Commontoall2
import kotlinx.android.synthetic.main.activity_setting2.*
import kotlinx.android.synthetic.main.activity_setting2.ivBack

class SettingActivity : AppCompatActivity(), Observer<RestObservable> {
    var message1 = "About Us"
    var message2 = "Privacy Policy"
    var message3 = "Terms of Service"
    var context: Context = this
    var status=""
    lateinit var shared: SharedPrefUtil

    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting2)
        shared = SharedPrefUtil(this)

        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT;
        if(getPrefrence(Constants.Social_login, "").equals("True"))
        {
            tvChangePassword.visibility=View.GONE
        }
        if(getPrefrence(Constants.notificationStatus,"").toString().equals("1")) {
            Log.e("checklog", "onnnnnnnnnnnnnn")
            status="0"
            iv_notification_switch.setChecked(true);
        } else {
            status="1"
            iv_notification_switch.setChecked(false);
        }
        iv_notification_switch.setOnClickListener {
            if (iv_notification_switch!!.isChecked) {
                iv_notification_switch.setChecked(true);
                Log.e("checklog", "onnnnnnnnnnnnnn")
                status="1"
               call_notification_on_offApi("1")
            } else {
                status="0"
                iv_notification_switch.setChecked(false);
                call_notification_on_offApi("0")
            }
        }
        ivBack.setOnClickListener {
            onBackPressed()
        }
        rlChangePassword.setOnClickListener {
            val intent = Intent(this, ChangePassword::class.java)
            startActivity(intent)
        }
        rlAbout.setOnClickListener {
            val intent = Intent(this, PrivacyPolicy::class.java)
            intent.putExtra("key", message1)
            intent.putExtra("type", "1")
            startActivity(intent)
        }
        rlPrivacyPolicy.setOnClickListener {
            val intent = Intent(this, PrivacyPolicy::class.java)
            intent.putExtra("key", message2)
            intent.putExtra("type", "3")
            startActivity(intent)
        }
        rlTermsService.setOnClickListener {
            val intent = Intent(this, PrivacyPolicy::class.java)
            intent.putExtra("key", message3)
            intent.putExtra("type", "2")
            startActivity(intent)
        }
        rlSendFeedback.setOnClickListener {
            val intent = Intent(this, SendFeedback::class.java)
            startActivity(intent)
        }
        rlLogOut.setOnClickListener {
            showPoupLogout()
        }
        rlDeleteAccount.setOnClickListener {
            showPoupupDelete()
        }
        tvWithdrawal.setOnClickListener {
            val intent = Intent(this, WithdrawalActivity::class.java)
            startActivity(intent)
        }
        if( intent.getStringExtra("setting").equals("managePayment")){
            rlManagePayment.visibility=View.GONE
            viewManagment.visibility=View.GONE
        } else {
            rlManagePayment.visibility=View.VISIBLE
            viewManagment.visibility=View.VISIBLE
            rlManagePayment.setOnClickListener {
                val intent = Intent(this, PaymentInfoActivity::class.java)
                startActivity(intent)
            }
        }
        //  clickHandle()
    }
    private fun showPoupupDelete() {
        val logoutDialog = Dialog(context)
        logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        logoutDialog.setContentView(R.layout.dialog_fragment_delete)
        logoutDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        logoutDialog.window!!.setGravity(Gravity.CENTER)
        // addPostDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val tvYes = logoutDialog.findViewById<AppCompatTextView>(R.id.tvYes)
        val tvNo = logoutDialog.findViewById<AppCompatTextView>(R.id.tvNo)
        logoutDialog.setCancelable(true)
        logoutDialog.setCanceledOnTouchOutside(true)
        tvYes.setOnClickListener {
           /* baseViewModel.Delete_account(this, getPrefrence(Constants.user_type,""),true)
            baseViewModel.getCommonResponse().observe(this, this)
*/
            logoutDialog.dismiss()
        }
        tvNo.setOnClickListener {
            logoutDialog.dismiss()
        }
        logoutDialog.show()
    }
    private fun showPoupLogout() {
        val logoutDialog = Dialog(context)
        logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        logoutDialog.setContentView(R.layout.dialog_fragment_logout)
        logoutDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        logoutDialog.window!!.setGravity(Gravity.CENTER)
        // addPostDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btn_Done = logoutDialog.findViewById<Button>(R.id.btnYes)
        val btnNo = logoutDialog.findViewById<Button>(R.id.btnNo)
        logoutDialog.setCancelable(true)
        logoutDialog.setCanceledOnTouchOutside(true)
        btn_Done.setOnClickListener {
            baseViewModel.Logout(this, getPrefrence(Constants.user_type,""),true)
            baseViewModel.getCommonResponse().observe(this, this)

        }
        btnNo.setOnClickListener {
            logoutDialog.dismiss()
        }
        logoutDialog.show()
    }



    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is Commontoall) {
                    clearPrefrences()
                    shared.isLogin = false
                    startActivity(Intent(this, SignUpAs::class.java))
                }
                else if (liveData.data is Commontoall2)
                {
                    Helper.showSuccessToast(this, liveData.data.message)
                    savePrefrence(Constants.notificationStatus,  status)
                }
            }
            Status.ERROR -> {
                if (liveData.error is Commontoall)
                {
                    Helper.showSuccessToast(this, liveData.error.message)
                }
            }
            else -> {
            }
        }
    }
    fun call_notification_on_offApi(status:String) {
        baseViewModel.call_notification_on_offApi(this,  status,  true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
}