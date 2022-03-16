package com.elewamathtutoring.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.Chat.mathChat.MathChatResponse
import com.elewamathtutoring.Activity.ParentOrStudent.teacherDetail.TeacherDetailsActivity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import kotlinx.android.synthetic.main.home_list.view.*
import kotlin.collections.ArrayList
class SearchHomeAdapter(
    var ctx: Context,
    var list: ArrayList<MathChatResponse.Body>
) : RecyclerView.Adapter<SearchHomeAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView: View = LayoutInflater.from(ctx).inflate(R.layout.home_list, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.tvItemName.text = list[position].name
        holder.itemView.tv_level.text = list[position].teachingLevel
        holder.itemView.tvPostion.text =
            Constants.isCertifiedOrtutor(list[position].isCertifiedOrtutor)
        Glide.with(ctx).load(Constants.IMAGE_URL + list[position].image)
            //.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .error(R.drawable.profile)
            .placeholder(R.drawable.profile_unselected).into(holder.itemView.ivTeacher)
        holder.itemView.home_listcardview.setOnClickListener {
            val intent = Intent(ctx, TeacherDetailsActivity::class.java)
            intent.putExtra("teacher_id", "" + list[position].id.toString())
            intent.putExtra("Type", "searchhome")
            ctx.startActivity(intent)
        }
        if (list[0].occupiedStatus == 1) {
            holder.itemView.ivStatus.setImageResource(R.drawable.blue_gola)
        } else if (list[0].occupiedStatus == 2) {
            holder.itemView.ivStatus.setImageResource(R.drawable.orng_gola)
        } else {
            holder.itemView.ivStatus.setImageResource(R.drawable.green_gola)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    class MyHolder(v: View) : RecyclerView.ViewHolder(v) {}
    fun notifyData(filterServiceList: ArrayList<MathChatResponse.Body>) {
        list = filterServiceList
        notifyDataSetChanged()
    }
}