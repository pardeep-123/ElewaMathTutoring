package com.elewamathtutoring.Adapter.TeacherOrTutor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.Chat.mathChat.MathPersonChatActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.RequestsActivity
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_add_participants.view.*
import kotlinx.android.synthetic.main.item_filteroptions.view.*


//, listNotifications: ArrayList<Body>
class RequestAdapter(c: Context) :
    RecyclerView.Adapter<RequestAdapter.ViewHolder>() {
    var ctn = c
   // var list = listNotifications
   class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_request, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       // return list.size
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(ctn, RequestsActivity::class.java)
            intent.putExtra("from", "request")
            //  intent.putExtra("id",list.get(position).id.toString())
            ctn.startActivity(intent)
        }
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