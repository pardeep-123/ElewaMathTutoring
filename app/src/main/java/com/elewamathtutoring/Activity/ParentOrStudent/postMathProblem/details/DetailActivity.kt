package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem.MathProblemListResponse
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem.TeacherProblemResponse
import com.elewamathtutoring.Adapter.ParentOrStudent.DetailAdapter
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*


class DetailActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    lateinit var detailAdapter: DetailAdapter
    var mathProblemListResponse: TeacherProblemResponse.Body? = null

    var list = ArrayList<TeacherProblemResponse.Body>()

    var listComment = ArrayList<CommentListResponse.Body.AllComment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ivBack.setOnClickListener(this)
        ivSendBtn.setOnClickListener(this)

        mathProblemListResponse = intent.getSerializableExtra("comments") as TeacherProblemResponse.Body
        tvDescription.setText(mathProblemListResponse!!.description)
        tvTime.text = Constants.getNotificationTime(mathProblemListResponse!!.createdAt.toLong())

        Glide.with(this).load(Constants.documents_URL+mathProblemListResponse!!.document).placeholder(R.drawable.profile_unselected)
            .into(ivImg)


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

    override fun onResume() {
        super.onResume()
        apiListComments()
    }

    private fun apiAddComments() {
        val hashMap = HashMap<String, String>()
        hashMap["comment"] = etComment.text.toString()
        hashMap["problemId"] = mathProblemListResponse?.id.toString()

        baseViewModel.AddComments(this, hashMap, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    private fun apiListComments() {
        baseViewModel.get_comments(this, true, mathProblemListResponse?.id.toString())
        baseViewModel.getCommonResponse().observe(this, this)
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is CommentListResponse) {
                    listComment.clear()
                    listComment.addAll(liveData.data.body.allComments)
                    rvComments.adapter = DetailAdapter(this, listComment)

                }
                if (liveData.data is AddCommentResponse) {
                    etComment.text.clear()
                    apiListComments()
                }
            }
            Status.ERROR -> {
                if (liveData.error is MathProblemListResponse) {

                }
            }
        }
    }
}