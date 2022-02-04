package com.elewamathtutoring.Adapter.TeacherOrTutor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Models.Teacher_level.Body
import com.elewamathtutoring.R
import com.riseball.interface_base.Teachinglevel_interface
import kotlinx.android.synthetic.main.item_filteroptions.view.*
import java.lang.Exception
import kotlin.collections.ArrayList
// var teachinglevel: ArrayList<String>,
class EducationCertificateAdapter(
    conx: Context) :
    RecyclerView.Adapter<EducationCertificateAdapter.ViewHolder>() {
    var ctn = conx
    var poz=-1
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_education_certificaate, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
         //   return list.size
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }
}