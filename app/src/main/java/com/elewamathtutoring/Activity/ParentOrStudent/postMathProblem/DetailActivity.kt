package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.elewamathtutoring.Adapter.ParentOrStudent.DetailAdapter
import com.elewamathtutoring.Adapter.ParentOrStudent.MathProblemAdapter
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var detailAdapter: DetailAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ivBack.setOnClickListener(this)
        rvComments.adapter = DetailAdapter(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
        }
    }
}