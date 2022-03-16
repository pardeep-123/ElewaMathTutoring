package com.elewamathtutoring.Adapter.TeacherOrTutor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elewamathtutoring.Adapter.ClickCallBack
import com.elewamathtutoring.Model.ImageModel
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.constant.Constants
import kotlinx.android.synthetic.main.item_education_certificaate.view.*
import kotlin.collections.ArrayList

// var teachinglevel: ArrayList<String>,
class EducationCertificateAdapter(val list: ArrayList<ImageModel>,    var clickCallBack: ClickCallBack) :
    RecyclerView.Adapter<EducationCertificateAdapter.ViewHolder>() {
    lateinit var ctn: Context

   // val itemClick: ((Pos: Int):Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ctn = parent.context
        val view =
            LayoutInflater.from(ctn).inflate(R.layout.item_education_certificaate, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        //   return list.size
        return list.size + 1
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pos: Int) {
            if (pos != 0) {
                itemView.llCamera.visibility=View.GONE
                if (list[pos-1].isGalleryAdded){
                    Glide.with(ctn).load(list[pos - 1].image).into(itemView.ivDocuments)
                }else{
                    Glide.with(ctn).load(list[pos - 1].image).into(itemView.ivDocuments)
                }
            } else{
                itemView.llCamera.visibility=View.VISIBLE
            }
            itemView.rvGallery.setOnClickListener {
                if (pos == 0){
                    clickCallBack.onItemClick(pos, "gellery")
                }
            }
        }
    }
}