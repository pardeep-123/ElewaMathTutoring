package com.elewamathtutoring.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.elewamathtutoring.R

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var ivLogo:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ivLogo.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
       when(p0?.id){
           R.id.ivLogo->{
               startActivity(Intent(this, SplashActivity::class.java))
           }
       }
    }
}