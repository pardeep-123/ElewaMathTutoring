package com.elewamathtutoring.Adapter.ParentOrTeacher

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.TeacherDetailsActivity

import com.elewamathtutoring.Models.My_Schedule.Body
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import kotlinx.android.synthetic.main.item_schedule_pending.view.*
import kotlinx.android.synthetic.main.item_schedule_pending.view.tvItemName
import java.text.SimpleDateFormat

class UpcomingSessionsAdapter(c: Context, pendinglist: ArrayList<Body>, i: Int) :
    RecyclerView.Adapter<UpcomingSessionsAdapter.ViewHolder>() {
    var ctn = c
    var type = i
    var list = pendinglist

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_schedule_pending, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      /*  if(type==1)
        {
            return  list.get(0).Pending_sessions.size
        }
        else if(type==2)
        {
            return   list.get(0).Upcoming_sessions.size
        }
        else
        {
          return  list.get(0).Today_sessions.size
        }*/
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
   try {
       val dateParser = SimpleDateFormat("yyyy-MM-dd")
        if(type==1)
        {
            holder.itemView.tvItemName.setText(list.get(0).Pending_sessions[position].Teacher.name)
            holder.itemView.tvItemType.setText(Constants.isCertifiedOrtutor(list.get(0).Pending_sessions[position].Teacher.isCertifiedOrtutor))
            Log.e("checking_id","---"+list.get(0).Pending_sessions[position].id.toString()+"----"+list.get(0).Pending_sessions[position].Teacher.name)
            Glide.with(ctn).load(Constants.IMAGE_URL+list.get(0).Pending_sessions[position].Teacher.image).placeholder(R.drawable.profile_unselected).
            into(holder.itemView.ivImage)
            val date = dateParser.parse(list.get(0).Pending_sessions[position].date)
            val dateFormatter = SimpleDateFormat("EEE, MMM dd")
            holder.itemView.tvItemDate.text=dateFormatter.format(date).toString()

            holder.itemView.tv_start_endtime.text=list.get(0).Pending_sessions[position].timeslot.get(0).startTime+" - "+list.get(0).Pending_sessions[position].timeslot.get(0).endTime

           // holder.itemView.tvItemDate.text = Constants.ConvertTimeStampToDate(list.get(0).Pending_sessions[position].createdAt.toLong(),"EEE, MMM yy")
        }
        else if(type==2)
        {
            holder.itemView.tvItemName.setText(list.get(0).Upcoming_sessions[position].Teacher.name)
            holder.itemView.tvItemType.setText(Constants.isCertifiedOrtutor(list.get(0).Upcoming_sessions[position].Teacher.isCertifiedOrtutor))
            Glide.with(ctn).load(Constants.IMAGE_URL+list.get(0).Upcoming_sessions[position].Teacher.image).placeholder(R.drawable.profile_unselected).into(holder.itemView.ivImage)
            holder.itemView.tvItemDate.text = Constants.ConvertTimeStampToDate(list.get(0).Upcoming_sessions[position].createdAt.toLong(),"EEE, MMM yy")
            holder.itemView.tv_start_endtime.text=list.get(0).Upcoming_sessions[position].timeslot.get(0).startTime+" - "+list.get(0).Upcoming_sessions[position].timeslot.get(0).endTime

        }
        else
        {
            holder.itemView.tvItemName.setText(list.get(0).Today_sessions[position].Teacher.name)
            holder.itemView.tvItemType.setText(Constants.isCertifiedOrtutor(list.get(0).Today_sessions[position].Teacher.isCertifiedOrtutor))
            Glide.with(ctn).load(Constants.IMAGE_URL+list.get(0).Today_sessions[position].Teacher.image).placeholder(R.drawable.profile_unselected).into(holder.itemView.ivImage)
            holder.itemView.tvItemDate.text = Constants.ConvertTimeStampToDate(list.get(0).Today_sessions[position].updatedAt.toLong(),"EEE, MMM yy")
            holder.itemView.tv_start_endtime.text=list.get(0).Today_sessions[position].timeslot.get(0).startTime+" - "+list.get(0).Today_sessions[position].timeslot.get(0).endTime

        }

}catch (e:Exception)
{

}
        holder.itemView.schedulePendingRoot.setOnClickListener {
            if(type==2)
            {
                val intent = Intent(ctn, TeacherDetailsActivity::class.java)
                intent.putExtra("teacher_id", list.get(0).Upcoming_sessions[position].Teacher.id.toString())
                intent.putExtra("session_id", list.get(0).Upcoming_sessions[position].id.toString())
                intent.putExtra("Type","schedule")
                ctn.startActivity(intent)
            }
        }
    }
}