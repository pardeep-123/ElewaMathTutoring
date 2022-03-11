package com.elewamathtutoring.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.elewamathtutoring.Activity.Chat.mathChat.MathChatResponse
import com.elewamathtutoring.Activity.ParentOrStudent.teacherDetail.TeacherDetailsActivity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.extensions.runDelayed
import kotlinx.android.synthetic.main.fragment_profile_tab.*
import kotlinx.android.synthetic.main.home_list.view.*
import java.util.logging.Handler
import kotlin.collections.ArrayList

class SearchHomeAdapter(
   var ctx: Context,
   var list: ArrayList<MathChatResponse.Body>
) : RecyclerView.Adapter<SearchHomeAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView: View = LayoutInflater.from(ctx).inflate(R.layout.home_list, parent, false)
        return MyHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.tvItemName.text=list[position].name
        holder.itemView.tv_level.text=list[position].teachingLevel
        holder.itemView.tvPostion.text= Constants.isCertifiedOrtutor(list[position].isCertifiedOrtutor)

        Glide.with(ctx).load(Constants.IMAGE_URL+list[position].image)
            //.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .error(R.drawable.profile)
            .placeholder(R.drawable.profile_unselected).into(holder.itemView.ivTeacher)

        holder.itemView.home_listcardview.setOnClickListener {
            val intent = Intent(ctx, TeacherDetailsActivity::class.java)
            intent.putExtra("teacher_id",""+list[position].id.toString())
            intent.putExtra("Type","searchhome")
            ctx.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {

        return list.size
    }

    class MyHolder(v: View) : RecyclerView.ViewHolder(v) {

    }
    fun notifyData(filterServiceList: ArrayList<MathChatResponse.Body>) {
        list = filterServiceList
        notifyDataSetChanged()
    }
}