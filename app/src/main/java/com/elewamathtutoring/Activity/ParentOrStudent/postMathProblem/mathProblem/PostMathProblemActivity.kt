package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.EditMathProblemActivity
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.VideoPlayActivity
import com.elewamathtutoring.Adapter.ParentOrStudent.MathProblemAdapter
import com.elewamathtutoring.BuildConfig
import com.elewamathtutoring.Models.Modecommon.Commontoall
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.CommonMethods.applyDim
import com.elewamathtutoring.Util.CommonMethods.clearDim
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.halilibo.bvpkotlin.BetterVideoPlayer
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import kotlinx.android.synthetic.main.activity_post_math_problem.*
import kotlinx.android.synthetic.main.dialog_delete_post.*
import kotlinx.android.synthetic.main.popup_file_attachment.view.*


class PostMathProblemActivity : AppCompatActivity(), View.OnClickListener,
    MathProblemAdapter.OnClickMath,
    Observer<RestObservable> {
    var myPopupWindow: PopupWindow? = null
    lateinit var context: Context
    lateinit var mathProblemAdapter: MathProblemAdapter

    lateinit var dialog: Dialog
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage = ""
    var type = "1"
    var list = ArrayList<TeacherProblemResponse.Body>()
    var pos = -1
    private val fileRequestCode = 202
    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            if (permissions.isNotEmpty()) {
                permissions.entries.forEach {
                    Log.d("permissions", "${it.key} = ${it.value}")
                }
                val readStorage = permissions[Manifest.permission.READ_EXTERNAL_STORAGE]
                val writeStorage = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE]
                val camera = permissions[Manifest.permission.CAMERA]

                if (readStorage == true && writeStorage == true && camera == true) {
                    Log.e("permissions", "Permission Granted Successfully")
                    pdfDialog()
                } else {
                    Log.e("permissions", "Permission not granted")
                    checkPermissionDenied(permissions.keys.first())
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_math_problem)
        context = this

        ivBack.setOnClickListener(this)
        ivFiles.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            if (!Environment.isExternalStorageManager()) {
//                askStorageManagerPermission()
//            }
//        }
        if (intent.getStringExtra("tutor").equals("postProblem")) {
            rlSearch.visibility = View.GONE
            llSubmit.visibility = View.GONE
            mathProblemAdapter = MathProblemAdapter(this, this, list, "1")
            rvMathProblem.adapter = mathProblemAdapter
            apiTeacherListProblems()
        } else {
            rlSearch.visibility = View.VISIBLE
            llSubmit.visibility = View.VISIBLE
            mathProblemAdapter = MathProblemAdapter(this, this, list, "2")
            rvMathProblem.adapter = mathProblemAdapter
            apiListProblems()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.ivFiles -> {
                setPopUpFiles()
                llImages.visibility = View.VISIBLE
            }
            R.id.btnSubmit -> {
                apiAddProblems()
            }
        }
    }

    //1=images, 2=videos, 3=others
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
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getImage()
            } else {
                requestPermission()
//                Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
            }
            ////////

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
                type = "1"
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
                type = "2"
            }.onCancel {
            }.start()
    }

    private fun apiListProblems() {
        baseViewModel.myMathProblems(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    private fun apiTeacherListProblems() {
        baseViewModel.mathProblems(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    private fun apiAddProblems() {
        baseViewModel.postMathProblem(this, firstimage, edtSearch.text.toString(), type, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is TeacherProblemResponse) {
                    list.clear()
                    list.addAll(liveData.data.body)
                    mathProblemAdapter.notifyDataSetChanged()
                    //  rvMathProblem.adapter = MathProblemAdapter(this, this, list)
                } else if (liveData.data is Commontoall) {
                    //finish()
                    list.removeAt(pos)
                    mathProblemAdapter.notifyDataSetChanged()
                } else if (liveData.data is AddPostResponse) {
                    edtSearch.text.clear()
                    llImages.visibility = View.GONE
                    apiListProblems()
                }
            }
            Status.ERROR -> {
                if (liveData.error is TeacherProblemResponse) {

                }
            }
        }
    }

    private fun deletePostDialog(id: String, position: Int) {
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
            pos = position
            filterDialog.dismiss()
            deletePostMathProblem(id)
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

    private fun deletePostMathProblem(id: String) {
        baseViewModel.deletePostMathProblem(this, id, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    override fun onClickDelete(deleteId: String, position: Int) {
        deletePostDialog(deleteId, position)
    }

    override fun onClickEdit(editId: TeacherProblemResponse.Body) {
        startActivity(
            Intent(this, EditMathProblemActivity::class.java)
                /*.putExtra("edit", list[pos].id.toString())*/
                .putExtra("edit", editId)
        )
    }

    override fun onCLickViewPost(type: TeacherProblemResponse.Body) {
      val intent = Intent(this, VideoPlayActivity::class.java)
        intent.putExtra("teacherProblem",type)
        startActivity(intent)
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
                    /**
                     * try to set pdf into imageview
                     */
//                    Glide.with(this).load(R.drawable.ic_pdf).into(ivAttachment)
                    ivAttachment.setImageResource(R.drawable.ic_pdf)
                    firstimage = pdfPath
                    type = "3"

                }
            } catch (e: Exception) {
            }
        }
    }

    private fun getImage() {

        if (hasPermissions(permissions)) {
            Log.e("Permissions", "Permissions Granted")
            pdfDialog()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            checkPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            checkPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            checkPermissionDenied(Manifest.permission.CAMERA)
        } else {
            Log.e("Permissions", "Request for Permissions")
            requestPermission()
        }
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    private fun hasPermissions(permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(this!!, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkPermissionDenied(permissions: String) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            val mBuilder = AlertDialog.Builder(this)
            val dialog: AlertDialog =
                mBuilder.setTitle(R.string.alert).setMessage(R.string.permissionRequired)
                    .setPositiveButton(
                        R.string.ok
                    ) { dialog, which -> requestPermission() }
                    .setNegativeButton(
                        R.string.cancel
                    ) { dialog, which ->

                    }.create()
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(
                        this!!, R.color.colorPrimary
                    )
                )
            }
            dialog.show()
        } else {
            val builder = AlertDialog.Builder(this)
            val dialog: AlertDialog =
                builder.setTitle(R.string.alert).setMessage(R.string.permissionRequired)
                    .setCancelable(
                        false
                    )
                    .setPositiveButton(R.string.openSettings) { dialog, which ->
                        //finish()
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts(
                                "package",
                                BuildConfig.APPLICATION_ID + ".provider",
                                null
                            )
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }.create()
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(
                        this, R.color.colorPrimary
                    )
                )
            }
            dialog.show()
        }
    }

    private fun requestPermission() {
        requestMultiplePermissions.launch(permissions)
    }

    fun showFullScreen(arr: String, type: String) {
        val dialog = Dialog(this, android.R.style.Theme_Light)
        dialog.setCanceledOnTouchOutside(true)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_full_screen)
        dialog.window?.setBackgroundDrawableResource(R.color.app);

        if (type == "1") {
            val image = dialog.findViewById<View>(R.id.ivShow) as ImageView
            Glide.with(this).load(arr).placeholder(R.drawable.placeholder_image).into(image)
        } else {
            var player = dialog.findViewById<BetterVideoPlayer>(R.id.player)
            player.setSource(Uri.parse(arr))
            player.setAutoPlay(true)
        }
        val cutIv = dialog.findViewById<ImageView>(R.id.cutIv) as ImageView
        cutIv.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    /**
     * @author Pardeep Sharma
     * set new Permissions for android 11 to read files data
     */
    private fun askStorageManagerPermission() {
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
            }
        }
    }
}


