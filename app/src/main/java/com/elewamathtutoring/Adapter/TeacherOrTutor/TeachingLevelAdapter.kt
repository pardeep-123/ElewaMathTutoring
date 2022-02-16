package com.elewamathtutoring.Adapter.TeacherOrTutor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfo.TeachingLevelResponse
import com.elewamathtutoring.Models.Teacher_level.Body
import com.elewamathtutoring.R
import com.riseball.interface_base.Teachinglevel_interface
import kotlinx.android.synthetic.main.item_filteroptions.view.*
import java.lang.Exception
import kotlin.collections.ArrayList
//
class TeachingLevelAdapter(
    var ctn: Context,
    var list: ArrayList<TeachingLevelResponse.Body>,
    var teachinglevel: ArrayList<String>,
    var teachingInfoActivity: Teachinglevel_interface
) :
    RecyclerView.Adapter<TeachingLevelAdapter.ViewHolder>() {
    var poz = -1
    var Level_list = ArrayList<String>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_signupcheckbox, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
        // return 3
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.name.setText(list.get(position).level)
        try {
            for (i in 0 until teachinglevel.size) {
                if (teachinglevel.get(i).equals(list.get(position).id.toString())) {
                    holder.itemView.tick.setImageResource(R.drawable.tick_blue)
                    Level_list.add(list.get(position).id.toString())
                }
            }
        } catch (e: Exception) {
        }
        holder.itemView.setOnClickListener {
            if (holder.itemView.tick.getDrawable().getConstantState() == ctn.getResources()
                    .getDrawable(R.drawable.uncheck).getConstantState()
            ) {
                holder.itemView.tick.setImageResource(R.drawable.checkbox)
                Level_list.add(list.get(position).id.toString())
            } else {
                holder.itemView.tick.setImageResource(R.drawable.uncheck)
                Level_list.remove(list.get(position).id.toString())
            }
          //  Teacher_level(Level_list)
        }
    }
  /*  fun Teacher_level(Level_list: ArrayList<String>) {
        teachingInfoActivity.Teachinglevel(Level_list)
    }*/
}