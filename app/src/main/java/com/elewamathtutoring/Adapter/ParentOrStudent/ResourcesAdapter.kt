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
import com.elewamathtutoring.Models.Notifications.Body
import com.elewamathtutoring.R

//, listNotifications: ArrayList<Body>
class ResourcesAdapter(c: Context) :
    RecyclerView.Adapter<ResourcesAdapter.ViewHolder>() {
    var ctn = c
   // var list = listNotifications

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_resources, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       // return list.size
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      /*  holder.itemView.home_listcardview.setOnClickListener {
            var intent = Intent(ctn, MathPersonChatActivity::class.java)
            ctn.startActivity(intent)
        }
*/
     /*   holder.itemView.tvMessages.text = list.get(position).message
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