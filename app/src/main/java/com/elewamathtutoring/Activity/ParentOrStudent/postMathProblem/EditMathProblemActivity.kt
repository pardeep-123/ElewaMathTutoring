package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem.MathProblemListResponse
import com.elewamathtutoring.Adapter.ParentOrStudent.MathProblemAdapter
import com.elewamathtutoring.Models.Modecommon.Commontoall
import com.elewamathtutoring.R
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_edit_math_problem.*
import kotlinx.android.synthetic.main.activity_edit_math_problem.ivBack



class EditMathProblemActivity : AppCompatActivity(), View.OnClickListener,
    Observer<RestObservable> {
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    var data: MathProblemListResponse.Body? = null
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var postId = ""
    var firstimage = ""
    var oldImage = ""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_math_problem)
        ivBack.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
        ivCamera.setOnClickListener(this)

        if (intent.getSerializableExtra("edit") != null) {

            data = (intent.getSerializableExtra("edit") as MathProblemListResponse.Body?)
            postId = data!!.id.toString()
            edaboutyou.setText(data!!.description)
            oldImage = intent.getStringExtra("edit").toString()
            Glide.with(this).load(intent.getStringExtra("edit").toString())
                .placeholder(R.drawable.placeholder_image).into(ivAddImage)
        }

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.btnSubmit -> {
               apiEditListProblems()
            }
            R.id.ivCamera -> {
                selectImage()
            }
        }
    }
    private fun selectImage() {
        Album.image(this).singleChoice().camera(true).columnCount(4).widget(
            Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivAddImage)
                firstimage = result[0].path
            }.onCancel {
            }.start()
    }
    private fun apiEditListProblems() {
        baseViewModel.editMyMathProblems(this, firstimage,edaboutyou.text.toString(),postId,true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

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