package com.elewamathtutoring.Adapter.TeacherOrTutor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.TeacherOrTutor.request.RequestsActivity
import com.elewamathtutoring.Models.Card_listing.AllSessionListResponse
import com.elewamathtutoring.Models.ListView.Model_myschdeullist
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import kotlinx.android.synthetic.main.item_calender.view.*


class SessionsAdapter(c: Context, sessionlist: ArrayList<AllSessionListResponse.Body>) :
    RecyclerView.Adapter<SessionsAdapter.ViewHolder>() {
    var ctn = c
   var list = sessionlist
//19080
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_calender, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        if(list[position].Student!=null){
            holder.itemView.tvItemName.text = list[position].Student.name


        holder.itemView.tvItemType.setText(Constants.isCertifiedOrtutor(list.get(position).Teacher.isCertifiedOrtutor))
//      if (list[position].timeslot.isNotEmpty())
//        holder.itemView.tv_start_endtime.text = list[position].timeslot[0].startTime +"-"+ list[position].timeslot[0].endTime
        Glide.with(ctn).load(list.get(position).Student.image).placeholder(R.drawable.profile_unselected).into(holder.itemView.ivImage)
       // holder.itemView.tvItemDate.text = Constants.ConvertTimeStampToDate(list.get(position).date.toLong(),"EEE, MMM yy")
  holder.itemView.tvItemDate.text = Constants.convertDateToRatingTime(list[position].date.toLong())

        }
      //  holder.itemView.tvItemDate.text= AppUtils.getDate(list.get(position).date,"EEE, MMM dd")
//        holder.itemView.tvItemDate.text= list.get(position).date
        holder.itemView.setOnClickListener {
            val intent = Intent(ctn, RequestsActivity::class.java)
            intent.putExtra("from", "Sessions")
            intent.putExtra("id",list.get(position).id.toString())
            ctn.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}