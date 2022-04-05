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

import kotlinx.android.synthetic.main.item_detail.view.*


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
        Glide.with(ctn).load(list[position].user.image).placeholder(R.drawable.profile_unselected)
            .into(holder.itemView.ivProfileImg)
        holder.itemView.tvName.text = list[position].user.name
        holder.itemView.tvTime.setText(list[position].createdAt.toString())
        holder.itemView.tvDescription.setText(list[position].comment)
    }
}