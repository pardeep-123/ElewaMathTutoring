package com.elewamathtutoring.Adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.AddBankAccountActivity
import com.elewamathtutoring.Activity.MyBankAccountsActivity
import com.elewamathtutoring.Models.BankAccountsModel.Body
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.dialog_delete.*
import kotlinx.android.synthetic.main.item_banklist.view.*

class MyBankAccountsAdapter(c: Context, bankList: ArrayList<Body>, activity: MyBankAccountsActivity) :
    RecyclerView.Adapter<MyBankAccountsAdapter.ViewHolder>() {
    var ctn = c
    var list = bankList
    var activity = activity

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_banklist, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        //return list.size
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.tvBranchName.text = list.get(position).bankbranch.toString()

        val lastFourDigits = list.get(position).accountNo.substring(list.get(position).accountNo.length - 4)
        holder.itemView.tvBankUserName.setText("XXXX XXXX XXXX "+lastFourDigits)
         holder.itemView.tvEdit.setOnClickListener {
            val intent = Intent(ctn, AddBankAccountActivity::class.java)
            intent.putExtra("from", "Edit")
            intent.putExtra("BankList", list)
            intent.putExtra("position", position.toString())

            ctn.startActivity(intent)
        }

        if(list.get(position).isDefault==1)
        {
            holder.itemView.iv_defualtimage.setImageResource(R.drawable.background_red_border)
        }
        else
        {
            holder.itemView.iv_defualtimage.setImageResource(R.drawable.ellipse_red)
        }
        holder.itemView.tvDelete.setOnClickListener {
            deleteBank(list.get(position).id.toString())
        }

        holder.itemView.selectdefualt.setOnClickListener {

            activity.selectdefualtBank(list.get(position).id.toString())
        }
        holder.itemView.ll_bankdetal.setOnClickListener {
        activity.selectedbankBank(list.get(position).id.toString(),list.get(position).bankbranch,list.get(position).accountNo.toString())
        }
    }

    fun deleteBank(bank_id: String) {
        val deleteDialog = Dialog(ctn)
        deleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        deleteDialog.setContentView(R.layout.dialog_delete)

        deleteDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        deleteDialog.window!!.setGravity(Gravity.CENTER)
        deleteDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        deleteDialog.setCancelable(true)
        deleteDialog.setCanceledOnTouchOutside(true)

        deleteDialog.btnDeleteNo.setOnClickListener {
            deleteDialog.dismiss()
        }
        deleteDialog.btnDeleteYes.setOnClickListener {
            activity.deleteBank(bank_id)

            deleteDialog.dismiss()
        }
        deleteDialog.show()
    }

}