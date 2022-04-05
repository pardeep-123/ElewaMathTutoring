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
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.details.DetailActivity
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem.MathProblemListResponse
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.CommonMethods
import com.elewamathtutoring.Util.constant.Constants
import kotlinx.android.synthetic.main.item_math_problem.view.*
import kotlinx.android.synthetic.main.popup_math_problem.view.*


class MathProblemAdapter(
    var ctn: Context,
    var onClickMath: OnClickMath,
    var list: ArrayList<MathProblemListResponse.Body>
) :
    RecyclerView.Adapter<MathProblemAdapter.ViewHolder>() {

    val viewGroup: ViewGroup = (ctn as Activity).window.decorView.rootView as ViewGroup
    var myPopupWindow: PopupWindow? = null

    interface OnClickMath{
        fun onClickDelete(deleteId: String, position: Int)
        fun onClickEdit(editId: MathProblemListResponse.Body)
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_math_problem, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvDescription.text = list[position].description
        holder.itemView.tvTime.text = Constants.getNotificationTime(list[position].createdAt.toLong())
        holder.itemView.tvComment.text = list[position].count.toString()+" comment"
        Glide.with(ctn).load(Constants.documents_URL+list[position].document).placeholder(R.drawable.profile_unselected)
            .into(holder.itemView.ivImg)

       //

        //

        holder.itemView.tvComment.setOnClickListener {

            val intent = Intent(ctn, DetailActivity::class.java)
            intent.putExtra("comments",list[position])
            ctn.startActivity(intent)
        }
        holder.itemView.ivDots.setOnClickListener {
//            clickCallBack.onItemClick(position, "dots")

            val inflater = ctn.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.popup_math_problem, null)
            myPopupWindow = PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
            view.llDelete.setOnClickListener {
//            clickCallBack.onItemClick(list[position].id, "delete")
                onClickMath.onClickDelete(list[position].id.toString(),position)
                myPopupWindow!!.dismiss()
            }
            view.llEdit.setOnClickListener {
//            clickCallBack.onItemClick(position, "edit")
                onClickMath.onClickEdit(list[position])
                myPopupWindow!!.dismiss()
            }
            myPopupWindow?.setOnDismissListener {
                CommonMethods.clearDim(viewGroup)
                myPopupWindow!!.dismiss()
            }

            myPopupWindow?.showAsDropDown(it, -0, -35)
            CommonMethods.applyDim(viewGroup, 0.5f)
        }
    }


}