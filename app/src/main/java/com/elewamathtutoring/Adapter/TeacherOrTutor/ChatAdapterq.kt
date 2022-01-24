package com.elewamathtutoring.Adapter.TeacherOrTutor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.R
import java.util.*

class ChatAdapterq(c: Context, list: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var ctn = c
    var list = list

    class View1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View1ViewHolder {

        return View1ViewHolder(LayoutInflater.from(ctn).inflate(R.layout.item_chat2, parent, false))
    }

    override fun getItemCount(): Int {
       return 2
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position == 0)
        {

        }
    }
}