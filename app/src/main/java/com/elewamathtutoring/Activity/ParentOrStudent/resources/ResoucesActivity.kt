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
import com.elewamathtutoring.Adapter.ParentOrStudent.AddParticipantsAdapter
import com.elewamathtutoring.Adapter.ParentOrStudent.MathChatAdapter
import com.elewamathtutoring.Adapter.ParentOrStudent.ResourcesAdapter
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.activity_add_participant.*
import kotlinx.android.synthetic.main.activity_math_chat.*
import kotlinx.android.synthetic.main.activity_resouces.*
import kotlinx.android.synthetic.main.activity_resouces.ivBack
import kotlinx.android.synthetic.main.activity_resouces.ivFilter

class ResoucesActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var resourcesAdapter: ResourcesAdapter
    lateinit var addParticipantsAdapter: AddParticipantsAdapter
    private var popupWindow: PopupWindow? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resouces)
        ivBack.setOnClickListener(this)
        ivFilter.setOnClickListener(this)

        resourcesAdapter = ResourcesAdapter(this)
        rvResources.adapter = resourcesAdapter

    }
    private fun setPopUpWindow() {
        val viewGroup: ViewGroup = window.decorView.rootView as ViewGroup
        val inflater =
            applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_resouces, null)
        popupWindow = PopupWindow(view, 550, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
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
}