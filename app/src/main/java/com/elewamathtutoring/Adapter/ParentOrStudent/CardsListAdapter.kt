package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.elewamathtutoring.Activity.ParentOrStudent.add_card.AddCardActivity
import com.elewamathtutoring.Activity.ParentOrStudent.payment.CardListingResponse
import com.elewamathtutoring.Activity.ParentOrStudent.payment.PaymentInfoActivity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.AppUtils.Companion.getCardType
import kotlinx.android.synthetic.main.item_cards.view.*

import kotlin.collections.ArrayList

class CardsListAdapter(var ctn: Context,
                       var cardlist: ArrayList<CardListingResponse.Body>,
                       var paymentInfoActivity: PaymentInfoActivity,
                       var typescreen: String,
                       var teachinglevel: ArrayList<String>, 
                       var timeSlot: TimeSlot) :
    RecyclerView.Adapter<CardsListAdapter.ViewHolder>() {
    interface TimeSlot{
        fun ondate(timeId:String)
    }
    var Level_list = ArrayList<String>()
    private val viewBinderHelper = ViewBinderHelper()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_cards, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
       return cardlist.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewBinderHelper.bind(holder.itemView.swipeRevealLayout, null);
        viewBinderHelper.setOpenOnlyOne(true)
       val lastFourDigits = cardlist.get(position).card_number.substring(cardlist.get(position).card_number.length - 4)


        if (getCardType(cardlist.get(position).card_number)!!.equals("0"))
        {
            holder.itemView.tvVisa.text="Visa XXXX-XXXX-XXXX"+lastFourDigits
        }else if (getCardType(cardlist.get(position).card_number)!!.equals("1")){
            holder.itemView.tvVisa.text="Mastercard XXXX-XXXX-XXXX"+lastFourDigits
        }else{
            holder.itemView.tvVisa.text="Mastercard XXXX-XXXX-XXXX"+lastFourDigits
        }


        holder.itemView.tvName.text=cardlist.get(position).holder_name.toString()

        holder.itemView.rel_card.setOnClickListener {
            Log.e("checkmylog","--dmfk")
          if(typescreen.equals("frombooking"))
          {
            paymentInfoActivity.ThanksForBookingDialog(cardlist.get(position).id.toString())
          }

        }

        holder.itemView.rlDeleteCard.setOnClickListener {
            paymentInfoActivity.deleteDialog(cardlist.get(position).id.toString())
        }

        holder.itemView.llEdit.setOnClickListener {
            var intent = Intent(ctn, AddCardActivity::class.java)
            intent.putExtra("type","edit")
            intent.putExtra("card_id",cardlist.get(position).id.toString())
            intent.putExtra("holder_name",cardlist.get(position).holder_name.toString())
            intent.putExtra("card_number",cardlist.get(position).card_number.toString())
            intent.putExtra("expiry_month",cardlist.get(position).expiry_month.toString())
            intent.putExtra("expiry_year",cardlist.get(position).expiry_year.toString())
            ctn.startActivity(intent)
        }
        if(cardlist[position].check == true)
        {
            holder.itemView.ivCheck.setImageResource(R.drawable.payment_checkfill)
            Level_list.add(cardlist.get(position).id.toString())
          //  holder.itemView.dayofweek.setTextColor(ContextCompat.getColor(ctn,R.color.white))
          //  holder.itemView.rlDatesAvailable.background  = ContextCompat.getDrawable(ctn,R.drawable.background_blue)
        }
        else {
            holder.itemView.ivCheck.setImageResource(R.drawable.payment_checkunfill)
            Level_list.remove(cardlist.get(position).id.toString())
        }
        holder.itemView.llSelect.setOnClickListener {
            if (cardlist[position].check) {
                cardlist[position].check= false
                timeSlot.ondate(cardlist[position].id.toString())
                notifyDataSetChanged()
            } else {
                cardlist[position].check = true
                timeSlot.ondate(cardlist[position].id.toString())
                notifyDataSetChanged()
            }
        }

      /*  try {
            for (i in 0 until teachinglevel.size) {
                if (teachinglevel.get(i).equals(cardlist[position].id.toString())) {
                    holder.itemView.ivCheck.setImageResource(R.drawable.payment_checkfill)
                    Level_list.add(cardlist.get(position).id.toString())
                }
            }
        } catch (e: Exception) {
        }
        holder.itemView.setOnClickListener {
            if (holder.itemView.ivCheck.getDrawable().getConstantState() == ctn.getResources()
                    .getDrawable(R.drawable.payment_checkunfill).getConstantState()
            ) {
                holder.itemView.ivCheck.setImageResource(R.drawable.payment_checkfill)
                Level_list.add(cardlist.get(position).id.toString())
            } else {
                holder.itemView.ivCheck.setImageResource(R.drawable.payment_checkunfill)
                Level_list.remove(cardlist.get(position).id.toString())
            }
            //  Teacher_level(Level_list)
        }*/
    }
}