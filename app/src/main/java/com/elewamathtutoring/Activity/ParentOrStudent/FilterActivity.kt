package com.elewamathtutoring.Activity.ParentOrStudent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.Activity.ParentOrStudent.settings.SettingActivity
import com.elewamathtutoring.Adapter.ParentOrStudent.FilterAdapter
import com.elewamathtutoring.Adapter.ParentOrStudent.NotificationsAdapter
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.activity_filter.ivBack
import kotlinx.android.synthetic.main.activity_notifications.*

class FilterActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        ivBack.setOnClickListener(this)
        rvFilter.adapter=FilterAdapter(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
              finish()
            }
        }
    }
}