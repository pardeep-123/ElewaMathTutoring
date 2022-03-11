package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.ParentOrStudent.filter.FilterActivity
import com.elewamathtutoring.Activity.ParentOrStudent.filter.SubjectsResponse
import com.elewamathtutoring.Adapter.ChooseTimeAdapter
import com.elewamathtutoring.Fragment.ParentOrStudent.search.SearchFragment
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_dates_available.view.*
import kotlinx.android.synthetic.main.item_filter.view.*
import kotlinx.android.synthetic.main.item_filteroptions.view.*
import java.lang.Exception

class FilterAdapter(
    var ctn: Context,
    var list: ArrayList<SubjectsResponse.Body>,
    var timeSlot: FilterAdapter.TimeSlot,
/*    var selected_certified: ArrayList<String>,
    var filterActivity: FilterActivity*/
) :
    RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    var certify_as=ArrayList<String>()
    interface TimeSlot{
        fun ondate(timeId:String)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_filter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvSubjects.setText(list[position].name)
/*
        val filterOptionsModel = list[position]
        try {
            for(i in 0 until selected_certified.size)
            {
                val poz=position+1
                if(selected_certified.get(i).equals(poz.toString()))
                {
                    filterOptionsModel.check=true
                }
            }
        }
        catch (e: Exception)
        {
        }

        if (!filterOptionsModel.check) {
            holder.itemView.tick.setImageResource(R.drawable.uncheck)
        }
        else
        {
            val data= position+1
            certify_as.add(data.toString())
            holder.itemView.tick.setImageResource(R.drawable.tick_blue)
        }

        holder.itemView.rl_root.setOnClickListener {
            val data= position+1
            if (filterOptionsModel.check)
            {
                filterOptionsModel.check = false
                certify_as.remove(data.toString())
                holder.itemView.tick.setImageResource(R.drawable.uncheck)
                // notifyItemChanged(position)
            }
            else
            {
                filterOptionsModel.check = true
                certify_as.add(data.toString())
                holder.itemView.tick.setImageResource(R.drawable.tick_blue)
                // notifyItemChanged(position)
            }
          //  filterActivity.cartified_as(certify_as)
            Log.e("checmylog","---"+certify_as.size)
        }




*/


        if(list[position].check == true) {
            holder.itemView.ivOn.background  = ContextCompat.getDrawable(ctn,R.drawable.checkbox)
        }
        else {
            holder.itemView.ivOn.background = ContextCompat.getDrawable(ctn,R.drawable.uncheck)
        }


        holder.itemView.ivOn.setOnClickListener {
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