package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem.MathProblemListResponse
import com.elewamathtutoring.Adapter.ParentOrStudent.MathProblemAdapter
import com.elewamathtutoring.Models.Modecommon.Commontoall
import com.elewamathtutoring.R
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_edit_math_problem.*
import kotlinx.android.synthetic.main.activity_edit_math_problem.ivBack
import kotlinx.android.synthetic.main.activity_post_math_problem.*

class EditMathProblemActivity : AppCompatActivity(), View.OnClickListener,
    Observer<RestObservable> {
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_math_problem)
        ivBack.setOnClickListener(this)
        ivBack.setOnClickListener(this)
/*        if (intent.getSerializableExtra("data") != null) {
            data = (intent.getSerializableExtra("data") as HomeResponse.Body.Product?)
            productId = data!!.id.toString()
            etProductName.setText(data!!.name)
            }*/
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.btnSubmit -> {
             //   apiEditListProblems()
            }
        }
    }
   /* private fun apiEditListProblems() {
        baseViewModel.editMyMathProblems(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }*/

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is Commontoall) {
                  finish()
                }
            }
            Status.ERROR -> {
                if (liveData.error is Commontoall) {

                }
            }
        }
    }
}