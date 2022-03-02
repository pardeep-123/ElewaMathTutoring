package com.elewamathtutoring.Activity.ParentOrStudent.add_card

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.Models.Add_Card.Model_addcards
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.SharedPrefUtil
import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.dialog_booking_thanks.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddCardActivity : AppCompatActivity() ,
    Observer<RestObservable>,View.OnClickListener {
     lateinit var contxt: Context
    lateinit var dialog: Dialog
    @Inject
    lateinit var validator: Validator
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    var current_year: Int = 0
    var future_year = 40
    lateinit var shared: SharedPrefUtil
    var month = ""
    var type_Add_edit=""
    var year = ""
    lateinit var yearArray: Array<String?>
    internal var items = arrayOf<CharSequence>("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        contxt=this
        shared = SharedPrefUtil(this)
        App.getinstance().getmydicomponent().inject(this)
        onClicks()
        ivOn.setOnClickListener(this)
        ivOf.setOnClickListener(this)
        current_year = Calendar.getInstance().get(Calendar.YEAR)
        yearArray = arrayOfNulls<String>(future_year)
        for (i in 0 until future_year) {
            yearArray[i] = (current_year + i).toString()
        }
        if (intent.getStringExtra("type").equals("edit")) {
            type_Add_edit="2"
            year=intent.getStringExtra("expiry_year").toString()
            month=intent.getStringExtra("expiry_month").toString()
            edNameOfCard.setText(intent.getStringExtra("holder_name"))
            etCard.setText(intent.getStringExtra("card_number"))
            etExpDate.setText(intent.getStringExtra("expiry_month").toString() + "/" + intent.getStringExtra("expiry_year").toString())

            btnSubmit.text = "Update"
        }
        else{
            type_Add_edit="1"
            btnSubmit.text = "Submit"
        }

        if(intent.getStringExtra("from").equals("Search"))
        {
            btnSubmit.text  = "CONFIRM PAYMENT"
        }
    }

    private fun onClicks() {
        btnSubmit.setOnClickListener{
            if (intent.getStringExtra("type")!!.equals("edit")) {
                if (validator.addcard(this, edNameOfCard.text.toString(), etCard.text.toString(), etExpDate.text.toString(), edCvv.text.toString()))
                {
                    baseViewModel.editCard(this, etCard.text.toString(), year, month, edNameOfCard.text.toString(), edCvv.text.toString(), "1", type_Add_edit, intent.getStringExtra("card_id").toString(), true)
                    baseViewModel.getCommonResponse().observe(this, this)
                }
            }
            else {
                if (validator.addcard(this, edNameOfCard.text.toString(), etCard.text.toString(), etExpDate.text.toString(), edCvv.text.toString())) {
                    if (ivOf.visibility == View.VISIBLE) {
                        Helper.showErrorAlert(this, "Please select save this card and its details securely to my account")
                    } else {
                        baseViewModel.addcard(
                            this,
                            etCard.text.toString(),
                            year,
                            month,
                            edNameOfCard.text.toString(),
                            edCvv.text.toString(),
                            "1",
                            type_Add_edit,
                            true
                        )
                        baseViewModel.getCommonResponse().observe(this, this)
                    }
                }
            }

        }
        ivBack.setOnClickListener { finish() }
        etExpDate.setOnClickListener {
            openMonth()
        }

    }

    private fun ThanksForBookingDialog() {

        val filterDialog = Dialog(this)
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        filterDialog.setContentView(R.layout.dialog_booking_thanks)

        filterDialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        filterDialog.window!!.setGravity(Gravity.CENTER)
        filterDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        filterDialog.setCancelable(false)
        filterDialog.setCanceledOnTouchOutside(false)

        val colorText =
            Html.fromHtml("We've let Ryan know that you are interested.Once they have confirmed your session, your card on file will be debited for <b> <font color=\"#918CE2\">\$100.00</font> </b")
        filterDialog.edPromoCode.text = (colorText.toString())

        val ss = SpannableString(colorText)

        filterDialog.edPromoCode.text = ss

        filterDialog.btnThanksContinue.setOnClickListener {
            filterDialog.dismiss()
           // startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        filterDialog.show()


    }

    private fun openMonth() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Expiry Month")
        builder.setItems(items, DialogInterface.OnClickListener { dialog, item ->
            // Do something with the selection
            // tv_month.setText(items[item])
            month = (items[item]).toString()
            openYear()
        })
        val alert = builder.create()
        alert.show()

    }

    private fun openYear() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Expiry Year")
        builder.setItems(yearArray, DialogInterface.OnClickListener { dialog, item ->


            val df: DateFormat = SimpleDateFormat("yyyy")
            val momth: DateFormat = SimpleDateFormat("MM")
            val dateyear: String = df.format(Calendar.getInstance().time)
            val datemomth: String = momth.format(Calendar.getInstance().time)
            year = yearArray[item].toString()
            if (year.toString().equals(dateyear))
            {
                if(month.toInt()<datemomth.toInt())
                {
                    Helper.showSuccessToast(this, "Please select valid expiration date!")
                    etExpDate.setText("")
                    year = ""
                }
                else
                {
                    year = yearArray[item].toString()
                    etExpDate.setText(month + "/" + yearArray!![item])
                }
            }
            else
            {
                year = yearArray[item].toString()
                etExpDate.setText(month + "/" + yearArray!![item])
            }
        })
        val alert = builder.create()
        alert.show()
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS ->
            {
                if (liveData.data is AddCardResponse)
                {
                    Helper.showSuccessToast(this, liveData.data.message.toString())
                    if(intent.getStringExtra("from").equals("Search"))
                    {
                       finish()
                    }
                    else
                    {
                       // ThanksDialog()
                        ThanksForBookingDialog()
                    }

                }
            }
            Status.ERROR ->
            {
                if (liveData.error is AddCardResponse)
                    Helper.showSuccessToast(this, liveData.error.toString())
            }
            else -> {}
        }
    }
    fun ThanksDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_booking_thanks)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setGravity(Gravity.CENTER)

        val btnThanksContinue = dialog.findViewById<Button>(R.id.btnThanksContinue)
        btnThanksContinue.setOnClickListener {
           dialog.dismiss()
            startActivity(Intent(this, MainActivity::class.java)
                .putExtra("addCard","booking"))
        }
        dialog.show()
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.ivOn ->{
                ivOn.visibility=View.GONE
                ivOf.visibility=View.VISIBLE
            } R.id.ivOf ->{
                ivOf.visibility=View.GONE
                ivOn.visibility=View.VISIBLE
            }
        }
    }
}