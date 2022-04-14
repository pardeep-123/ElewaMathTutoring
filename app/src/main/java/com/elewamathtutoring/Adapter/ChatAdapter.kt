package com.pawskeeper.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.Chat.chatModel.ChatListModel
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.constant.Constants.Companion.convertTimestampToDate
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import kotlinx.android.synthetic.main.item_chat2.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ChatAdapter(var context: Context?, var arrayList: ArrayList<ChatListModel.ChatListItem>) : RecyclerView.Adapter<ChatAdapter.RecyclerViewHolder>()
{
    var timeago=""
    inner class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_chat2, parent, false))
    }

    override fun getItemCount(): Int {
       // return arrayList.size
        return arrayList.size
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int)
    {
            if(arrayList[position].senderID.toString().equals(getPrefrence(Constants.USER_ID,""))) {
                /**
                 * to check the message type
                 */
                if (arrayList[position].msgType==0) {
                    holder.itemView.chatright.text = arrayList[position].message
                    holder.itemView.time_right.text = getHourAgoTime(
                        convertTimestampToDate(
                            arrayList.get(position).createdAt.toLong(),
                            "yyyy.MM.dd G 'at' HH:mm:ss"
                        )
                    )
                    holder.itemView.layout1.visibility = View.GONE
                    holder.itemView.layout3.visibility = View.GONE
                    holder.itemView.layout4.visibility = View.GONE
                    holder.itemView.layout2.visibility = View.VISIBLE
                }else{
                    Glide.with(context!!).load(Constants.SOCKET_BASE_URL_IMAGE+arrayList[position].message).into(holder.itemView.ivRight)
                    holder.itemView.time_right.text = getHourAgoTime(
                        convertTimestampToDate(
                            arrayList.get(position).createdAt.toLong(),
                            "yyyy.MM.dd G 'at' HH:mm:ss"
                        )
                    )
                    holder.itemView.layout1.visibility = View.GONE
                    holder.itemView.layout2.visibility = View.GONE
                    holder.itemView.layout3.visibility = View.GONE
                    holder.itemView.layout4.visibility = View.VISIBLE
                }
            }
            else// reciver side
            {
                if (arrayList[position].msgType==0) {
                    holder.itemView.chatleft.text = arrayList[position].message
                    holder.itemView.time1.text = getHourAgoTime(
                        convertTimestampToDate(
                            arrayList.get(position).createdAt.toLong(),
                            "yyyy.MM.dd G 'at' HH:mm:ss"
                        )
                    )

                    holder.itemView.layout1.visibility = View.VISIBLE
                    holder.itemView.layout3.visibility = View.GONE
                    holder.itemView.layout4.visibility = View.GONE
                    holder.itemView.layout2.visibility = View.GONE
                }else{
                    Glide.with(context!!).load(Constants.SOCKET_BASE_URL_IMAGE+arrayList[position].message).into(holder.itemView.ivRight)
                /*    holder.itemView.chatleft.text = arrayList.get(position).message*/
                    holder.itemView.time1.text = getHourAgoTime(
                        convertTimestampToDate(
                            arrayList.get(position).createdAt.toLong(),
                            "yyyy.MM.dd G 'at' HH:mm:ss"
                        )
                    )
                    holder.itemView.layout1.visibility = View.GONE
                    holder.itemView.layout3.visibility = View.VISIBLE
                    holder.itemView.layout4.visibility = View.GONE
                    holder.itemView.layout2.visibility = View.GONE


                }
            }
    }
    fun getHourAgoTime(date: String):String {

        try {
            val format = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss")
            val past: Date = format.parse(date)
            val now = Date()
            val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime())
            val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime())
            val hours: Long = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime())
            val days: Long = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime())

            Log.e("(nowdate","---"+past.date+"----"+now.date+"---")

            if (now.date.equals(past.date)) {
                if (seconds < 60) {
                    timeago = "$seconds sec ago"
                } else if (minutes < 60) {
                    timeago = "$minutes min ago"
                } else if (hours < 24) {
                    timeago = "$hours hour ago"
                } else {
                    timeago = "$days days ago"
                }

                //  timeago = "An hour ago"
            } else {
                if (seconds < 60) {
                    timeago = "$seconds sec ago"
                } else if (minutes < 60) {
                    timeago = "$minutes min ago"
                } else if (hours < 24) {
                    timeago = "$hours hour ago"
                } else {
                    timeago = "$days days ago"
                }
            }
                return timeago
            Log.e("chaeckingdate","---"+date+"----"+timeago)
        } catch (j: Exception) {
            j.printStackTrace()
            return timeago
        }
    }

}