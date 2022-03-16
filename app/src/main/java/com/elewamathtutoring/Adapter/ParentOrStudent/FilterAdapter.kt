package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView

import com.elewamathtutoring.Activity.ParentOrStudent.filter.SubjectsResponse

import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_filter.view.*

import java.lang.Exception

class FilterAdapter(
    var ctn: Context,
    var list: ArrayList<SubjectsResponse.Body>,
    var timeSlot: FilterAdapter.TimeSlot
    //var selected_certified: ArrayList<String>
    //var filterActivity: FilterActivity
) :
    RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    var certify_as=ArrayList<String>()
    interface TimeSlot{
        fun ondate(timeId:String,name:String)
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

        if(list[position].check == true) {
            holder.itemView.ivOn.background  = ContextCompat.getDrawable(ctn,R.drawable.checkbox)
        }
        else {
            holder.itemView.ivOn.background = ContextCompat.getDrawable(ctn,R.drawable.uncheck)
        }

        holder.itemView.ivOn.setOnClickListener {
            if (list[position].check) {
                list[position].check= false
                timeSlot.ondate(list[position].id.toString(),list[position].name)
                notifyDataSetChanged()
            } else {
                list[position].check = true
                timeSlot.ondate(list[position].id.toString(),list[position].name)

                notifyDataSetChanged()
                }
            }
        }
    }
