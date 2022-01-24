package com.elewamathtutoring.Adapter.TeacherOrTutor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.TeacherDetailsActivity
import com.elewamathtutoring.Models.Search.Body
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_searchresult.view.*
import kotlin.collections.ArrayList

class Teaching_searchresultAdapter(
    conx: Context,
    mylist: ArrayList<Body>
) :
    RecyclerView.Adapter<Teaching_searchresultAdapter.ViewHolder>() {
    var ctn = conx
    var list = mylist
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_searchresult, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
           // return list.size
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.itemView.tv_resultname.text = list.get(0).data.get(position).name

        holder.itemView.setOnClickListener {
            var intent = Intent(ctn, TeacherDetailsActivity::class.java)
            intent.putExtra("teacher_id",""+list.get(0).data.get(position).id.toString())
            intent.putExtra("Type","techersearch")
            ctn.startActivity(intent)
        }
    }


}