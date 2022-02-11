package com.elewamathtutoring.Fragment.ParentOrStudent.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.ParentOrStudent.editProfile.EditProfile
import com.elewamathtutoring.Activity.NotificationsActivity
import com.elewamathtutoring.Activity.ParentOrStudent.settings.SettingActivity
import com.elewamathtutoring.Models.Login.Body
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.pawskeeper.Modecommon.Commontoall
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.ivNotification
import kotlinx.android.synthetic.main.fragment_profile.view.ivSetting
import kotlin.collections.ArrayList

class ProfileFragment : Fragment(), Observer<RestObservable> {
    //var listdata= ArrayList<com.elewamathtutoring.Models.TeacherRequestsList.Body>()
    var name = ""
    var about = ""
    var image = ""
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    lateinit var contex: Context
    lateinit var btnEditProfile: Button
    lateinit var v: View
    var list = ArrayList<Body>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profile, container, false)
        contex = activity as Context
        onClicks()
     //   v.rootView.rv_pastTeachers.adapter = PastTeacherAdapter(requireContext(),this@ProfileFragment)
        return v
    }


    private fun onClicks() {
        v.rootView.ivSetting.setOnClickListener {
            startActivity(Intent(context, SettingActivity::class.java))
        }
        v.rootView.ivNotification.setOnClickListener {
            startActivity(Intent(context, NotificationsActivity::class.java))
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnEditProfile = view.findViewById(R.id.btnedtProfile)
        btnEditProfile.setOnClickListener {
            val i = Intent(requireContext(), EditProfile::class.java)
         //   i.putExtra("from","EditProfile")
          //  intent.putExtra("ProfileList",list)
            i.putExtra("name", name)
            i.putExtra("about", about)
            i.putExtra("image", Constants.IMAGE_URL + image)
            startActivity(i)
        }
    }
    private fun api() {
        baseViewModel.get_profile(requireActivity(), true)
        baseViewModel.getCommonResponse().observe(requireActivity(), this)
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is ProfileResponse) {
                   // list.addAll(listOf(liveData.data.body))
                    text_parent_name.text = liveData.data.body.name
                    text_parent_email.text = liveData.data.body.email
                    text_parent_about.text = liveData.data.body.about
                    Glide.with(contex).load(liveData.data.body.image)
                        .placeholder(R.drawable.profile_unselected).into(image_parent_image)
                    text_parent_titelname.text = "About " + liveData.data.body.name

                    name=liveData.data.body.name
                    about=liveData.data.body.about
                    image=liveData.data.body.image


                    apiPastTeacher()
                }
               /* else if (liveData.data is Model_TeacherRequestList) {
                    listdata.clear()
                    listdata.addAll(liveData.data.body)

                v.rootView.rv_pastTeachers.adapter = PastTeacherAdapter(requireContext(),listdata,this@ProfileFragment)
                    if(listdata.size==0)
                    {
                        tv_pasttecher.visibility=View.GONE
                    }
                }*/
                else if (liveData.data is Commontoall) {
                    apiPastTeacher()
                    Helper.showSuccessToast(requireContext(), liveData.data.message)
                }
            }
            Status.ERROR -> {
                if (liveData.error is ProfileResponse)
                {
                    Helper.showSuccessToast(requireContext(), liveData.error.message)
                }
                else if (liveData.error is Commontoall)
                {
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

    private fun apiPastTeacher() {
        baseViewModel.PastTeacher(requireActivity(), "2,3,4,5,6", true)
        baseViewModel.getCommonResponse().observe(requireActivity(), this)
    }
     fun apicomplete(id: Int) {
         baseViewModel.change_session_status(requireActivity(), "6",id.toString(),true)
        // baseViewModel.getCommonResponse().observe(requireActivity(), this)

     }
}