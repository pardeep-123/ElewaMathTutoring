package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.elewamathtutoring.R

fun ImageView.loadImage(path: Any) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 8f
    circularProgressDrawable.centerRadius = 40f
    circularProgressDrawable.setColorSchemeColors(
        ContextCompat.getColor(
            this.context,
            R.color.white
        )
    )
    circularProgressDrawable.start()
    Glide.with(this)
        .load(path)
        .placeholder(circularProgressDrawable)
        .error(R.drawable.app_icon)
        .into(this)

}