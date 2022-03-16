package com.elewamathtutoring.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Fragment.ParentOrStudent.search.SearchFragment
import com.elewamathtutoring.Model.FilterOptions2Model
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_filteroptions.view.*
import java.lang.Exception
import kotlin.collections.ArrayList

class FilterOptions2Adapter(
    c: Context,
    filterList: ArrayList<FilterOptions2Model>,
    var searchFragment: SearchFragment,
    var selected_certified: ArrayList<String>
) :
    RecyclerView.Adapter<FilterOptions2Adapter.ViewHolder>() {
    var ctn = c

    var list = filterList
    var certify_as=ArrayList<String>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_filteroptions, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
         //   return list.size
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filterOptionsModel = list[position]
        holder.itemView.name.text = filterOptionsModel.data
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
        catch (e:Exception)
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
            searchFragment.cartified_as(certify_as)
            Log.e("checmylog","---"+certify_as.size)
        }
    }
}