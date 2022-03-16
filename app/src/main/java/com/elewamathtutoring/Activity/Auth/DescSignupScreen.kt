package com.elewamathtutoring.Activity.Auth

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.TeacherOrTutor.MainTeacherActivity
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.Models.Login.Model_login
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.constant.Constants.Companion.getImageUriFromBitmap
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.Util.helper.extensions.savePrefrence
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_desc_signup_screen.*
import java.util.*
import java.util.concurrent.ExecutionException

class DescSignupScreen : AppCompatActivity(), Observer<RestObservable> {
    lateinit var ivBack: ImageView
    private var mAlbumFiles: ArrayList<AlbumFile> = ArrayList()
    var firstimage=""
    var byteimagearray = ByteArray(0)
    val context: Context =this
    lateinit var btnNext: Button
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc_signup_screen)
        val w: Window = window
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ivBack = findViewById(R.id.ivBack)
        btnNext = findViewById(R.id.btnConfirmSignUp)
        Constants.scrollEditText(edtDesc)
        ivBack.setOnClickListener {
            onBackPressed()

        }
        if(getPrefrence(Constants.Social_login, "").equals("True"))
        {
            Glide.with(this).load(intent.getStringExtra("image").toString())
                .placeholder(R.drawable.profile_unselected).into(ivProfileSignUp)
            firstimage=intent.getStringExtra("image").toString()
        }
        ivProfileSignUp.setOnClickListener {selectImage() }

        btnNext.setOnClickListener {
            if(firstimage.isEmpty())
            {
                Helper.showErrorAlert(this, context.getString(R.string.error_empty_image))
            }
            else if(edtDesc.text.toString().isEmpty())
            {
                Helper.showErrorAlert(this, "Please tell us what you're looking for.")
            }
            else
            {
                if(getPrefrence(Constants.Social_login, "").equals("True"))
                {
                   // saveBytesArray2(firstimage)
                    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                    StrictMode.setThreadPolicy(policy)
                    val bmp: Bitmap = Constants.getBitmapFromURL(firstimage)!!
                    val uri: Uri = getImageUriFromBitmap(this, bmp)
                    val imagePath = Constants.getPath(this, uri)

                    baseViewModel.ParentSocialSignup(this, imagePath, intent.getStringExtra("socialId").toString(), intent.getStringExtra("Name").toString(), intent.getStringExtra("email").toString(), intent.getStringExtra("socialtype").toString(), edtDesc.text.toString(), true)
                    baseViewModel.getCommonResponse().observe(this, this)
                }
                else
                {
                    baseViewModel.signinUser(this, firstimage, intent.getStringExtra("password").toString(), intent.getStringExtra("Name").toString(), intent.getStringExtra("email").toString(), edtDesc.text.toString(), true)
                    baseViewModel.getCommonResponse().observe(this, this)
                }
            }
        }
    }

    private fun selectImage() {
        Album.image(this).singleChoice().camera(true).columnCount(4).widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivProfileSignUp)
                firstimage = result[0].path
            }.onCancel {
            }.start()
    }


    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS ->
            {
                if (liveData.data is Model_login)
                {
                    Log.e("DEVICETOCKEN", "" + liveData.data.body.token + " dT--" + liveData.data.body.deviceToken)
                    savePrefrence(Constants.AUTH_KEY, liveData.data.body.token)
                    savePrefrence(Constants.USER_ID, liveData.data.body.id.toString())
                    savePrefrence(Constants.notificationStatus, liveData.data.body.notificationStatus.toString())
                    Constants.USER_IDValue = liveData.data.body.id.toString()
                    savePrefrence(Constants.Social_login, "False")
                    savePrefrence(Constants.user_type, liveData.data.body.userType.toString())
                    if (liveData.data.body.userType == 1)
                    {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else
                    {
                        startActivity(Intent(this, MainTeacherActivity::class.java))
                        finish()
                    }
                } else
                {
                }
            }

            Status.ERROR ->
            {
                if (liveData.error is Model_login) Helper.showSuccessToast(this, liveData.error.message)
            }
            else -> {
                if (liveData.error is Model_login)
                    Helper.showSuccessToast(this, liveData.error.message)
            }
        }
    }

    fun saveBytesArray2(imagePath: String?) {
        Thread(Runnable {
            try
            {
                val bytes = Glide.with(this).`as`(ByteArray::class.java).load(imagePath).submit().get()
                byteimagearray = bytes
                Log.e("checkmybitmapaarray", "---" + byteimagearray.toString())
            } catch (e: ExecutionException)
            {
                e.printStackTrace()
            } catch (e: InterruptedException)
            {
                e.printStackTrace()
            }
        }).start()
    }


}