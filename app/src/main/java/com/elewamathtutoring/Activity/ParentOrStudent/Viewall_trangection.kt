package com.elewamathtutoring.Activity.ParentOrStudent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.elewamathtutoring.Adapter.ParentOrStudent.TransactionAdapter
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_withdrawal.*

class Viewall_trangection : AppCompatActivity() {
    var list=ArrayList<com.elewamathtutoring.Activity.ParentOrStudent.wallet.response.Body>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewall_trangection)
        ivBack.setOnClickListener {
            finish()
        }
        list = (intent.getSerializableExtra("listof_trangection") as java.util.ArrayList<com.elewamathtutoring.Activity.ParentOrStudent.wallet.response.Body>?)!!
        rv_transactionList.adapter= TransactionAdapter(this,list,"showall")

    }
}