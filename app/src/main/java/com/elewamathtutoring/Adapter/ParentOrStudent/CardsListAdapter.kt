package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
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
                       var typescreen: String) :
    RecyclerView.Adapter<CardsListAdapter.ViewHolder>() {
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
    }


}