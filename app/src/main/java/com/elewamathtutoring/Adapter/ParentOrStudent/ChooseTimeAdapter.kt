package com.elewamathtutoring.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.ParentOrStudent.teacherDetail.TeacherDetailResponse
import com.elewamathtutoring.Activity.ScheduleASessionActivity
import com.elewamathtutoring.R
import kotlin.collections.ArrayList
//     chooseTimeList: ArrayList<Body>,
//  var  scheduleASessionActivity: ScheduleASessionActivity

class ChooseTimeAdapter(
    c: Context,
  var  profile: ArrayList<TeacherDetailResponse.Body>,
    scheduleASessionActivity: ScheduleASessionActivity
) : RecyclerView.Adapter<ChooseTimeAdapter.ViewHolder>() {
    var ctn = c
   // var list = chooseTimeList
    var selectedtme_position=ArrayList<String>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_dates_available, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        //  return  list.get(0).time_slots.size
        return 9
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       /* holder.itemView.dayofweek.text = list.get(0).time_slots.get(position).startTime+"-"+list.get(0).time_slots.get(position).endTime


        if(list.get(0).time_slots.get(position).check==true)
        {
            holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(ctn,R.color.white))
            holder.itemView.rlDatesAvailable.background  = ContextCompat.getDrawable(ctn,R.drawable.background_blue)
        }
        else
        {
            holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(ctn,R.color.textcolor))
            holder.itemView.rlDatesAvailable.background = null
        }

        holder.itemView.rlDatesAvailable.setOnClickListener {
            if (list.get(0).time_slots.get(position).check) {
                list.get(0).time_slots.get(position).check = false

                notifyItemChanged(position)
            } else {
                list.get(0).time_slots.get(position).check = true

                notifyItemChanged(position)
            }

        }*/
    }
}