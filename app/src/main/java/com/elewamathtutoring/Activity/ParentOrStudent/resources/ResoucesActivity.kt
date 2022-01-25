package com.elewamathtutoring.Activity.ParentOrStudent.resources

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elewamathtutoring.Adapter.ParentOrStudent.MathChatAdapter
import com.elewamathtutoring.Adapter.ParentOrStudent.ResourcesAdapter
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_math_chat.*
import kotlinx.android.synthetic.main.activity_resouces.*
import kotlinx.android.synthetic.main.activity_resouces.ivBack

class ResoucesActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var resourcesAdapter: ResourcesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resouces)
        ivBack.setOnClickListener(this)

        resourcesAdapter = ResourcesAdapter(this)
        rvResources.adapter = resourcesAdapter

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            /*R.id.ivCut -> {
                finish()
                // startActivity(Intent(this, VideoCallActivity::class.java))
            }*/
        }
    }
}