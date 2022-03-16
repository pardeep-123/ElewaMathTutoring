package com.elewamathtutoring.Activity.ParentOrStudent.editProfile
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.rideandserve.utils.ImagePickerUtility
import com.bumptech.glide.Glide
import com.elewamathtutoring.Models.Login.Body
import com.elewamathtutoring.Models.Login.Model_login
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants.Companion.scrollEditText
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_edit_profile.*
class EditProfile : ImagePickerUtility(), Observer<RestObservable> {
    var firstimage = ""
    var oldImage = ""
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var editProfilelist = ArrayList<Body>()
    override fun selectedImage(imagePath: String?, code: Int) {
        Glide.with(this).load(imagePath).placeholder(R.drawable.profile_unselected).into(ivProfileSignUp)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        scrollEditText(edtAboutYou)
     /*   if (intent.getStringExtra("from").equals("EditProfile")) {
            editProfilelist = intent.getSerializableExtra("ProfileList") as ArrayList<Body>
            edtName.setText(editProfilelist.get(0).name)
            edtAboutYou.setText(editProfilelist.get(0).about)
            edtName.setText(intent.getStringExtra("username").toString())
            Glide.with(this).load(editProfilelist.get(0).image).into(ivProfileSignUp)

        }*/

        edtName.setText(intent.getStringExtra("name").toString())
        edtAboutYou.setText(intent.getStringExtra("about").toString())
        oldImage = intent.getStringExtra("image").toString()
        Glide.with(this).load(intent.getStringExtra("image").toString())
            .placeholder(R.drawable.placeholder_image).into(ivProfileSignUp)
        ivBack.setOnClickListener {
            onBackPressed()
        }
        ivAdd.setOnClickListener {
            selectImage()
        }
        btnSave.setOnClickListener {
          api()
        }
    }
    private fun api() {
        baseViewModel.editParentProfile(this, firstimage, edtName.text.toString(), edtAboutYou.text.toString(), true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
    private fun selectImage() {
        Album.image(this).singleChoice().camera(true).columnCount(4).widget(
            Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivProfileSignUp)
                firstimage = result[0].path
            }.onCancel {
            }.start()
    }
    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is EditProfileResponse) {
                    Helper.showSuccessToast(this, liveData.data.message)
                    finish()
                }
            }
            Status.ERROR -> {
                if (liveData.error is EditProfileResponse)
                    Helper.showSuccessToast(this, liveData.error.message)
            }
        }
    }
}