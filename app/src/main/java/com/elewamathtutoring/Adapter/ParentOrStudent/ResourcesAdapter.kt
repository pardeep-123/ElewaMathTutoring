package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.AddCardActivity
import com.elewamathtutoring.Activity.Chat.mathChat.MathPersonChatActivity
import com.elewamathtutoring.Activity.ParentOrStudent.resources.ResourcesResponse
import com.elewamathtutoring.Models.Notifications.Body
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_notifications.view.*
import kotlinx.android.synthetic.main.item_notifications.view.tvMessages
import kotlinx.android.synthetic.main.item_resources.view.*

//, listNotifications: ArrayList<Body>
class ResourcesAdapter(var ctn: Context,
                     var  list: ResourcesResponse
) :
    RecyclerView.Adapter<ResourcesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_resources, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
     return list.body.size
        //return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvDescription.setText(list.body[position].text)
        holder.itemView.tvlink.setText(list.body[position].link)
        holder.itemView.tvCategory.setText(list.body[position].categoryName)
        holder.itemView.tvAuthorName.setText(list.body[position].authname)

    }
}