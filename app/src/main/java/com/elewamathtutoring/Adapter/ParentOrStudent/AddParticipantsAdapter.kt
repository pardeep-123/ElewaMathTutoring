package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.Chat.mathChat.MathPersonChatActivity
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_add_participants.view.*
import kotlinx.android.synthetic.main.item_filteroptions.view.*


//, listNotifications: ArrayList<Body>
class AddParticipantsAdapter(c: Context) :
    RecyclerView.Adapter<AddParticipantsAdapter.ViewHolder>() {
    var ctn = c
   // var list = listNotifications
   class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_add_participants, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       // return list.size
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        /*holder.itemView.setOnClickListener {
            if (holder.itemView.tick.getDrawable().getConstantState() == ctn.getResources().getDrawable( R.drawable.uncheck).getConstantState()) {
                holder.itemView.tick.setImageResource(R.drawable.checkbox)
                //  Level_list.add(list.get(position).id.toString())
            }
            else {
                holder.itemView.tick.setImageResource(R.drawable.uncheck)
                // Level_list.remove(list.get(position).id.toString())
            }
        }*/
     /*   holder.itemView.rlLayout.setOnClickListener {
            var intent = Intent(ctn, MathPersonChatActivity::class.java)
            ctn.startActivity(intent)
        }*/
    }
}