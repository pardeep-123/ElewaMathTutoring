package com.elewamathtutoring.Activity.TeacherOrTutor.editProfile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.TeacherOrTutor.edit.EditResponse
import com.elewamathtutoring.Models.Login.Body
import com.elewamathtutoring.Models.Login.Model_login
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.Util.constant.Constants.Companion.scrollEditText
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_about_you.*

import java.util.*
import javax.inject.Inject


class AboutYouActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    private var mAlbumFiles: ArrayList<AlbumFile> = ArrayList()
    var firstimage = ""
    var oldImage = ""
    var profilelist = ArrayList<EditResponse.Body>()
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    @Inject
    lateinit var validator: Validator
    val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_you)
        App.getinstance().getmydicomponent().inject(this)
        ivBack.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        rlPick.setOnClickListener(this)
        scrollEditText(edaboutyou)
        scrollEditText(edTeachingHistory)

        if (intent.getSerializableExtra("list_model") != null) {
            profilelist = (intent.getSerializableExtra("list_model") as ArrayList<EditResponse.Body>?)!!
            text_teacher_name.setText(profilelist[0].name.toString())
            edaboutyou.setText(profilelist[0].about.toString())
            edTeachingHistory.setText(profilelist[0].teachingHistory.toString())
            oldImage = intent.getStringExtra("image").toString()
            Glide.with(context).load(profilelist[0].image)
                .placeholder(R.drawable.profile_unselected).into(ivProfileDesc)
            //  btnNext.text="SAVE"
        } else {

            text_teacher_name.setText(intent.getStringExtra("name").toString())
            edaboutyou.setText(intent.getStringExtra("about").toString())
            edTeachingHistory.setText(intent.getStringExtra("teachingHistory").toString())
            oldImage = intent.getStringExtra("image").toString()
            Glide.with(this).load(intent.getStringExtra("image").toString())
                .placeholder(R.drawable.placeholder_image).into(ivProfileDesc)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rlPick -> {
                selectImage()
            }
            R.id.btnNext -> {
                api()
            }
            R.id.ivBack -> {
                finish()
            }
        }
    }

    private fun api() {
        baseViewModel.EditTeacherBasicProfile(
            this,
            firstimage,
            text_teacher_name.text.toString(),
            edaboutyou.text.toString(),
            edTeachingHistory.text.toString(),
            true
        )
        baseViewModel.getCommonResponse().observe(this, this)
    }

    private fun selectImage() {
        Album.image(this).singleChoice().camera(true).columnCount(4).widget(
            Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build()
        )
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivProfileDesc)
                firstimage = result[0].path
            }.onCancel {
            }.start()
    }

    /**
     * Called when the data is changed.
     * @param t  The new data
     */
    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is EditTeacherProfileResponse) {
                    Helper.showSuccessToast(this, liveData.data.message)
                    finish()
                }
            }
            Status.ERROR -> {
                if (liveData.error is EditTeacherProfileResponse)
                    Helper.showSuccessToast(this, liveData.error.message)
            }
            else -> {
            }
        }
    }


}