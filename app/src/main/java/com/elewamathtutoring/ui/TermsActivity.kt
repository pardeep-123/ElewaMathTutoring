package com.elewamathtutoring.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.toolbar.*

class TermsActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)
        ivBack.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
        }
    }
}