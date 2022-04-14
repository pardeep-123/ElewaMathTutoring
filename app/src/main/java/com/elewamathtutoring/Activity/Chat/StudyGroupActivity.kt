package com.elewamathtutoring.Activity.Chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.rideandserve.utils.ImagePickerUtility
import com.bumptech.glide.Glide
import com.elewamathtutoring.*
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_study_group.*
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class StudyGroupActivity : ImagePickerUtility(), View.OnClickListener , Observer<RestObservable> {
    var firstimage = ""
    var extension = ""
    var image = ""
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }

    override fun selectedImage(imagePath: String?, code: Int) {
        Glide.with(this).load(imagePath).into(group_img)

       /* if (imagePath != null) {
            baseViewModel.imageUpload(this, imagePath,true)
        }
        baseViewModel.getCommonResponse().observe(this, this)*/
        try {
            extension =
                imagePath?.substring(imagePath.lastIndexOf(".") + 1).toString() // Without dot jpg, png
        } catch (e: Exception) {
        }
      //  image = getBase64FromPath(imagePath!!)
        image = newRetrun(imagePath!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_group)
        btnNext.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        group_img.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.group_img -> {
              //  selectImage()
                getImage(this,0)
            }
            R.id.btnNext -> {
                when {
                    image == "" -> {
                        showToast("Choose Image First")
                    }
                    etGroupName.text.toString().isEmpty() -> {
                        showToast("Enter Group Name")
                    }
                    else -> {
                        startActivity(Intent(this, AddParticipantActivity::class.java)
                            .putExtra("extension",extension)
                            .putExtra("groupName",etGroupName.text.toString().trim())
                            .putExtra("image",image))
                    }
                }
            }
        }
    }
    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is UploadImageResponse) {
                    // send image as bit 64 in socket
                    var imageList=t.data.body.image
                }
            }
            Status.ERROR -> {
                if (t.error is UploadImageResponse)
                    Helper.showSuccessToast(this, t.error.message)
            }
        }
    }
}