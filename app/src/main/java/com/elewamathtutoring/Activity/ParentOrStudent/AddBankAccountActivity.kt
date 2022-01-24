package com.elewamathtutoring.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Models.BankAccountsModel.Body
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.pawskeeper.Modecommon.Commontoall
import kotlinx.android.synthetic.main.activity_add_bank_account.*
import java.util.*
import javax.inject.Inject

class AddBankAccountActivity : AppCompatActivity(), View.OnClickListener,
    Observer<RestObservable> {
    var contxt: Context = this
    var position = 0
    var list = ArrayList<Body>()
    var current_id = ""
    @Inject
    lateinit var validator: Validator
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bank_account)
        App.getinstance().getmydicomponent().inject(this)
        contxt = this
        click()
        if (intent.getStringExtra("from").equals("Edit")) {
            list = (intent.getSerializableExtra("BankList") as java.util.ArrayList<Body>?)!!
            tvHearder.text = "Edit Bank Account"
            btnAddBankAccountSave.text = "Update"
            setBankInfo()
        }
    }
    private fun setBankInfo() {
        position = intent.getStringExtra("position").toString().toInt()
        current_id = list.get(position).id.toString()
        ifscCode.setText(list.get(position).ifscCode.toString())
        branch.setText(list.get(position).bankbranch)
        acNumber.setText(list.get(position).accountNo)
        acHolderName.setText(list.get(position).accountHolder)
        cacNumber.setText(list.get(position).accountNo)
    }
    fun click() {
        btnAddBankAccountSave.setOnClickListener(this)
        ivBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnAddBankAccountSave -> {
                if (validator.addBankValidation(
                        this,
                        ifscCode.text.toString(),
                        branch.text.toString(),
                        acNumber.text.toString(),
                        cacNumber.text.toString(),
                        acHolderName.text.toString()
                    )
                ) {

                    CallApi(current_id)
                } else {
                    Log.e("fnjn", "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
                }
            }
            R.id.ivBack -> {
                onBackPressed()
            }
        }
    }

    private fun CallApi(id: String) {
        baseViewModel.addBank(this, id,
            ifscCode.text.toString().trim(),
            branch.text.toString().trim(),
            acNumber.text.toString().trim(),
            acHolderName.text.toString().trim(),
            intent.getStringExtra("from").toString(),
            true
        )
        baseViewModel.getCommonResponse().observe(this, this)
    }
    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is Commontoall) {
                    Helper.showSuccessToast(this, liveData.data.message.toString())
                    finish()
                }
            }
            Status.ERROR -> {
                if (liveData.error is Commontoall)
                    Helper.showSuccessToast(this, liveData.error.toString())
            }
            else -> {
            }
        }
    }
}