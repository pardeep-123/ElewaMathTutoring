package com.pawskeeper.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.Chat.Chat_Activity
import com.elewamathtutoring.Activity.Chat.chatModel.chatThreads.GetInboxMessageListResponse
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import kotlinx.android.synthetic.main.item_message.view.*
import java.text.SimpleDateFormat
import java.util.*


class MessageAdapter(var context: Context, var orderDetailForMapResponse: ArrayList<GetInboxMessageListResponse>) : RecyclerView.Adapter<MessageAdapter.MessageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        return MessageHolder(LayoutInflater.from(context).inflate(R.layout.item_message, parent, false))
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.itemView.tvMessageUser.text= orderDetailForMapResponse.get(position).userName
  
            holder.itemView.tvMessage.text= orderDetailForMapResponse.get(position).lastMessage
        
        Glide.with(context).load(Constants.IMAGE_URL + orderDetailForMapResponse.get(position).userImage).placeholder(R.drawable.placeholder_image).into(holder.itemView.ivMessageImage)

        holder.itemView.setOnClickListener {
            val i = Intent(context, Chat_Activity::class.java)
            if(getPrefrence(Constants.USER_ID, "").equals(orderDetailForMapResponse.get(position).user2Id.toString()))
            {
                i.putExtra("receiverId", orderDetailForMapResponse.get(position).userId.toString())
            }
            else
            {
                i.putExtra("receiverId", orderDetailForMapResponse.get(position).user2Id.toString())
            }

            i.putExtra("chatUserName", orderDetailForMapResponse.get(position).userName)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int
    {
       // return orderDetailForMapResponse.size

        return 3
    }

    inner class MessageHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
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
