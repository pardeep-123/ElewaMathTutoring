package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.R


class DetailAdapter(var ctn: Context) :
    RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_detail, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return 3
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }
}