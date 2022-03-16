package com.elewamathtutoring.Adapter.TeacherOrTutor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.TeacherOrTutor.availability.TimeSlotsResponse
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_dates_available.view.*

class TimeSlotAvailableAdapter(var list: ArrayList<TimeSlotsResponse.Body>, var timeSlot: TimeSlot) :
    RecyclerView.Adapter<TimeSlotAvailableAdapter.ViewHolder>() {

    interface TimeSlot{
        fun ondate(timeId:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_dates_available, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val timeSlotsAvailableModel = list[position]
        holder.itemView.dayofweek.text = timeSlotsAvailableModel.startTime+"-"+timeSlotsAvailableModel.endTime


//        for(i in 0 until Selctedarray_time.size)
//        {
//            if(list[position].id.toString().equals(Selctedarray_time[i])) {
//                timeSlotsAvailableModel.check =true
//                Array_time.add(list[position].id.toString())
//            }
//        }

        if (timeSlotsAvailableModel.check) {
            holder.itemView.rlDatesAvailable.setBackgroundResource(R.drawable.background_blue)
            holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        }
        else
        {
            holder.itemView.rlDatesAvailable.background = null
            holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        }
      //  availablityActivity.Selected_time(Array_time)
        holder.itemView.rlDatesAvailable.setOnClickListener {
            if (timeSlotsAvailableModel.check)
            {
                timeSlotsAvailableModel.check = false
                timeSlot.ondate(timeSlotsAvailableModel.id.toString())
//                Array_time.remove(timeSlotsAvailableModel.id.toString())
//                Selctedarray_time.remove(timeSlotsAvailableModel.id.toString())
//                availablityActivity.Selected_time(Array_time)
                //notifyItemChanged(position)
                notifyDataSetChanged()
            }
            else {
                timeSlotsAvailableModel.check = true
                timeSlot.ondate(timeSlotsAvailableModel.id.toString())

//                Array_time.add(timeSlotsAvailableModel.id.toString())
//                Selctedarray_time.add(timeSlotsAvailableModel.id.toString())
//                availablityActivity.Selected_time(Array_time)
               // notifyItemChanged(position)
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

}