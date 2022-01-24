package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Model.FilterOptionsModel
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_filteroptions.view.*
import java.util.*

class FilterOptionsAdapter(c: Context, list: ArrayList<FilterOptionsModel>) :
    RecyclerView.Adapter<FilterOptionsAdapter.ViewHolder>() {
    var ctn = c
    var list = list

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_filteroptions, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       /* if (list.size == 0) {
            return list.size
        } else {
            return 2
        }*/
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var filterList = list[position]
        holder.itemView.name.text = filterList.data

        if (!filterList.check) {
            holder.itemView.tick.setImageResource(R.drawable.uncheck)
        } else {
            holder.itemView.tick.setImageResource(R.drawable.tick_blue)
        }

        holder.itemView.rl_root.setOnClickListener {
            if (filterList.check) {
                filterList.check = false
                notifyItemChanged(position)
            } else {
                filterList.check = true
                notifyItemChanged(position)
            }

        }
    }
}