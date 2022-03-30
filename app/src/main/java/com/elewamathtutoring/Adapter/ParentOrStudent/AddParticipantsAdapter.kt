package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.provider.SyncStateContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Model.GroupListModel
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import kotlinx.android.synthetic.main.activity_signup_teacher.*
import kotlinx.android.synthetic.main.item_add_participants.view.*
import java.util.ArrayList

class AddParticipantsAdapter(
    c: Context,
    var list: ArrayList<GroupListModel.GroupListModelItem>,
    var groupId: GroupId
) :
    RecyclerView.Adapter<AddParticipantsAdapter.ViewHolder>() {
    var ctn = c

    interface GroupId {
        fun groupIds(id: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_add_participants, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (list[position].isCheck==true)
            holder.itemView.ivOf.isChecked = true
        else
            holder.itemView.ivOf.isChecked = false

        holder.itemView.ivOf.setOnCheckedChangeListener { _, b ->
            if (b) {
                list[position].isCheck = true
                groupId.groupIds(list[position].id)
            } else {
                list[position].isCheck = false
                groupId.groupIds(list[position].id)
            }
          //  notifyDataSetChanged()
        }
        holder.itemView.name.text = list[position].name
        Glide.with(ctn).load(Constants.SOCKET_BASE_URL_IMAGE_USER + list[position].image)
            .into(holder.itemView.ivProfile)


    }
}