package com.elewamathtutoring.Adapter.TeacherOrTutor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Models.ListView.Body
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_hadder.view.*

class Hadder_sessionsadapter(
    c: Context,
    var today: ArrayList<Body>,
    var upcomming: ArrayList<Body>,
    var title: ArrayList<String>
) :
    RecyclerView.Adapter<Hadder_sessionsadapter.ViewHolder>() {
    var ctn = c

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_hadder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_titleSession.text = title.get(position)
        if(title.get(position).equals("TODAY'S SESSIONS")) {
            upcomming.reverse()
            holder.itemView.recycler_data.adapter = SessionsAdapter(ctn, today)
        } else {
            upcomming.reverse()
            holder.itemView.recycler_data.adapter = SessionsAdapter(ctn, upcomming)
        }
        //holder.itemView.tvItemType.text = list.get(0).body.get(position).
    }
    override fun getItemCount(): Int {
        //return title.size
        return 3
    }
}