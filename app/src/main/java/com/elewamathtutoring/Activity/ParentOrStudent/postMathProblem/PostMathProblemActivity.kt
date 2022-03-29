package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.elewamathtutoring.Adapter.ClickCallBack
import com.elewamathtutoring.Adapter.ParentOrStudent.MathProblemAdapter
import com.elewamathtutoring.Adapter.ParentOrStudent.NotificationsAdapter
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.CommonMethods.applyDim
import com.elewamathtutoring.Util.CommonMethods.clearDim
import kotlinx.android.synthetic.main.activity_post_math_problem.*
import kotlinx.android.synthetic.main.item_math_problem.*
import kotlinx.android.synthetic.main.popup_math_problem.*
import kotlinx.android.synthetic.main.popup_math_problem.llEdit
import kotlinx.android.synthetic.main.popup_math_problem.view.*


class PostMathProblemActivity : AppCompatActivity(), View.OnClickListener , ClickCallBack {
    var myPopupWindow: PopupWindow? = null
    lateinit var context: Context
    lateinit var mathProblemAdapter:MathProblemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_math_problem)
        context=this
        ivBack.setOnClickListener(this)
        rvMathProblem.adapter = MathProblemAdapter(this,this)
    }
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.ivBack->{
                finish()
            }

        }
    }

    override fun onItemClick(pos: Int, value: String) {
        when (value) {
            "dots" -> {
                setPopUpWindow()
            }
        }
    }
    private fun setPopUpWindow() {
        val viewGroup: ViewGroup = window.decorView.rootView as ViewGroup
        val inflater =
            applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_math_problem, null)
        myPopupWindow = PopupWindow(view, 400, RelativeLayout.LayoutParams.WRAP_CONTENT, true)

        ivDots.setOnClickListener {
            myPopupWindow?.showAsDropDown(it, -0, -35)
            applyDim(viewGroup, 0.5f)
        }
        view.llDelete.setOnClickListener {
            finish()
        }
        view.llEdit.setOnClickListener {
            finish()
        }
        myPopupWindow?.setOnDismissListener {
            clearDim(viewGroup)
        }
    }
}