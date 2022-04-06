package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.details.CommentListResponse
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem.MathProblemListResponse
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants

import kotlinx.android.synthetic.main.item_detail.view.*
import kotlinx.android.synthetic.main.item_detail.view.tvDescription
import kotlinx.android.synthetic.main.item_detail.view.tvTime
import kotlinx.android.synthetic.main.item_math_problem.view.*


class DetailAdapter(var ctn: Context, var list: ArrayList<CommentListResponse.Body.AllComment>) :
    RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_detail, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(ctn).load(Constants.IMAGE_URL+list[position].user.image).placeholder(R.drawable.profile_unselected)
            .into(holder.itemView.ivProfileImg)
        holder.itemView.tvName.text = list[position].user.name
        holder.itemView.tvTime.text = Constants.getNotificationTime(list[position].createdAt.toLong())
        holder.itemView.tvDescription.setText(list[position].comment)
    }
}