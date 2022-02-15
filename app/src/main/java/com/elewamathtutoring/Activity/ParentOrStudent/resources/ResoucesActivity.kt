package com.elewamathtutoring.Activity.ParentOrStudent.resources

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Adapter.ParentOrStudent.AddParticipantsAdapter
import com.elewamathtutoring.Adapter.ParentOrStudent.MathChatAdapter
import com.elewamathtutoring.Adapter.ParentOrStudent.NotificationsAdapter
import com.elewamathtutoring.Adapter.ParentOrStudent.ResourcesAdapter
import com.elewamathtutoring.Models.Notifications.Body
import com.elewamathtutoring.Models.Notifications.Model_Notifications
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_add_participant.*
import kotlinx.android.synthetic.main.activity_math_chat.*
import kotlinx.android.synthetic.main.activity_notifications.*
import kotlinx.android.synthetic.main.activity_resouces.*
import kotlinx.android.synthetic.main.activity_resouces.ivBack
import kotlinx.android.synthetic.main.activity_resouces.ivFilter
import javax.inject.Inject

class ResoucesActivity : AppCompatActivity(), View.OnClickListener , Observer<RestObservable> {
lateinit var context: Context
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    var list = ArrayList<Body>()
    var aboutResponse: ResourcesResponse? = null
    lateinit var resourcesAdapter: ResourcesAdapter
    lateinit var addParticipantsAdapter: AddParticipantsAdapter
    private var popupWindow: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resouces)
        context=this
        ivBack.setOnClickListener(this)
        ivFilter.setOnClickListener(this)


    }

    override fun onResume() {
        super.onResume()
        api()
    }
    private fun setPopUpWindow() {
        val viewGroup: ViewGroup = window.decorView.rootView as ViewGroup
        val inflater =
            applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_resouces, null)
        popupWindow = PopupWindow(view, 450, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        ivFilter.setOnClickListener {
            popupWindow?.showAsDropDown(it, 150, 48)
            applyDim(viewGroup, 0.5f)
        }
        /*  view.tvEdit.setOnClickListener {
              startActivity(
                  Intent(
                      this,
                      EditProductActivity::class.java
                  )
              )
          }*/
        /*  view.tvDelete.setOnClickListener { dialogDelete() }*/
        popupWindow?.setOnDismissListener {
            clearDim(viewGroup)
        }
    }

    fun applyDim(parent: ViewGroup, dimAmount: Float) {
        val dim: Drawable = ColorDrawable(Color.BLACK)
        dim.setBounds(0, 0, parent.width, parent.height)
        dim.alpha = (255 * dimAmount).toInt()
        val overlay = parent.overlay
        overlay.add(dim)
    }

    fun clearDim(parent: ViewGroup) {
        val overlay = parent.overlay
        overlay.clear()
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.ivFilter -> {
                setPopUpWindow()
                // startActivity(Intent(this, VideoCallActivity::class.java))
            }
        }
    }


    private fun api() {
        baseViewModel.get_resources(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
    override fun onChanged(it: RestObservable?) {
        when (it!!.status) {
            Status.SUCCESS -> {
                if (it.data is ResourcesResponse) {
                    aboutResponse=it.data
                    list = it.data.body as ArrayList<Body>
                    list.reverse()

                    if(aboutResponse!!.body.isEmpty())
                    {
                        tvNoData.visibility=View.VISIBLE
                    }
                    else
                    {
                        tvNoData.visibility=View.GONE
                        rvResources.adapter = ResourcesAdapter(this,aboutResponse!!)
                    }
                }
            }
            Status.ERROR -> {
                if (it.error is ResourcesResponse) {

                }
            }
        }
    }
}