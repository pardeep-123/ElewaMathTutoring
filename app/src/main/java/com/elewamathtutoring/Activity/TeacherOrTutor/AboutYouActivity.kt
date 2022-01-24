package com.elewamathtutoring.Activity.TeacherOrTutor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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
import kotlinx.android.synthetic.main.activity_about_you.text_teacher_name
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile_tab.*
import java.util.*
import javax.inject.Inject


class AboutYouActivity : AppCompatActivity(), View.OnClickListener  , Observer<RestObservable> {
    private var mAlbumFiles: ArrayList<AlbumFile> = ArrayList()
    var firstimage=""
    var profilelist=ArrayList<Body>()
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    @Inject
    lateinit var validator: Validator
    val context:Context=this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_you)
        App.getinstance().getmydicomponent().inject(this)

        ivBack.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        rlPick.setOnClickListener(this)
        scrollEditText(edaboutyou)
        scrollEditText(edTeachingHistory)

        if(intent.getStringExtra("key")!!.equals("signup"))
        {
            tvname.visibility=View.GONE
            relat_name.visibility=View.GONE
            try {
                Glide.with(this).load(intent.getStringExtra("image").toString())
                    .placeholder(R.drawable.profile_unselected).into(ivProfileDesc)
                firstimage=intent.getStringExtra("image").toString()
            }
            catch (e:Exception)
            {
            }
        }
        else
        {
            profilelist = (intent.getSerializableExtra("list_model") as ArrayList<Body>?)!!
            text_teacher_name.setText(profilelist.get(0).name.toString())
            edaboutyou.setText(profilelist.get(0).about.toString())
            edTeachingHistory.setText(profilelist.get(0).teachingHistory.toString())
            Glide.with(context)
                .load(profilelist.get(0).image)
                .placeholder(R.drawable.profile_unselected)
                .into(ivProfileDesc)
            btnNext.text="SAVE"
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rlPick -> {
                selectImage()
            }
            R.id.btnNext -> {
                if (intent.getStringExtra("key").equals("signup")) {
                    if (validator.Teacheraboutus(this, firstimage, edaboutyou.text.toString(),
                            edTeachingHistory.text.toString(), text_teacher_name.text.toString(), "add")) {
                        val password = intent.getStringExtra("password").toString()
                        val email = intent.getStringExtra("email").toString()
                        val Name = intent.getStringExtra("Name").toString()

                        val intent = Intent(this, TeachingInfoActivity::class.java)
                        intent.putExtra("key", "signup")
                        intent.putExtra("password", password)
                        intent.putExtra("email", email)
                        intent.putExtra("Name", Name)
                        intent.putExtra("Image", firstimage)
                        intent.putExtra("socialId", intent.getStringExtra("socialId").toString())
                        intent.putExtra("socialtype", intent.getStringExtra("socialtype").toString())
                        intent.putExtra("about_teacher", edaboutyou.text.toString())
                        intent.putExtra("teacher_history", edTeachingHistory.text.toString())
                        startActivity(intent)
                    }
                }
                else {
                    if (validator.Teacheraboutus(this, firstimage, edaboutyou.text.toString(), edTeachingHistory.text.toString(), text_teacher_name.text.toString(), "edit")) {
                        baseViewModel.EditTeacherBasicProfile(
                            this,
                            firstimage,
                            edTeachingHistory.text.toString(),
                            text_teacher_name.text.toString(),
                            edaboutyou.text.toString(),
                            true
                        )
                        baseViewModel.getCommonResponse().observe(this, this)
                    }
                }
            }
            R.id.ivBack -> {
                finish()
            }
        }
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
                if (liveData.data is Model_login) {
                    Helper.showSuccessToast(this, liveData.data.message)
                    finish()
                }
            }
            Status.ERROR -> {
                if (liveData.error is Model_login)
                    Helper.showSuccessToast(this, liveData.error.message)
            }
            else -> {
            }
        }
    }


}