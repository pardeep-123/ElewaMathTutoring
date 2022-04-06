package com.elewamathtutoring.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.Chat.Chat_Activity
import com.elewamathtutoring.Activity.Chat.chatModel.chatThreads.GetMessageInboxModel
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import kotlinx.android.synthetic.main.item_message.view.*
import java.text.SimpleDateFormat
import java.util.*


class MessageAdapter(var context: Context, var orderDetailForMapResponse: ArrayList<GetMessageInboxModel.GetMessageInboxModelItem>) : RecyclerView.Adapter<MessageAdapter.MessageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        return MessageHolder(LayoutInflater.from(context).inflate(R.layout.item_message, parent, false))
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        if (orderDetailForMapResponse[position].groupId!=null) {
            holder.itemView.tvMessageUser.text = orderDetailForMapResponse[position].groupName
            Glide.with(context)
                .load(Constants.SOCKET_BASE_URL_IMAGE + orderDetailForMapResponse.get(position).groupImage)
                .placeholder(R.drawable.profile_unselected).into(holder.itemView.ivMessageImage)
        }
            else {
            holder.itemView.tvMessageUser.text = orderDetailForMapResponse[position].userName
            Glide.with(context)
                .load(Constants.SOCKET_BASE_URL_IMAGE_USER + orderDetailForMapResponse.get(position).userImage)
                .placeholder(R.drawable.profile_unselected).into(holder.itemView.ivMessageImage)
        }
            holder.itemView.tvMessage.text= orderDetailForMapResponse[position].lastMessage
        

        holder.itemView.setOnClickListener {
            val i = Intent(context, Chat_Activity::class.java)
            if(getPrefrence(Constants.USER_ID, "").equals(orderDetailForMapResponse.get(position).user2Id.toString())) {
                i.putExtra("receiverId", orderDetailForMapResponse.get(position).userId.toString())
                i.putExtra("chatUserName", orderDetailForMapResponse.get(position).userName)
            }else if ( orderDetailForMapResponse.get(position).user2Id==0) {
                i.putExtra("receiverId", orderDetailForMapResponse.get(position).user2Id.toString())
                i.putExtra("groupId", orderDetailForMapResponse.get(position).groupId.toString())
                i.putExtra("chatUserName", orderDetailForMapResponse.get(position).groupName)
            }else{
                i.putExtra("receiverId", orderDetailForMapResponse.get(position).user2Id.toString())
                i.putExtra("chatUserName", orderDetailForMapResponse.get(position).userName)
            }


            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
      return orderDetailForMapResponse.size
    }
    inner class MessageHolder(view: View) : RecyclerView.ViewHolder(view) {}
    fun convertTimestampToDate(timestamp: Long, dateFormateStyle: String): String {
        val calendar = Calendar.getInstance()
        val tz = TimeZone.getDefault()
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.timeInMillis))
        val sdf = SimpleDateFormat(dateFormateStyle)
        sdf.timeZone = tz
        val currenTimeZone = Date(timestamp * 1000)
        return sdf.format(currenTimeZone)
    }
}
