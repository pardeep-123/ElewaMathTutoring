package com.elewamathtutoring.Util

import android.app.Activity
import android.text.format.DateFormat
import android.util.Log
import android.view.inputmethod.InputMethodManager
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AppUtils {
    companion object {
        //--------------------------Keyboard Hide ----------------------//
        fun hideSoftKeyboard(activity: Activity) {
            val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken, 0
            )
        }

        //--------------------------Date ----------------------//
        fun secondsToTimeStamp(seconds: Long, format: String): String {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            val milliSeconds = seconds * 1000
            return sdf.format(milliSeconds)
        }

        //--------------------------Layout blur ----------------------//


        fun cardTypeModelSet(): ArrayList<CardTypeModel>? {
            val listOfPattern: ArrayList<CardTypeModel> = ArrayList<CardTypeModel>()
            val cardTypeModel = CardTypeModel()
            cardTypeModel.setName("Visa")
            cardTypeModel.setRegx("^4[0-9]$")
            cardTypeModel.setType("0")
            listOfPattern.add(cardTypeModel)
            val cardTypeModel2 = CardTypeModel()
            cardTypeModel2.setName("Master")
            cardTypeModel2.setRegx("^5[1-5]$")
            cardTypeModel2.setType("1")
            listOfPattern.add(cardTypeModel2)
            return listOfPattern

        }

        fun getCardType(number: String): String? {
            var type: String? = ""
            for (i in cardTypeModelSet()!!.indices) {
                if (number.substring(0, 2).matches(cardTypeModelSet()!![i].regx.toRegex())) {
                    type = cardTypeModelSet()!![i].type
                }
            }
            return type
        }

        fun getDate(dateTime: String, outputFormate: String): String {
            val input = SimpleDateFormat("dd-MM-yyyy")
            val output = SimpleDateFormat(outputFormate) //"hh:mma dd MMMM, yyyy"
            var d: Date? = null
            try {
                d = input.parse(dateTime)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val formatted: String = output.format(d)
            Log.i("DATE", "" + formatted)
            return formatted
        }
    }
}