package com.elewamathtutoring.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.TeacherDetailsActivity
import com.elewamathtutoring.Models.Search.Body
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import kotlinx.android.synthetic.main.home_list.view.*
import kotlin.collections.ArrayList

class SearchHomeAdapter(
    c: Context, var list: ArrayList<Body>,
) : RecyclerView.Adapter<SearchHomeAdapter.MyHolder>() {
    var ctn = c


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView: View =
            LayoutInflater.from(ctn).inflate(R.layout.home_list, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.tvItemName.text=list.get(0).data.get(position).name
        holder.itemView.tvPostion.text= Constants.isCertifiedOrtutor(list.get(0).data.get(position).isCertifiedOrtutor)
        Glide.with(ctn).load(list.get(0).data.get(position).image).placeholder(R.drawable.profile_unselected).into(holder.itemView.ivTeacher)

        for(i in 0 until list.get(0).data.get(position).teaching_level.size)
        {
            var data=list.get(0).data.get(position).teaching_level.get(i).level+","
            holder.itemView.tv_level.text=holder.itemView.tv_level.text.toString()+data
        }

        var s = holder.itemView.tv_level.text.toString()
        holder.itemView.tv_level.text = s.substring(0, s.length -1)

        holder.itemView.home_listcardview.setOnClickListener {
            var intent = Intent(ctn, TeacherDetailsActivity::class.java)
            intent.putExtra("teacher_id",""+list.get(0).data.get(position).id.toString())
            intent.putExtra("Type","searchhome")
            ctn.startActivity(intent)
        }
          //  ctn.startActivity(Intent(ctn,TeacherDetailsActivity::class.java)) }
    }

    override fun getItemCount(): Int {
       // return list.get(0).data.size
        return 3
    }

    class MyHolder(v: View) : RecyclerView.ViewHolder(v) {

    }
}