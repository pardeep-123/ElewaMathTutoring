package com.elewamathtutoring.Adapter.ParentOrStudent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.details.DetailActivity
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem.MathProblemListResponse
import com.elewamathtutoring.Adapter.ClickCallBack
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.CommonMethods
import kotlinx.android.synthetic.main.item_math_problem.view.*
import kotlinx.android.synthetic.main.popup_math_problem.view.*


class MathProblemAdapter(
    var ctn: Context,
    var clickCallBack: ClickCallBack,
    var list: ArrayList<MathProblemListResponse.Body>
) :
    RecyclerView.Adapter<MathProblemAdapter.ViewHolder>() {

    val viewGroup: ViewGroup = (ctn as Activity).window.decorView.rootView as ViewGroup
    var myPopupWindow: PopupWindow? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_math_problem, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvDescription.text = list[position].description
        setPopUpWindow(position)
        holder.itemView.ivDots.setOnClickListener {
//            val intent = Intent(ctn, TeacherDetailsActivity::class.java)
//            ctn.startActivity(intent)
        }
        holder.itemView.tvComment.setOnClickListener {
            val intent = Intent(ctn, DetailActivity::class.java)
            intent.putExtra("comments",list[position].id.toString())
            ctn.startActivity(intent)
        }
        holder.itemView.ivDots.setOnClickListener {
//            clickCallBack.onItemClick(position, "dots")
            myPopupWindow?.showAsDropDown(it, -0, -35)
            CommonMethods.applyDim(viewGroup, 0.5f)
        }
    }

    private fun setPopUpWindow(position: Int) {
        val inflater = ctn?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_math_problem, null)
        myPopupWindow = PopupWindow(view, 400, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        view.llDelete.setOnClickListener {
            clickCallBack.onItemClick(position, "delete")
            myPopupWindow!!.dismiss()
        }
        view.llEdit.setOnClickListener {
            clickCallBack.onItemClick(position, "edit")
            myPopupWindow!!.dismiss()
        }
        myPopupWindow?.setOnDismissListener {
            CommonMethods.clearDim(viewGroup)
            myPopupWindow!!.dismiss()
        }
    }
}