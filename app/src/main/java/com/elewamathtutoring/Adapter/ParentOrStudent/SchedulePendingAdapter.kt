package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.TeacherOrTutor.request.RequestsActivity
import com.elewamathtutoring.Adapter.ClickCallBack
import com.elewamathtutoring.Fragment.TeacherOrTutor.request.RequestListResponse
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.AppUtils
import com.elewamathtutoring.Util.constant.Constants
import kotlinx.android.synthetic.main.item_schedule_pending.view.*


class SchedulePendingAdapter(c: Context, s: ArrayList<RequestListResponse.Body>,
                             var clickCallBack: ClickCallBack
) : RecyclerView.Adapter<SchedulePendingAdapter.ViewHolder>() {
    var ctn = c
    var list = s
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_schedule_pending, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvItemName.text = list.get(position).Student.name
        holder.itemView.tvItemType.setText(Constants.personVirtual(list.get(position).personVirtual))
        try {
            holder.itemView.tv_start_endtime.text = list.get(position).timeslot.get(0).startTime +"-"+list.get(position).timeslot.get(0).endTime
        }catch (e:Exception)
        { }

        Glide.with(ctn).load(list.get(position).Student.image)
            .placeholder(R.drawable.profile_unselected).into(holder.itemView.ivImage)
      //   holder.itemView.tvItemDate.text=AppUtils.secondsToTimeStamp(list.get(position).date.toLong(),"EEE, MMM dd")

        holder.itemView.setOnClickListener {
            val intent = Intent(ctn, RequestsActivity::class.java)
            intent.putExtra("from", "request")
             intent.putExtra("id", list[position].id.toString())
            ctn.startActivity(intent)
        }
        holder.itemView.btnAccept.setOnClickListener {
            clickCallBack.onItemClick(position, "accept")
        }
        holder.itemView.btnReject.setOnClickListener {
            clickCallBack.onItemClick(position, "reject")
        }
    }
}