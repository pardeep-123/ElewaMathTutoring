package com.elewamathtutoring.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.ParentOrStudent.addBAnk.AddBankAccountActivity
import com.elewamathtutoring.Adapter.MyBankAccountsAdapter
import com.elewamathtutoring.Models.BankAccountsModel.Model_BankAccount
import com.elewamathtutoring.Models.BankAccountsModel.Body
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable

import com.elewamathtutoring.viewmodel.BaseViewModel
import com.pawskeeper.Modecommon.Commontoall
import com.pawskeeper.Modecommon.Commontoall2
import kotlinx.android.synthetic.main.activity_my_bank_accounts.*
import kotlin.collections.ArrayList

class MyBankAccountsActivity : AppCompatActivity(), Observer<RestObservable> {
    var banksList = ArrayList<Body>()
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bank_accounts)
        onClicks()
    }

    private fun onClicks() {
        ivBack.setOnClickListener { finish() }
        btnAddNewAccount.setOnClickListener {

            val intent = Intent(this, AddBankAccountActivity::class.java)
            intent.putExtra("from", "Add")
            startActivity(intent)
        }
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is Model_BankAccount) {
                    banksList.clear()
                    banksList.addAll(liveData.data.body)
                    rv_myBankAccounts.adapter = MyBankAccountsAdapter(this, banksList, this@MyBankAccountsActivity)

                    if(banksList.size==0)
                    {
                        whennodata.visibility=View.VISIBLE
                    }else
                    {
                        whennodata.visibility=View.GONE
                    }
                } else if (liveData.data is Commontoall) {
                    Helper.showSuccessToast(this, liveData.data.message)
                    api()
                }else if (liveData.data is Commontoall2) {
                    Helper.showSuccessToast(this, liveData.data.message)
                    api()
                }
            }
            Status.ERROR -> {
                if (liveData.error is Model_BankAccount) {
                    Helper.showSuccessToast(this, liveData.error.message)
                }
                if (liveData.error is Commontoall) {
                    Helper.showSuccessToast(this, liveData.error.message)

                }
            }
            else -> {
            }


        }
    }

    fun deleteBank(bank_id: String) {
        baseViewModel.deleteBank(this, bank_id, true)
        baseViewModel.getCommonResponse().observe(this, this)

    }

   fun selectdefualtBank(bank_id: String) {
        baseViewModel.SetDefault_Bank(this, bank_id, true)
        baseViewModel.getCommonResponse().observe(this, this)

    }

   fun selectedbankBank(bank_id: String, accountHolder: String, accountno: String) {
       val returnIntent = Intent()
       returnIntent.putExtra("bank_id",bank_id)
       returnIntent.putExtra("accountHolder", accountHolder)
       returnIntent.putExtra("accountno", accountno)
       setResult(Activity.RESULT_OK, returnIntent)
       finish()
    }

    fun api() {
        baseViewModel.bankAccounts(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    override fun onResume() {
        super.onResume()
        api()
    }
}