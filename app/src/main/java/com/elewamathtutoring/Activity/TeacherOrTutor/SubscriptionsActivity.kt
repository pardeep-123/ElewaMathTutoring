package com.elewamathtutoring.Activity.TeacherOrTutor

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.applandeo.materialcalendarview.utils.calendar
import com.elewamathtutoring.Models.Login.Model_login
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.pawskeeper.Modecommon.Commontoall
import com.pawskeeper.Modecommon.Commontoall2
import kotlinx.android.synthetic.main.activity_subscriptions.*
import kotlinx.android.synthetic.main.dialog_payment_successfull.*
import kotlinx.android.synthetic.main.dialog_promo_code.btnContinue
import java.text.SimpleDateFormat
import java.util.*

class SubscriptionsActivity : AppCompatActivity(), View.OnClickListener , Observer<RestObservable> {
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }

    var subscrip_type="1"
    var subscrip_planid="1"
    var subscrip_name="Free"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscriptions)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT;
        sub_free.setOnClickListener(this)
        sub_basic.setOnClickListener(this)
        sub_premium.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        btnChooseThisPlan.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.sub_free -> {
                subscrip_type="1"
                subscrip_name="Free"
                subscrip_planid="4"
                tvFree.setTextColor(ContextCompat.getColor(this, R.color.textcolor))
                tvBasic.setTextColor(ContextCompat.getColor(this, R.color.text))
                tvPreminum.setTextColor(ContextCompat.getColor(this, R.color.text))

                barFree.setBackgroundColor(getColor(R.color.textcolor))
                barBasic.setBackgroundColor(getColor(R.color.text))
                barPremium.setBackgroundColor(getColor(R.color.text))

                tv_nontime.setTextColor(resources.getColor(R.color.lightgrey))
                tv_badge.setTextColor(resources.getColor(R.color.lightgrey))
                tv_addfree.setTextColor(resources.getColor(R.color.lightgrey))


                tv_titlesubscription.setText("Pre-approved \nuniversity students.")
                tv_price.setText(Constants.Currency+"0.00")
            }
            R.id.sub_basic -> {
                subscrip_type="2"
                subscrip_name="Basic"
                subscrip_planid="6"
                tvFree.setTextColor(ContextCompat.getColor(this, R.color.text))
                tvBasic.setTextColor(ContextCompat.getColor(this, R.color.textcolor))
                tvPreminum.setTextColor(ContextCompat.getColor(this, R.color.text))

                barFree.setBackgroundColor(getColor(R.color.text))
                barBasic.setBackgroundColor(getColor(R.color.textcolor))
                barPremium.setBackgroundColor(getColor(R.color.text))

                tv_nontime.setTextColor(resources.getColor(R.color.lightgrey))
                tv_badge.setTextColor(resources.getColor(R.color.lightgrey))
                tv_addfree.setTextColor(resources.getColor(R.color.lightgrey))

                tv_price.setText(Constants.Currency+"9.99")
                tv_titlesubscription.setText("Access to advertise yourself as a tutor.")
            }
            R.id.sub_premium -> {
                subscrip_type="3"
                subscrip_type="Premium"
                subscrip_planid="7"
                tvFree.setTextColor(ContextCompat.getColor(this, R.color.text))
                tvBasic.setTextColor(ContextCompat.getColor(this, R.color.text))
                tvPreminum.setTextColor(ContextCompat.getColor(this, R.color.textcolor))

                barFree.setBackgroundColor(getColor(R.color.text))
                barBasic.setBackgroundColor(getColor(R.color.text))
                barPremium.setBackgroundColor(getColor(R.color.textcolor))

                iv_nontime.setImageResource(R.drawable.icon_awesome_check)
                iv_bages.setImageResource(R.drawable.icon_awesome_check)
                iv_addfree.setImageResource(R.drawable.icon_awesome_check)

                tv_price.setText(Constants.Currency+"16.99")
                relativ_premium.visibility=View.VISIBLE
                tv_titlesubscription.setText("Access to advertise as a\n business or specialty class.")
            }
            R.id.ivBack -> {
                finish()
            }
            R.id.btnChooseThisPlan -> {
                if(subscrip_type.equals("1"))
                {
                    promoDialog()
                }
            }
        }
    }

    private fun promoDialog() {

        val promoDialog = Dialog(this)
        promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        promoDialog.setContentView(R.layout.dialog_promo_code)

        promoDialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)

        promoDialog.window!!.setGravity(Gravity.CENTER)
        promoDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        promoDialog.btnContinue.setOnClickListener {
        if(promoDialog.edPromoCode.text.toString().isEmpty())
        {
            Helper.showSuccessToast(this, "Please enter promo code")
        }
        else
        {
            baseViewModel.promocode_exist(this,promoDialog.edPromoCode.text.toString() , true)
            baseViewModel.getCommonResponse().observe(this, this)
            promoDialog.dismiss()
        }
        }

        promoDialog.setCancelable(true)
        promoDialog.setCanceledOnTouchOutside(true)
        promoDialog.show()
    }

    private fun paymentSuccesfullDialog()
    {
        val paymentDialog = Dialog(this)
        paymentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        paymentDialog.setContentView(R.layout.dialog_payment_successfull)
        paymentDialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        paymentDialog.window!!.setGravity(Gravity.CENTER)
        paymentDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        paymentDialog.setCancelable(true)
        paymentDialog.setCanceledOnTouchOutside(true)

        paymentDialog.btnPaymentContinue.setOnClickListener {
            startActivity(Intent(this, MainTeacherActivity::class.java))
            paymentDialog.dismiss()
        }

        paymentDialog.show()
    }
    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is Commontoall) {
                    if(liveData.data.message.equals("promo code not exist."))
                    {
                        Helper.showSuccessToast(this, "Promo code does'not exists.Please try different one.")
                    }
                    else
                    {
                        calendar.add(Calendar.DAY_OF_MONTH, 28)
                        val date = calendar.time
                        val format = SimpleDateFormat("yyyy-MM-dd")//2021-06-11

                        baseViewModel.buy_plan(this, subscrip_type, tv_price.text.toString().replace(Constants.Currency,""),
                            subscrip_name,"1",subscrip_planid,format.format(date),true)
                        baseViewModel.getCommonResponse().observe(this, this)
                    }
                }
                else if(liveData.data is Commontoall2)
                {
                    paymentSuccesfullDialog()
                }
                else if(liveData.data is Model_login)
                {
                    startActivity(Intent(this, MainTeacherActivity::class.java))
                }
            }
            Status.ERROR -> {
                if (liveData.error is Commontoall)
                {
                    Helper.showSuccessToast(this, liveData.error.message)
                }
                else if (liveData.error is Commontoall2)
                {
                    Helper.showSuccessToast(this, liveData.error.message)
                } else if (liveData.error is Model_login)
                {
                    Helper.showSuccessToast(this, liveData.error.message)
                }
            }
            else -> {
            }
        }
    }

}