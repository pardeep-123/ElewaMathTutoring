package com.elewamathtutoring.Activity.Chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.R
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_study_group.*
import java.util.ArrayList

class StudyGroupActivity : AppCompatActivity(), View.OnClickListener  {
    var firstimage = ""
    private var mAlbumFiles = ArrayList<AlbumFile>()
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
                selectImage()
            }
            R.id.btnNext -> {
                startActivity(Intent(this, AddParticipantActivity::class.java))
            }
        }
    }
    private fun selectImage() {
        Album.image(this).singleChoice().camera(true).columnCount(4).widget(
            Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build()
        )
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(group_img)
                firstimage = result[0].path
            }.onCancel {
            }.start()
    }

}