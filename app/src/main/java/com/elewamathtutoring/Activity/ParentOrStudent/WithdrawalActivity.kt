package com.elewamathtutoring.Activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.ParentOrStudent.Viewall_trangection
import com.elewamathtutoring.Adapter.ParentOrStudent.TransactionAdapter
import com.elewamathtutoring.Models.BankAccountsModel.Model_BankAccount
import com.elewamathtutoring.Models.Wallet.Body
import com.elewamathtutoring.Models.Wallet.Model_wallet_amount_history
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.pawskeeper.Modecommon.Commontoall
import kotlinx.android.synthetic.main.activity_withdrawal.*
import kotlinx.android.synthetic.main.activity_withdrawal.ivBack
import kotlinx.android.synthetic.main.item_transfer_to_bank.*

class WithdrawalActivity : AppCompatActivity(), Observer<RestObservable> {
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    var wallat_list=ArrayList<Body>()
    var selectedBank_id=""
    var enteredamount=0.0
    var amount=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdrawal)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT;
        onClicks()
        walletapi()
    }

    private fun walletapi() {
        baseViewModel.Get_Wallet(this,  true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
    private fun onClicks() {
        rl_Bank.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AddBankAccountActivity::class.java
                )
            )
        }
        btnWithdrawalNow.setOnClickListener {
            if(tvAmount.text.toString().equals(""))
            {
            }
            else
            {
                enteredamount= tvAmount.text.toString().toFloat().toDouble()
            }
            if(tvAmount.text.toString().equals(""))
            {
                Helper.showErrorAlert(this, "Please enter amount")
            }
            else if(tvAmount.text.toString().equals("")||tvAmount.text.toString().equals("0")||tvAmount.text.toString().equals("0.0"))
            {
                Helper.showErrorAlert(this, "Please enter amount")
            }
            else if(enteredamount>amount)
            {
                Helper.showErrorAlert(this, "Please enter valid amount")
            }
            else if(selectedBank_id.equals("0")||selectedBank_id.equals(""))
            {
                Helper.showErrorAlert(this, "Please select bank account")
            }
            else
            {
                baseViewModel.withdrawlAmount(this, tvAmount.text.toString(),selectedBank_id,true)
                baseViewModel.getCommonResponse().observe(this, this)
            }
        }


        rlSelectBank.setOnClickListener {
            val intent = Intent(this, MyBankAccountsActivity::class.java)
            startActivityForResult(intent, 11)
        }

        tvAddNewBank.setOnClickListener {
            startActivity(Intent(this, AddBankAccountActivity::class.java))
        }

        relative_selectBank.setOnClickListener {
            val intent = Intent(this, MyBankAccountsActivity::class.java)
            startActivityForResult(intent, 11)
        }

        tvViewAll.setOnClickListener {
            intent = Intent(this, Viewall_trangection::class.java)
        intent.putExtra("listof_trangection", wallat_list)
        startActivity(intent)
        }
        ivBack.setOnClickListener { finish() }
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is Model_wallet_amount_history)
                {
                    if(liveData.data.body.TotalAmount.walletAmount.equals(""))
                    {
                        tvBalance.text=Constants.Currency+"0.00"
                    }
                    else
                    {
                        amount=liveData.data.body.TotalAmount.walletAmount.toDouble()
                        tvBalance.text=Constants.Currency+liveData.data.body.TotalAmount.walletAmount
                    }
                    wallat_list.addAll(listOf(liveData.data.body))

                    if(wallat_list.get(0).wallet_history.size==0)
                    {
                        trangectiojnlist.visibility=View.GONE
                    }
                    else
                    {
                        rv_transactionList.adapter=TransactionAdapter(this,wallat_list,"showonly5")
                        trangectiojnlist.visibility=View.VISIBLE
                    }
                    apibankAccounts()
                }
                else if (liveData.data is Model_BankAccount) {

                    if(liveData.data.body.size==0)
                    {
                        relative_selectBank.visibility=View.GONE
                        rl_Bank.visibility=View.VISIBLE
                    }else
                    {
                        relative_selectBank.visibility=View.VISIBLE
                        tvAddNewBank.visibility=View.VISIBLE
                        rl_Bank.visibility=View.GONE
                    }
                }
                else if (liveData.data is Commontoall) {
                finish()
                }
                else
                {
                }
            }

            Status.ERROR -> {
                if (liveData.error is Model_wallet_amount_history)
                    Helper.showSuccessToast(this, liveData.error.message)
            }
            else -> {
            }
        }

    }
    override fun onActivityResult(resrequestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(resrequestCode, resultCode, data)
        if (data != null) {
            bankname.setText(data.getStringExtra("accountHolder").toString())
            selectedBank_id=data.getStringExtra("bank_id").toString()
            val lastFourDigits = data.getStringExtra("accountno").toString().substring(data.getStringExtra("accountno").toString().length - 4)
            tv_accountno.setText("XXXX XXXX XXXX "+lastFourDigits)
            rlSelectBank.visibility=View.VISIBLE
            rl_Bank.visibility=View.GONE
            relative_selectBank.visibility=View.GONE
        }
    }
    fun apibankAccounts() {
        baseViewModel.bankAccounts(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
}