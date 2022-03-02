package com.elewamathtutoring.Fragment.TeacherOrTutor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.ParentOrStudent.settings.SettingActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.edit.EditResponse
import com.elewamathtutoring.Activity.TeacherOrTutor.editProfile.AboutYouActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.edit.EditYourProfileActivity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.constant.Constants.Companion.isCertifiedOrtutor
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_profile_tab.*
import kotlinx.android.synthetic.main.fragment_profile_tab.view.*

class TeacherProfileTabFragment : Fragment(), View.OnClickListener, Observer<RestObservable> {
    lateinit var v: View
    lateinit var contex: Context
    var profilelist = ArrayList<EditResponse.Body>()
    var data = ""
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    var image = ""
    var name = ""
    var about = ""
    var teachingHistory = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profile_tab, container, false)
        contex = activity as Context
        onClicks()
        return v
    }

    private fun api() {
        //, getPrefrence(Constants.user_type, "")
        baseViewModel.getProfile(requireActivity(), true)
        baseViewModel.getCommonResponse().observe(requireActivity(), this)
    }

    private fun onClicks() {
        v.rootView.ivSetting.setOnClickListener(this)
        v.rootView.llEditProfileInformation.setOnClickListener(this)
        v.rootView.btnEditProfile.setOnClickListener(this)
        // v.rootView.llUpgradeYourProfile.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivSetting -> {
                startActivity(
                    Intent(context, SettingActivity::class.java)
                        .putExtra("setting", "managePayment")
                )
            }
            R.id.llEditProfileInformation -> {
                val intent = Intent(contex, EditYourProfileActivity::class.java)
                intent.putExtra("list_model", profilelist)
                startActivity(intent)
            }
            R.id.btnEditProfile -> {
                val i = Intent(contex, AboutYouActivity::class.java)
                i.putExtra("name", name)
                i.putExtra("about", about)
                i.putExtra("teachingHistory", teachingHistory)
                i.putExtra("image", image)
                //i.putExtra("list",profilelist )
                startActivity(i)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is EditResponse) {
                    profilelist.addAll(listOf(liveData.data.body))
                    text_teacher_name.text = liveData.data.body.name
                    text_parent_spicilty.text = liveData.data.body.specialties
                    text_parent_spicilty.text =
                        isCertifiedOrtutor(liveData.data.body.isCertifiedOrtutor)
                    text_teacher_CancelationPolicy.text = liveData.data.body.cancellationPolicy
                    text_teacher_AboutUser.text = liveData.data.body.about
                    text_teacher_inprice.text =
                        Constants.Currency + liveData.data.body.InPersonRate.toString() + ".00/Hr"
                    Glide.with(contex).load(liveData.data.body.image)
                        .placeholder(R.drawable.profile_unselected).into(image_teacher_image)

                    name = liveData.data.body.name
                    about = liveData.data.body.about
                    teachingHistory = liveData.data.body.teachingHistory
                    image = liveData.data.body.image
                }
            }
            Status.ERROR -> {
                if (liveData.error is EditResponse) {
                    Helper.showSuccessToast(requireContext(), liveData.error.message)
                }
            }
            else -> {
            }
        }
    }
    override fun onResume() {
        super.onResume()
        api()
    }
}