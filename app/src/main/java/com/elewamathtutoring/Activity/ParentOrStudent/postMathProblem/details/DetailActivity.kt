package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem.MathProblemListResponse
import com.elewamathtutoring.Adapter.ParentOrStudent.DetailAdapter
import com.elewamathtutoring.R
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    lateinit var detailAdapter: DetailAdapter
    var id = ""
    var list = ArrayList<MathProblemListResponse.Body>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ivBack.setOnClickListener(this)
        ivSendBtn.setOnClickListener(this)
        rvComments.adapter = DetailAdapter(this)
  //      id= getString(list[0].id.toString())
    }



    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.ivSendBtn -> {
                apiAddComments()
            }
        }
    }
    private fun apiAddComments() {
        baseViewModel.AddComments(this, id, etComment.text.toString(), true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
  /*  private fun apiListComments() {
        baseViewModel.get_comments(this, id, etComment.text.toString(), true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
*/
    override fun onChanged(it: RestObservable?) {
        when (it!!.status) {
            Status.SUCCESS -> {
                if (it.data is AddCommentResponse) {
                    finish()
                }
            }
            Status.ERROR -> {
                if (it.error is AddCommentResponse) {

                }
            }
        }
    }
}