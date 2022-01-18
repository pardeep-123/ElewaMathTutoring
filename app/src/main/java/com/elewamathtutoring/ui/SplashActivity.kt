package com.elewamathtutoring.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        btnNext.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnNext->{
                startActivity(Intent(this, SplashActivity::class.java))
            }
        }
    }
}