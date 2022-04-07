package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem.MathProblemListResponse
import com.elewamathtutoring.Adapter.ParentOrStudent.MathProblemAdapter
import com.elewamathtutoring.Models.Modecommon.Commontoall
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.CommonMethods
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import kotlinx.android.synthetic.main.activity_edit_math_problem.*
import kotlinx.android.synthetic.main.activity_edit_math_problem.btnSubmit
import kotlinx.android.synthetic.main.activity_edit_math_problem.ivBack
import kotlinx.android.synthetic.main.activity_post_math_problem.*
import kotlinx.android.synthetic.main.popup_file_attachment.view.*


class EditMathProblemActivity : AppCompatActivity(), View.OnClickListener,
    Observer<RestObservable> {
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }

    var data: MathProblemListResponse.Body? = null
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var postId = ""
    var firstimage = ""
    var oldImage = ""
    var type = ""
    private val fileRequestCode = 202
    var myPopupWindow: PopupWindow? = null

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
            oldImage = data!!.document
            Glide.with(this).load(Constants.documents_URL+data!!.document)
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
                setPopUpFiles()
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
                type="1"
            }.onCancel {
            }.start()
    }
    private fun selectVideo() {
        Album.video(this).singleChoice().camera(true).columnCount(4).widget(
            Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivAddImage)
                firstimage = result[0].path
                type = "2"
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
        ivCamera.setOnClickListener {
            myPopupWindow?.showAsDropDown(it, -0, -35)
            CommonMethods.applyDim(viewGroup, 0.5f)
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
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                pdfDialog()
            }else {
                Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
            }
            myPopupWindow!!.dismiss()
        }
        myPopupWindow?.setOnDismissListener {
            CommonMethods.clearDim(viewGroup)
        }
    }
    private fun pdfDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.addCategory("android.intent.category.DEFAULT")
                    intent.data =
                        Uri.parse(String.format("package:%s", applicationContext.packageName))
                    startActivityForResult(intent, 456)
                } catch (e: java.lang.Exception) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    startActivityForResult(intent, 456)
                }
            } else {
                filePicker()
            }

        } else {
            filePicker()
        }

    }
    private fun filePicker() {
//        val docs = arrayOf("pdf","doc", "docx", "dot", "dotx")
        FilePickerBuilder.instance
            .setMaxCount(1) //optional
            .setActivityTheme(R.style.LibAppTheme) //optional
            .pickFile(this, fileRequestCode)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileRequestCode && resultCode == RESULT_OK) {
            try {
                if (data != null) {
                    val docPaths: java.util.ArrayList<String> = java.util.ArrayList()
                    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS)!!)
                    val pdfPath = docPaths[0]
                    Log.d("PATHGET_file", pdfPath)
                    firstimage = pdfPath
                    type = "3"

                }
            } catch (e: Exception) {
            }
        }
    }

}