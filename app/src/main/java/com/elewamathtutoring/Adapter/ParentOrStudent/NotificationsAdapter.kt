package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.ParentOrStudent.notification.NotificationModel
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import kotlinx.android.synthetic.main.item_notifications.view.*


import kotlin.collections.ArrayList

class NotificationsAdapter(c: Context, listNotifications: ArrayList<NotificationModel.Body>) :
    RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {
    var ctn = c
    var list = listNotifications

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_notifications, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvMessages.text = list[position].message
        holder.itemView.tvNotificationName.text = list[position].name
        holder.itemView.tvDate.text = Constants.convertDateToRatingTime(list[position].createdAt.toLong())
        holder.itemView.tvTime.text = Constants.convertTime(list[position].createdAt.toLong())
        Glide.with(ctn).load(Constants.IMAGE_URL+list[position].image)
            .into(holder.itemView.ivNotifications)
    }
}