package com.elewamathtutoring.Adapter.TeacherOrTutor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.TeacherOrTutor.AvailablityActivity
import com.elewamathtutoring.Model.DatesAvailableModel
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_dates_available.view.*

class DatesAvailableAdapter(
    mylist: ArrayList<DatesAvailableModel>,
   var Selctedarray_date: ArrayList<String>,
    var availablityActivity: AvailablityActivity
) :
    RecyclerView.Adapter<DatesAvailableAdapter.ViewHolder>() {
    var list = mylist
    var Array_date=ArrayList<String>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dates_available, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
           // return list.size
        return 7
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datesAvailableModel = list[position]
        holder.itemView.dayofweek.text = datesAvailableModel.day
        val poz=position+1

        for(i in 0 until Selctedarray_date.size)
        {
            if(poz.toString().equals(Selctedarray_date.get(i)))
            {
                datesAvailableModel.check =true
                Array_date.add(poz.toString())
            }
        }

       if(datesAvailableModel.check)
            {
                holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.white))
                holder.itemView.rlDatesAvailable.background  = ContextCompat.getDrawable(holder.itemView.context,R.drawable.background_blue)
            }
           else
           {
               holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.textcolor))
               holder.itemView.rlDatesAvailable.background = null
           }
        availablityActivity.Selected_date(Array_date)
        holder.itemView.rlDatesAvailable.setOnClickListener {
            val poz=position+1
            if(datesAvailableModel.check)
            {
                datesAvailableModel.check = false
                Array_date.remove(poz.toString())
                Selctedarray_date.remove(poz.toString())
                availablityActivity.Selected_date(Array_date)
                notifyItemChanged(position)
            }
            else{
                datesAvailableModel.check =true
                Array_date.add(poz.toString())
                Selctedarray_date.add(poz.toString())
                availablityActivity.Selected_date(Array_date)
                notifyItemChanged(position)
            }
        }
    }
}