package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.Chat.Chat_Activity
import com.elewamathtutoring.Activity.Chat.mathChat.MathChatResponse
import com.elewamathtutoring.Activity.Chat.mathChat.MathPersonChatActivity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import kotlinx.android.synthetic.main.item_math_chat.view.*

class MathChatAdapter(c: Context, var list: ArrayList<MathChatResponse.Body>, var type: String) :
    RecyclerView.Adapter<MathChatAdapter.MyHolder>() {
    var ctn = c


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_math_chat, parent, false)
        return MyHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size

    }
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.home_listcardview.setOnClickListener {
            var intent = Intent(ctn, Chat_Activity::class.java)
            intent.putExtra("receiverId",list[position].id.toString())
            intent.putExtra("chatUserName",list[position].name)
            ctn.startActivity(intent)
        }
        if (type == "1") {
            holder.itemView.tvName.text = list[position].name
            Glide.with(holder.itemView.context)
                .load(Constants.IMAGE_URL + list[position].image)
                .placeholder(R.drawable.placeholder_image).into(holder.itemView.ivTeacher)
            holder.itemView.tvPostion.visibility = View.GONE
            holder.itemView.tv_level.visibility = View.GONE
            holder.itemView.ivMsg.visibility = View.GONE
            holder.itemView.rlResources.visibility = View.VISIBLE
        } else {
            Glide.with(holder.itemView.context)
                .load(Constants.IMAGE_URL + list[position].image)
                .placeholder(R.drawable.placeholder_image).into(holder.itemView.ivTeacher)

            holder.itemView.tvPostion.visibility = View.VISIBLE
            holder.itemView.tv_level.visibility = View.VISIBLE
            holder.itemView.rlResources.visibility = View.GONE
            holder.itemView.ivMsg.visibility = View.VISIBLE

            holder.itemView.tvName.setText(list[position].name)
            holder.itemView.tvPostion.setText(list[position].teachingLevel)
            holder.itemView.tv_level.setText(list[position].teachingLevel)
        }

    }
    class MyHolder(v: View) : RecyclerView.ViewHolder(v) {}
    fun notifyData(filterServiceList: ArrayList<MathChatResponse.Body>) {
        list = filterServiceList
        notifyDataSetChanged()
    }
}
//class MathChatAdapter(c: Context, var list: MathChatResponse, var type: String) :
//    RecyclerView.Adapter<MathChatAdapter.ViewHolder>() {
//    var ctn = c
//
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(ctn).inflate(R.layout.item_math_chat, parent, false)
//        return ViewHolder(view)
//    }
//    override fun getItemCount(): Int {
//        return list.body.size
//
//    }
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.itemView.home_listcardview.setOnClickListener {
//            var intent = Intent(ctn, Chat_Activity::class.java)
//            ctn.startActivity(intent)
//        }
//        if (type == "1") {
//            holder.itemView.tvName.text = list.body[position].name
//            Glide.with(holder.itemView.context)
//                .load(Constants.IMAGE_URL + list.body[position].image)
//                .placeholder(R.drawable.placeholder_image).into(holder.itemView.ivTeacher)
//            holder.itemView.tvPostion.visibility = View.GONE
//            holder.itemView.tv_level.visibility = View.GONE
//            holder.itemView.ivMsg.visibility = View.GONE
//            holder.itemView.rlResources.visibility = View.VISIBLE
//        } else {
//            Glide.with(holder.itemView.context)
//                .load(Constants.IMAGE_URL + list.body[position].image)
//                .placeholder(R.drawable.placeholder_image).into(holder.itemView.ivTeacher)
//
//            holder.itemView.tvPostion.visibility = View.VISIBLE
//            holder.itemView.tv_level.visibility = View.VISIBLE
//            holder.itemView.rlResources.visibility = View.GONE
//            holder.itemView.ivMsg.visibility = View.VISIBLE
//
//            holder.itemView.tvName.setText(list.body[position].name)
//            holder.itemView.tvPostion.setText(list.body[position].teachingLevel)
//            holder.itemView.tv_level.setText(list.body[position].teachingLevel)
//        }
//
//        /*  holder
//          try {
//              holder.itemView.tvNotificationsTime.text = Constants.ConvertTimeStampToDate(list.get(position).createdAt.toLong(),"EEE dd, yyyy")
//
//          }catch (e:Exception)
//          {
//
//          }*/
///*
//        if (getPrefrence(Constants.user_type, "").toString().equals("1")) {
//            Glide.with(holder.itemView.context)
//                .load(Constants.IMAGE_URL + list.body[position].image)
//                .placeholder(R.drawable.placeholder_image).into(holder.itemView.ivTeacher)
//            holder.itemView.tvName.setText(list.body[position].name)
//            holder.itemView.tvPostion.visibility=View.GONE
//            holder.itemView.tv_level.visibility=View.GONE
//            holder.itemView.rlResources.visibility=View.VISIBLE
//        }
//        else {
//            Glide.with(holder.itemView.context)
//                .load(Constants.IMAGE_URL + list.body[position].image)
//                .placeholder(R.drawable.placeholder_image).into(holder.itemView.ivTeacher)
//
//            holder.itemView.tvPostion.visibility=View.VISIBLE
//            holder.itemView.tv_level.visibility=View.VISIBLE
//            holder.itemView.rlResources.visibility=View.GONE
//
//            holder.itemView.tvName.setText(list.body[position].name)
//            holder.itemView.tvPostion.setText(list.body[position].teachingLevel)
//            holder.itemView.tv_level.setText(list.body[position].teachingLevel)
//        }
//*/
//
//    }
//}