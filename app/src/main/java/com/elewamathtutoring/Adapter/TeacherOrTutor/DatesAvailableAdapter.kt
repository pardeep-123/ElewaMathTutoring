package com.elewamathtutoring.Adapter.TeacherOrTutor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Model.DatesAvailableModel
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_dates_available.view.*

class DatesAvailableAdapter(private var mylist: ArrayList<DatesAvailableModel>, var sendDays: SendDays) :
    RecyclerView.Adapter<DatesAvailableAdapter.ViewHolder>() {

    /**\
     * @author Pardeep Sharma
     * created interface for sending the days
     */
    interface SendDays{
        fun sendDays(days: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dates_available, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
          return mylist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datesAvailableModel = mylist[position]
        holder.itemView.dayofweek.text = datesAvailableModel.day
       // val poz=position+1

//        for(i in 0 until Selctedarray_date.size){
//            if(poz.toString() == Selctedarray_date[i])
//            {
//                datesAvailableModel.check =true
//                Array_date.add(poz.toString())
//            }
//        }

       if(datesAvailableModel.check){
                holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.white))
                holder.itemView.rlDatesAvailable.background  = ContextCompat.getDrawable(holder.itemView.context,R.drawable.background_blue)
            }
           else
           {
               holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.black))
               holder.itemView.rlDatesAvailable.background = null
           }
       // availablityActivity.Selected_date(Array_date)
        holder.itemView.rlDatesAvailable.setOnClickListener {
            val poz=position+1
            if(datesAvailableModel.check) {
                datesAvailableModel.check = false
                sendDays.sendDays(poz.toString())
//                Array_date.remove(poz.toString())
//                Selctedarray_date.remove(poz.toString())
//                availablityActivity.Selected_date(Array_date)
                notifyItemChanged(position)
            }
            else{
                datesAvailableModel.check =true
                sendDays.sendDays(poz.toString())
//                Array_date.add(poz.toString())
//                Selctedarray_date.add(poz.toString())
//                availablityActivity.Selected_date(Array_date)
                notifyItemChanged(position)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}