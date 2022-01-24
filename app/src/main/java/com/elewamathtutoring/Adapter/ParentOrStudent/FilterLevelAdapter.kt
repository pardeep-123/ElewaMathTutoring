package com.elewamathtutoring.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Model.FilterOptions2Model
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_filteroptions.view.*
import java.util.*

class FilterLevelAdapter(
    c: Context,
    filterLevelList: ArrayList<FilterOptions2Model>
) : RecyclerView.Adapter<FilterLevelAdapter.ViewHolder>() {
    var ctn = c
    var list = filterLevelList

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_filteroptions, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 3
       /* if (list.size != 0) {
            return list.size
        } else {
            return 10
        }*/
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var filterLevelModel = list[position]
        holder.itemView.name.text = filterLevelModel.data
        if (!filterLevelModel.check) {
            holder.itemView.tick.setImageResource(R.drawable.uncheck)
        } else {
            holder.itemView.tick.setImageResource(R.drawable.tick_blue)
        }

        holder.itemView.rl_root.setOnClickListener {
            if (filterLevelModel.check) {
                filterLevelModel.check = false
                notifyItemChanged(position)
            } else {
                filterLevelModel.check = true
                notifyItemChanged(position)
            }
        }
    }
}