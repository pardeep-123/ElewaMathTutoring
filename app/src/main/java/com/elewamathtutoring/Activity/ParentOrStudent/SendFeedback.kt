package com.elewamathtutoring.Activity

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.pawskeeper.Modecommon.Commontoall
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_send_feedback.*
import kotlinx.android.synthetic.main.activity_send_feedback.ivBack

import java.util.ArrayList
import javax.inject.Inject

class SendFeedback : AppCompatActivity(),View.OnClickListener, Observer<RestObservable> {
    @Inject
    lateinit var validator: Validator
    val context: Context = this
    private var mAlbumFiles: ArrayList<AlbumFile> = ArrayList()
    var firstimage=""
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_feedback)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT;
        Constants.scrollEditText(cs_message)
        App.getinstance().getmydicomponent().inject(this)

        ivBack.setOnClickListener(this)
        //ivProfileSignUp.setOnClickListener(this)
        btnSendFeedBack.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivBack -> {
                onBackPressed()
            }
           /* R.id.ivProfileSignUp -> {
                selectImage()
            }*/
            R.id.btnSendFeedBack -> {
                if (validator.contactUs(this, firstimage, cs_message.text.toString())) {
                    api()
                }
            }
        }
    }

    fun api()
    {
        baseViewModel.Contact_us(this, firstimage, cs_message.text.toString(), true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is Commontoall) {
                    Helper.showSuccessToast(this, liveData.data.message)
                    finish()
                }
            }
            Status.ERROR -> {
                if (liveData.error is Commontoall)
                    Helper.showSuccessToast(this, liveData.error.message)
            }
            else -> {
            }
        }
    }

   /* private fun selectImage() {
        Album.image(this).singleChoice().camera(true).columnCount(4).widget(
            Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build()
        )
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivProfileSignUp)
                firstimage = result[0].path
            }.onCancel {
            }.start()
    }*/

}