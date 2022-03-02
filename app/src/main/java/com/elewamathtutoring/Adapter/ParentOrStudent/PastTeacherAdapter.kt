package com.elewamathtutoring.Adapter.ParentOrStudent

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Fragment.ParentOrStudent.profile.ProfilResponse
import com.elewamathtutoring.Fragment.ParentOrStudent.profile.ProfileFragment

import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import kotlinx.android.synthetic.main.item_past_teacher.view.*
import java.text.SimpleDateFormat


class PastTeacherAdapter(
    c: Context,
   /* var listdata: ArrayList<Body>,*/
    var profileFragment: ProfileFragment,
    var list: ArrayList<ProfilResponse.Body.PastTeacher.Teacher>
) : RecyclerView.Adapter<PastTeacherAdapter.ViewHolder>() {
    var ctn = c
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_past_teacher, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
       // return listdata.size
        return list.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvItemName.setText(list[position].name)
        holder.itemView.tvItemType.setText(Constants.isCertifiedOrtutor(list[position].isCertifiedOrtutor))
      //  Log.e("checking_id","---"+listdata.get(position).id.toString()+"----"+listdata.get(position).teacher.name)
        //  holder.itemView.tv_start_endtime.text = listdata.get(position).timeslot.get(0).startTime +"-"+listdata.get(position).Pending_sessions[position].timeslot.get(0).endTime
        Glide.with(ctn).load(Constants.IMAGE_URL+list[position].image).placeholder(R.drawable.profile_unselected).
        into(holder.itemView.ivImage)
        /*val dateParser = SimpleDateFormat("yyyy-MM-dd")
        val date = dateParser.parse(list[position].pastTeacher[position].date)
        val dateFormatter = SimpleDateFormat("EEE, MMM dd")
        holder.itemView.tvItemDate.text=dateFormatter.format(date).toString()
        holder.itemView.tv_start_endtime.text=list[position].pastTeacher[position].time+" - "+list[position].pastTeacher[position].time*/

 /*       if(list[position].pastTeacher[position].status == 2)
        {
            holder.itemView.btnComplete.visibility=View.VISIBLE
        }
        else
        {
            holder.itemView.btnComplete.visibility=View.GONE
        }
        holder.itemView.schedulePendingRoot.setOnClickListener {
            val intent = Intent(ctn, TeacherDetailsActivity::class.java)
            intent.putExtra("teacher_id", listdata.get(position).teacher.id.toString())
            intent.putExtra("Type","PastTecher")
            ctn.startActivity(intent)          //  ctn.startActivity(Intent(ctn, TeacherDetailsActivity::class.java))
        }
        holder.itemView.btnComplete.setOnClickListener {
            completeSessionDialog(listdata.get(position).id)
        }*/
    }

    private fun completeSessionDialog(id: Int) {
        val completeDialog = Dialog(ctn)
        completeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        completeDialog.setContentView(R.layout.dialog_complete)
        completeDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        completeDialog.window!!.setGravity(Gravity.CENTER)
        completeDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
       /* completeDialog.complete_Yes.setOnClickListener {
            profileFragment.apicomplete(id)
            completeDialog.dismiss()
        }
        completeDialog.complete_No.setOnClickListener {
            completeDialog.dismiss()
        }*/

        completeDialog.setCancelable(true)
        completeDialog.setCanceledOnTouchOutside(true)

        completeDialog.show()
    }
}