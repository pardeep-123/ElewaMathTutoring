package com.elewamathtutoring.Activity.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfoActivity
import com.elewamathtutoring.R
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_signup_teacher.*

import java.util.ArrayList

class SignupTeacherActivity : AppCompatActivity(), View.OnClickListener {
    private var mAlbumFiles = ArrayList<AlbumFile>()
    private var firstImage = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_teacher)
        ivBack.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        add_img.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext -> {
                startActivity(Intent(this, TeachingInfoActivity::class.java))
            }
            R.id.ivBack -> {
              finish()
            }
            R.id.add_img -> {
                selectImage()
            }
        }
    }
    private fun selectImage() {
        Album.image(this).singleChoice().camera(true).columnCount(4).widget(
            Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build()
        )
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivImage)
                firstImage = result[0].path
            }.onCancel {
            }.start()
    }
}