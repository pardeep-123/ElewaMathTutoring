package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.ParentOrStudent.resources.ResourcesResponse
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_resources.view.*


class ResourcesAdapter(
    var ctn: Context,
    var list: ArrayList<ResourcesResponse.Body>
) :
    RecyclerView.Adapter<ResourcesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_resources, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
     return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvDescription.setText(list[position].text)
        holder.itemView.tvlink.setText(list[position].link)
        holder.itemView.tvCategory.setText(list[position].categoryName)
        holder.itemView.tvAuthorName.setText(list[position].authname)
    }
}