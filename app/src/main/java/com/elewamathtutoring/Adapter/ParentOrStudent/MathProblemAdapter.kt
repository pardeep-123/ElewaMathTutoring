package com.elewamathtutoring.Adapter.ParentOrStudent

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.DetailActivity
import com.elewamathtutoring.Adapter.ClickCallBack
import com.elewamathtutoring.R
import kotlinx.android.synthetic.main.item_math_problem.view.*
import kotlinx.android.synthetic.main.item_math_problem.view.ivDownload
import kotlinx.android.synthetic.main.item_resources.view.*


class MathProblemAdapter(var ctn: Context,
                         var clickCallBack: ClickCallBack
) :
    RecyclerView.Adapter<MathProblemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(ctn).inflate(R.layout.item_math_problem, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return 3
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.ivDots.setOnClickListener {
//            val intent = Intent(ctn, TeacherDetailsActivity::class.java)
//            ctn.startActivity(intent)
        }

        holder.itemView.tvComment.setOnClickListener {
           val intent = Intent(ctn, DetailActivity::class.java)
            ctn.startActivity(intent)
           }

        holder.itemView.ivDots.setOnClickListener {
            clickCallBack.onItemClick(position, "dots")
        }
    }
}