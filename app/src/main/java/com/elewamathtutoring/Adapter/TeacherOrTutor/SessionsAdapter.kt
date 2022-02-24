package com.elewamathtutoring.Adapter.TeacherOrTutor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.TeacherOrTutor.RequestsActivity
import com.elewamathtutoring.Models.ListView.Body
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.AppUtils
import com.elewamathtutoring.Util.constant.Constants
import kotlinx.android.synthetic.main.item_schedule_pending.view.*
import java.text.SimpleDateFormat

class SessionsAdapter(c: Context, sessionlist: ArrayList<Body>) :
    RecyclerView.Adapter<SessionsAdapter.ViewHolder>() {
    var ctn = c
   var list = sessionlist

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_schedule_pending, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.itemView.tvItemName.text = list.get(position).Student.name
        holder.itemView.tvItemType.setText(Constants.isCertifiedOrtutor(list.get(position).Teacher.isCertifiedOrtutor))
        holder.itemView.tv_start_endtime.text = list.get(position).timeslot.get(0).startTime +"-"+list.get(position).timeslot.get(0).endTime
        Glide.with(ctn).load(Constants.IMAGE_URL+list.get(position).Student.image).placeholder(R.drawable.profile_unselected).into(holder.itemView.ivImage)
      //  holder.itemView.tvItemDate.text = Constants.ConvertTimeStampToDate(list.get(position).createdAt.toLong(),"EEE, MMM yy")
       /* val dateParser = SimpleDateFormat("yyyy-MM-dd")
        val date = dateParser.parse(list.get(position).date)
        val dateFormatter = SimpleDateFormat("EEE, MMM dd")
        holder.itemView.tvItemDate.text=dateFormatter.format(date).toString()
*/
        holder.itemView.tvItemDate.text= AppUtils.secondsToTimeStamp(list.get(position).date.toLong(),"EEE, MMM dd")
        holder.itemView.setOnClickListener {
            val intent = Intent(ctn, RequestsActivity::class.java)
            intent.putExtra("from", "Sessions")
            intent.putExtra("id",list.get(position).id.toString())
            ctn.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size

    }
}