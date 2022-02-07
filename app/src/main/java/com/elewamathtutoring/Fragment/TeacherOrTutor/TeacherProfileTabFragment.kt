package com.elewamathtutoring.Fragment.TeacherOrTutor

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
import com.elewamathtutoring.Activity.SettingActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.AboutYouActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.EditYourProfileActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.SubscriptionsActivity
import com.elewamathtutoring.Models.Login.Body
import com.elewamathtutoring.Models.Login.Model_login
import com.elewamathtutoring.Models.Teacher_level.Model_teacher_level
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.constant.Constants.Companion.isCertifiedOrtutor
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_profile_tab.*
import kotlinx.android.synthetic.main.fragment_profile_tab.view.*

class TeacherProfileTabFragment : Fragment(), View.OnClickListener , Observer<RestObservable> {
    lateinit var v: View
    lateinit var contex: Context
    var profilelist=ArrayList<Body>()
    var data=""
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profile_tab, container, false)
        contex = activity as Context
        onClicks()

        return v
    }
    private fun api()
    {
        baseViewModel.get_profile(requireActivity(), getPrefrence(Constants.user_type, ""), true)
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
                startActivity(Intent(context, SettingActivity::class.java)
                    .putExtra("setting","managePayment")
                )
            }
            R.id.llEditProfileInformation -> {
                val intent = Intent(contex, EditYourProfileActivity::class.java)
                intent.putExtra("list_model",profilelist )
                startActivity(intent)
            }
            R.id.btnEditProfile -> {
                val intent = Intent(contex, AboutYouActivity::class.java)
                startActivity(intent)
            }
       /*     R.id.llUpgradeYourProfile -> {
                startActivity(Intent(context, SubscriptionsActivity::class.java))
            }*/
        }
    }
    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is Model_login) {
                    profilelist.addAll(listOf(liveData.data.body))
                    text_teacher_name.text=liveData.data.body.name
                    data = profilelist.get(0).teachingLevel.toString()
                   // text_teacher_spicel.text=liveData.data.body.specialties
                    text_parent_spicilty.text=isCertifiedOrtutor(liveData.data.body.isCertifiedOrtutor)
                    text_teacher_CancelationPolicy.text=liveData.data.body.cancellationPolicy
                    text_teacher_AboutUser.text=liveData.data.body.about
                 //   text_teacher_TeachingHistory.text=liveData.data.body.teachingHistory
                    //text_teacher_virtual.text=(Constants.Currency+liveData.data.body.virtualRate.toString())+".00/Hr"
                    text_teacher_inprice.text=Constants.Currency+liveData.data.body.InPersonRate.toString()+".00/Hr"
                    Glide.with(contex).load(liveData.data.body.image).placeholder(R.drawable.profile_unselected).into(image_teacher_image)
                    api_techinglevel()
                }
               else if (liveData.data is Model_teacher_level)
               {
                 /*  text_teacher_category.text=""
                   var words= ArrayList<String>()
                   try
                   {
                       words= data.split(",") as ArrayList<String>
                   }
                   catch(e:Exception)
                   {
                        words= data.split("") as ArrayList<String>
                   }

                   for(i in 0 until words.size) {
                       for (j in 0 until liveData.data.body.size)
                       {
                           if (words.get(i).toInt() == liveData.data.body.get(j).id)
                           {
                               var data=liveData.data.body.get(j).level+","
                               text_teacher_category.text=text_teacher_category.text.toString()+data
                           }
                       }
                   }
                   var s = text_teacher_category.text.toString()
                   text_teacher_category.text = s.substring(0, s.length -1)*/
                }
            }
            Status.ERROR -> {
                if (liveData.error is Model_login)
                {
                    Helper.showSuccessToast(requireContext(), liveData.error.message)
                }
            }
            else -> {
            }
        }
    }
    private fun api_techinglevel() {
        baseViewModel.teacher_level(requireActivity(), false)
    }

    override fun onResume() {
        super.onResume()
        api()
    }
}