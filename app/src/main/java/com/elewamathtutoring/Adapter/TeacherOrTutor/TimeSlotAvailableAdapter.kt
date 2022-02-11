package com.elewamathtutoring.Adapter.TeacherOrTutor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.TeacherOrTutor.AvailablityActivity
import com.elewamathtutoring.Models.Time_slots.Body
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_dates_available.view.*

class TimeSlotAvailableAdapter(
    list: ArrayList<Body>,
   var Selctedarray_time: ArrayList<String>,
    var availablityActivity: AvailablityActivity
) :
    RecyclerView.Adapter<TimeSlotAvailableAdapter.ViewHolder>() {
    var arrayList = list
    var Array_time=ArrayList<String>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_dates_available, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
         //   return arrayList.size
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var timeSlotsAvailableModel = arrayList[position]
        holder.itemView.dayofweek.text = timeSlotsAvailableModel.startTime+"-"+timeSlotsAvailableModel.endTime


        for(i in 0 until Selctedarray_time.size)
        {
            if(arrayList.get(position).id.toString().equals(Selctedarray_time.get(i)))
            {
                timeSlotsAvailableModel.check =true
                Array_time.add(arrayList.get(position).id.toString())
            }
        }

        if (timeSlotsAvailableModel.check) {
            holder.itemView.rlDatesAvailable.setBackgroundResource(R.drawable.background_blue)
            holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        }
        else
        {
            holder.itemView.rlDatesAvailable.background = null
            holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        }
        availablityActivity.Selected_time(Array_time)
        holder.itemView.rlDatesAvailable.setOnClickListener {
            if (timeSlotsAvailableModel.check)
            {
                timeSlotsAvailableModel.check = false
                Array_time.remove(timeSlotsAvailableModel.id.toString())
                Selctedarray_time.remove(timeSlotsAvailableModel.id.toString())

                availablityActivity.Selected_time(Array_time)
                notifyItemChanged(position)
            }
            else
            {
                timeSlotsAvailableModel.check = true
                Array_time.add(timeSlotsAvailableModel.id.toString())
                Selctedarray_time.add(timeSlotsAvailableModel.id.toString())

                availablityActivity.Selected_time(Array_time)
                notifyItemChanged(position)
            }
        }
    }
}