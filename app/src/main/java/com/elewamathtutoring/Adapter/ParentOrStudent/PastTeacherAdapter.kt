package com.elewamathtutoring.Adapter.ParentOrStudent

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Activity.ParentOrStudent.teacherDetail.TeacherDetailsActivity
import com.elewamathtutoring.Fragment.ParentOrStudent.profile.ProfilResponse
import com.elewamathtutoring.Fragment.ParentOrStudent.profile.ProfileFragment

import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import kotlinx.android.synthetic.main.item_past_teacher.view.ivImage
import kotlinx.android.synthetic.main.item_past_teacher.view.schedulePendingRoot
import kotlinx.android.synthetic.main.item_past_teacher.view.tvItemDate
import kotlinx.android.synthetic.main.item_past_teacher.view.tvItemName
import kotlinx.android.synthetic.main.item_past_teacher.view.tvItemType
import kotlinx.android.synthetic.main.item_past_teacher.view.tv_start_endtime


class PastTeacherAdapter(
    c: Context,
    var profileFragment: ProfileFragment,
    var list: ArrayList<ProfilResponse.Body.PastTeacher>
) : RecyclerView.Adapter<PastTeacherAdapter.ViewHolder>() {
    var ctn = c

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_past_teacher, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvItemName.setText(list[position].teacher.name)
        holder.itemView.tvItemType.setText(Constants.isCertifiedOrtutor(list[position].teacher.isCertifiedOrtutor))
        Glide.with(ctn).load(Constants.IMAGE_URL + list[position].teacher.image)
            .placeholder(R.drawable.profile_unselected).into(holder.itemView.ivImage)

        holder.itemView.tvItemDate.text = Constants.ConvertTimeStampToDate(list[position].teacher.updatedAt.toLong(),"EEE, MMM yy")
        holder.itemView.tv_start_endtime.text=list[position].time +" /hr"

        holder.itemView.schedulePendingRoot.setOnClickListener {
            val intent = Intent(ctn, TeacherDetailsActivity::class.java)
            intent.putExtra("teacher_id", list[position].teacher.id.toString())
            intent.putExtra("session_id", list.get(0).id.toString())
            intent.putExtra("Type", "PastTeacher")
            ctn.startActivity(intent)
        }

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