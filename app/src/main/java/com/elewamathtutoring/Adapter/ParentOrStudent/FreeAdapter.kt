package com.elewamathtutoring.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.ParentOrStudent.session.ScheduleASessionActivity
import com.elewamathtutoring.Activity.ParentOrStudent.teacherDetail.TeacherDetailResponse

import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_dates_available.view.*
import kotlin.collections.ArrayList

class FreeAdapter(
    var ctn: Context,
    var list: ArrayList<TeacherDetailResponse.Body.FreeSlots>,
    var timeSlot: ScheduleASessionActivity
) : RecyclerView.Adapter<FreeAdapter.ViewHolder>() {
    interface TimeSlot{
        fun ondate(timeId:String)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_dates_available, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
         return  list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.dayofweek.text = list[position].startTime+"-"+list[position].endTime
        if(list[position].check) {
            holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(ctn,R.color.white))
            holder.itemView.rlDatesAvailable.background  = ContextCompat.getDrawable(ctn,R.drawable.background_blue)
        }
        else {
            holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(ctn,R.color.black))
            holder.itemView.rlDatesAvailable.background = null
        }
        holder.itemView.rlDatesAvailable.setOnClickListener {
            if (list[position].check) {
                list[position].check= false
                timeSlot.ondate(list[position].id.toString())
                notifyDataSetChanged()
            } else {
                list[position].check = true
                timeSlot.ondate(list[position].id.toString())
                notifyDataSetChanged()
            }
        }
    }
}