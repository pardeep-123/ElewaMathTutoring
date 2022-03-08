package com.elewamathtutoring.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.elewamathtutoring.Activity.TeacherOrTutor.request.RequestDetailResponse
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_dates_available.view.*
import kotlin.collections.ArrayList

class ChooseTimeAdapter(
   var ctn: Context,
    var list: ArrayList<RequestDetailResponse.Body>
) : RecyclerView.Adapter<ChooseTimeAdapter.ViewHolder>() {
    var selectedtme_position=ArrayList<String>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_dates_available, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
         return  list[0].timeslot.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.dayofweek.text = list[0].timeslot[position].startTime+"-"+list[0].timeslot[position].endTime

  /*      if(list.get(0).timeslot[position].check == true)
        {
            holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(ctn,R.color.white))
            holder.itemView.rlDatesAvailable.background  = ContextCompat.getDrawable(ctn,R.drawable.background_blue)
        }
        else
        {
            holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(ctn,R.color.textcolor))
            holder.itemView.rlDatesAvailable.background = null
        }*/

        holder.itemView.rlDatesAvailable.setOnClickListener {
            if (list.get(0).timeslot.get(position).check) {
                list.get(0).timeslot[position].check= true

                notifyItemChanged(position)
            } else {
                list.get(0).timeslot[position].check = true

                notifyItemChanged(position)
            }

        }
    }
}