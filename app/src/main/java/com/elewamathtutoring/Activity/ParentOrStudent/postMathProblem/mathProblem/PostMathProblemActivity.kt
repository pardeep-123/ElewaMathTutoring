package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.EditMathProblemActivity
import com.elewamathtutoring.Adapter.ClickCallBack
import com.elewamathtutoring.Adapter.ParentOrStudent.MathProblemAdapter
import com.elewamathtutoring.Models.Modecommon.Commontoall
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.CommonMethods.applyDim
import com.elewamathtutoring.Util.CommonMethods.clearDim
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_post_math_problem.*
import kotlinx.android.synthetic.main.activity_post_math_problem.ivBack
import kotlinx.android.synthetic.main.dialog_delete_post.*
import kotlinx.android.synthetic.main.popup_file_attachment.view.*


class PostMathProblemActivity : AppCompatActivity(), View.OnClickListener, ClickCallBack,
    Observer<RestObservable> {
    var myPopupWindow: PopupWindow? = null
    lateinit var context: Context
    lateinit var mathProblemAdapter: MathProblemAdapter
    lateinit var dialog: Dialog
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage = ""
    var oldImage = ""
    var list = ArrayList<MathProblemListResponse.Body>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_math_problem)
        context = this
        ivBack.setOnClickListener(this)
        ivFiles.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
        //   rvMathProblem.adapter = MathProblemAdapter(this,this)
    }

    override fun onResume() {
        super.onResume()
        apiListProblems()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.ivFiles -> {
                setPopUpFiles()
                llImages.visibility=View.VISIBLE
            }
            R.id.btnSubmit -> {
                apiAddProblems()
            }
        }
    }
    override fun onItemClick(pos: Int, value: String) {
        when (value) {
            "delete" -> {
                deletePostDialog()
            }
            "edit" -> {
                startActivity(
                    Intent(this, EditMathProblemActivity::class.java)
                        .putExtra("edit", list[pos].id.toString())
                )
            }
        }
    }
    private fun setPopUpFiles() {
        val viewGroup: ViewGroup = window.decorView.rootView as ViewGroup
        val inflater =
            applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_file_attachment, null)
        myPopupWindow = PopupWindow(
            view,
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            true
        )
        ivFiles.setOnClickListener {
            myPopupWindow?.showAsDropDown(it, -0, -35)
            applyDim(viewGroup, 0.5f)
        }
        view.ivCamera.setOnClickListener {
            selectImage()
            myPopupWindow!!.dismiss()
        }
        view.ivVideo.setOnClickListener {
            selectVideo()
            myPopupWindow!!.dismiss()
        }
        view.ivFile.setOnClickListener {
            myPopupWindow!!.dismiss()
        }
        myPopupWindow?.setOnDismissListener {
            clearDim(viewGroup)
        }
    }
    private fun selectImage() {
        Album.image(this).singleChoice().camera(true).columnCount(4).widget(
            Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build()
        )
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivAttachment)
                firstimage = result[0].path
            }.onCancel {
            }.start()
    }
    private fun selectVideo() {
        Album.video(this).singleChoice().camera(true).columnCount(4).widget(
            Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build()
        )
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivAttachment)
                firstimage = result[0].path
            }.onCancel {
            }.start()
    }
    private fun apiListProblems() {
        baseViewModel.myMathProblems(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
    private fun apiAddProblems() {
        baseViewModel.postMathProblem(this,firstimage,edtSearch.text.toString() ,true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is MathProblemListResponse) {
                    list.clear()
                    list.addAll(liveData.data.body)
                    rvMathProblem.adapter = MathProblemAdapter(this, this, list)
                }
                if (liveData.data is Commontoall) {
                    finish()
                }
            }

            Status.ERROR -> {
                if (liveData.error is MathProblemListResponse) {

                }
            }
        }
    }

    private fun deletePostDialog() {
        val filterDialog = Dialog(this)
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        filterDialog.setContentView(R.layout.dialog_delete_post)
        filterDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        filterDialog.window!!.setGravity(Gravity.CENTER)
        filterDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        filterDialog.setCancelable(false)
        filterDialog.setCanceledOnTouchOutside(true)

        filterDialog.btnYes.setOnClickListener {
            filterDialog.dismiss()
            deletePostMathProblem(list[0].id.toString())
            // startActivity(Intent(this, MainActivity::class.java))

        }

        filterDialog.btnNo.setOnClickListener {
            filterDialog.dismiss()
            filterDialog.setCancelable(true)
            // startActivity(Intent(this, MainActivity::class.java))

            // finish()
        }
        filterDialog.show()
    }

    fun deletePostMathProblem(id: String) {
        baseViewModel.deletePostMathProblem(this, id, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }


}