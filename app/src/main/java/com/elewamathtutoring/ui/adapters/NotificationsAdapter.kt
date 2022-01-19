package com.elewamathtutoring.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.R
import java.lang.Exception
import kotlin.collections.ArrayList

class NotificationsAdapter(c: Context) :
    RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {
    var ctn = c

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_notifications, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       /* holder.itemView.tvMessages.text = list.get(position).message
        try {
            holder.itemView.tvNotificationsTime.text = Constants.ConvertTimeStampToDate(list.get(position).createdAt.toLong(),"EEE dd, yyyy")

        }catch (e:Exception)
        {

        }
        if (getPrefrence(Constants.user_type, "").toString().equals("1")) {
            holder.itemView.tvNotificationName.text =
                list.get(position).sessionDetail.get(0).teacher.name
           //holder.itemView.tvMessages.text = list.get(position).message
            Glide.with(ctn).load(Constants.IMAGE_URL+list.get(position).sessionDetail.get(0).teacher.image)
                .into(holder.itemView.ivNotifications);
        } else {
            holder.itemView.tvNotificationName.text =
                list.get(position).sessionDetail.get(0).student.name
            Glide.with(ctn).load(Constants.IMAGE_URL+list.get(position).sessionDetail.get(0).student.image)
                .into(holder.itemView.ivNotifications);
        }*/

    }
}