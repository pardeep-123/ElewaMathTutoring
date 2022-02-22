package com.elewamathtutoring.Util

import android.annotation.SuppressLint
import android.app.Activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.format.DateFormat
import android.util.Base64
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object CommonMethods {


    @SuppressLint("ClickableViewAccessibility")
    fun scrollEditText(editText: EditText) {
        editText.setOnTouchListener { v, event ->
            if (editText.hasFocus()) {
                v.parent.requestDisallowInterceptTouchEvent(true)
            }
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_SCROLL -> {
                    v.parent.requestDisallowInterceptTouchEvent(false)
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
    }



    //--------------------------Keyboard Hide----------------------//
    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken, 0
        )
    }

    fun hideKeyboard(activity: Activity?) {
        if (activity != null) {
            val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken, 0
            )
        }
    }

    //--------------------------Layout blur ----------------------//
    fun applyDim(parent: ViewGroup, dimAmount: Float) {
        val dim: Drawable = ColorDrawable(Color.BLACK)
        dim.setBounds(0, 0, parent.width, parent.height)
        dim.alpha = (255 * dimAmount).toInt()
        val overlay = parent.overlay
        overlay.add(dim)
    }

    //--------------------------Layout clear blur----------------------//
    fun clearDim(parent: ViewGroup) {
        val overlay = parent.overlay
        overlay.clear()
    }

/*    fun openFragment(fragment: Fragment, ctx: Context) {

        val fragmentManager: FragmentManager =(ctx as AppCompatActivity).supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null)
        transaction.replace(R.id.rlSignInContainer, fragment)
        transaction.commit()
    }*/


    fun getDateFromTimestamp(timestamp: Int, outputFormate: String): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)

        calendar.timeInMillis = timestamp * 1000L
        val date = DateFormat.format(outputFormate, calendar).toString()
        return date.replace("AM", "am").replace("PM", "pm")
    }

    fun getAddedDateFromTimestamp(timestamp: Int, outputFormate: String, min: Int): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)

        calendar.timeInMillis = timestamp * 1000L
        calendar.add(Calendar.MINUTE, min)
        val date = DateFormat.format(outputFormate, calendar).toString()
        return date.replace("AM", "am").replace("PM", "pm")
    }

    fun getMillis(unix: Long): Long {
        var date = Date(unix * 1000)
        return date.getTime()
    }

    /*fun getAddedDate(timestamp: Int,min:Int): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        calendar.add(Calendar.MINUTE,min)
        val date = DateFormat.format("MMM dd, yyyy hh:mma", calendar).toString()
        return date.replace("AM", "am").replace("PM", "pm")
    }*/

    fun getAddedCurrentTime(min: Int): String {
        /*val calendar = Calendar.getInstance(Locale.ENGLISH)*/
        /*val date = SimpleDateFormat("dd MMMM, yyyy").format(Date())*/
        val sdf = SimpleDateFormat("hh:mma dd MMMM, yyyy")
        val currentDateandTime = sdf.format(Date())

        val date = sdf.parse(currentDateandTime)
        val calendar = Calendar.getInstance()
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, min);
        val currentDate = DateFormat.format("hh:mma dd MMMM, yyyy", calendar).toString()
        return currentDate.replace("AM", "am").replace("PM", "pm")
    }

    fun getAddedDate(dateTime: String, min: Int): String {
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        /*val output = SimpleDateFormat("hh:mma dd MMMM, yyyy") //"hh:mma dd MMMM, yyyy"*/
        var d: Date? = null

        try {
            d = input.parse(dateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val calendar = Calendar.getInstance()
        calendar.setTime(d)
        calendar.add(Calendar.MINUTE, min)
        val currentDate = DateFormat.format("hh:mma dd MMMM, yyyy", calendar).toString()
        /*val formatted: String = output.format(d)
        Log.i("DATE", "" + formatted)*/
        return currentDate.replace("AM", "am").replace("PM", "pm")
    }


/*
    fun getCurrentTime(): String {
        val currentTime = SimpleDateFormat("hh:mma", Locale.getDefault()).format(Date())
        return currentTime.replace("AM", "am").replace("PM", "pm")
    }*/

    fun getDate(dateTime: String, outputFormate: String): String {
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val output = SimpleDateFormat(outputFormate) //"hh:mma dd MMMM, yyyy"
        var d: Date? = null
        try {
            d = input.parse(dateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val formatted: String = output.format(d)
        Log.i("DATE", "" + formatted)
        return formatted.replace("AM", "am").replace("PM", "pm")
    }


    fun getPercentage(value: Int, total: Int): String {
        val div = value.toDouble() / total.toDouble()
        val per = div * 100.00
        val total = (100.00 - per).toInt()
        return total.toString()
    }

    fun getBitmap(encodedImage: String): Bitmap? {
        val decodedString = Base64.decode(encodedImage, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        return decodedByte

    }


}