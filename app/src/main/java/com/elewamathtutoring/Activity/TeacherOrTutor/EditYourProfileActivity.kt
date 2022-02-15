package com.elewamathtutoring.Activity.TeacherOrTutor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfo.TeachingInfoActivity
import com.elewamathtutoring.Models.Login.Body
import com.elewamathtutoring.Models.Login.Model_login
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_edit_your_profile.*

class EditYourProfileActivity : AppCompatActivity(), View.OnClickListener,
    Observer<RestObservable> {
    var profilelist=ArrayList<Body>()
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_your_profile)
        llAboutYou.setOnClickListener(this)
        llTeachingInfoRates.setOnClickListener(this)
        llAvailability.setOnClickListener(this)
        ivBack.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.llAboutYou -> {
                val intentt = Intent(this, AboutYouActivity::class.java)
                intentt.putExtra("key", "editprofile")
                intentt.putExtra("list_model", profilelist)
                startActivity(intentt)
            }
            R.id.llTeachingInfoRates -> {
                val intentt = Intent(this, TeachingInfoActivity::class.java)
                intentt.putExtra("key", "editprofile")
                intentt.putExtra("list_model", profilelist)
                startActivity(intentt)
            }
            R.id.llAvailability -> {
                val intentt = Intent(this, AvailablityActivity::class.java)
                intentt.putExtra("key", "available")
                /*intentt.putExtra("list_model", intent.getSerializableExtra("list_model"))
                intentt.putExtra("key", profilelist)*/
                startActivity(intentt)
            }
            R.id.ivBack -> {
                finish()
            }
        }
    }
    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is Model_login) {
                    profilelist.clear()
                    profilelist.addAll(listOf(liveData.data.body))
                }
            }
            Status.ERROR -> {
                if (liveData.error is Model_login)
                {
                    Helper.showSuccessToast(this, liveData.error.message)
                }
            }
            else -> {
            }
        }
    }
    override fun onResume() {
        super.onResume()
        baseViewModel.get_profile(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
}//  baseViewModel.get_profile(this, getPrefrence(Constants.user_type, ""), true)