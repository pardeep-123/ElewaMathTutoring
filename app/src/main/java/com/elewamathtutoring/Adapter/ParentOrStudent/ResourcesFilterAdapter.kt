package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.ParentOrStudent.resources.CategoriesResponse
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_resources_filter.view.*

class ResourcesFilterAdapter(var ctn: Context, var list: ArrayList<CategoriesResponse.Body>,
                             var id:Id
) :
    RecyclerView.Adapter<ResourcesFilterAdapter.ViewHolder>() {
    interface Id{
        fun onFilter(id:String)
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_resources_filter, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvBooks.text = list[position].name
        holder.itemView.tvBooks.setOnClickListener {
//            if (list[position].check) {
//                list[position].check= false
//                id.onFilter(list[position].id.toString())
//                notifyDataSetChanged()
//            } else {
//                list[position].check = true
                id.onFilter(list[position].id.toString())
                notifyDataSetChanged()
          //  }
        }
    }
}