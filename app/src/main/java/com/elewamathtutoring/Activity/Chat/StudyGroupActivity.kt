package com.elewamathtutoring.Activity.Chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.rideandserve.utils.ImagePickerUtility
import com.bumptech.glide.Glide
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.R
import com.elewamathtutoring.getBase64FromPath
import com.elewamathtutoring.showToast
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_study_group.*
import java.util.ArrayList

class StudyGroupActivity : ImagePickerUtility(), View.OnClickListener  {
    var firstimage = ""
    var extension = ""
    var image = ""

    override fun selectedImage(imagePath: String?, code: Int) {
        Glide.with(this).load(imagePath).into(group_img)
        try {
            extension =
                imagePath?.substring(imagePath.lastIndexOf(".") + 1).toString() // Without dot jpg, png
        } catch (e: Exception) {
        }
        image = getBase64FromPath(imagePath!!)
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

}