package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Models.Wallet.Body
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.constant.Constants.Companion.ConvertTimeStampToDate
import kotlinx.android.synthetic.main.item_history.view.*
import java.util.*

class TransactionAdapter(c: Context, wallat_list: ArrayList<Body>, sizee:String) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    var ctn = c
    var size = sizee
    var list = wallat_list
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       /* if(size.equals("showonly5"))
        {
            if(list.get(0).wallet_history.size>=5)
            {
                return 5
            }
            else
            {
                return list.get(0).wallet_history.size
            }
        }
        else
        {
            return list.get(0).wallet_history.size
        }*/
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_amount.text=Constants.Currency+list.get(0).wallet_history.get(position).amount.toString()
        holder.itemView.tvDate.text=ConvertTimeStampToDate(list.get(0).wallet_history.get(position).createdAt.toLong(),"MMMM mm, yyyy HH:mm a")

        if(list.get(0).wallet_history.get(position).status==0)
        {
            holder.itemView.tv_status.text="Pending"
        }
        else
        {
            holder.itemView.tv_status.text="Successfull"
        }
    }
}