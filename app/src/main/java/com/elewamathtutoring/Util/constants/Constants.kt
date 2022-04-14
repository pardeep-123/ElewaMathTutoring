package com.elewamathtutoring.Util.constant

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Constants {
    var Notification_chat = "Notification_chat"
    companion object {
      //  http://3.141.207.49
       // const val BASE_URL = "http://3.141.207.49/api/"//live Eteacher
        const val BASE_URL = "http://202.164.42.227:7552/api/"//live
        const val SOCKET_BASE_URL = "http://202.164.42.227:7552"//live

        /* const val BASE_URL = "http://192.168.1.181:7552/api/"//local
        const val SOCKET_BASE_URL = "http://192.168.1.181:7552"//local*/
        const val SOCKET_BASE_URL_IMAGE = "http://202.164.42.227:7552/uploads/chat/"//live

        const val SOCKET_BASE_URL_IMAGE_USER = "http://202.164.42.227:7552/uploads/users/"//live

        //const val BASE_URL = "http://localhost:7552/api/"  local Math Tutoring
        const val SECURITY_KEY = "securitykey"
        const val IMAGE_URL = "http://202.164.42.227:7552/uploads/users/"
        const val documents_URL = "http://202.164.42.227:7552/uploads/documents/"

        const val AUTH_KEY = "token"
        const val Currency = "$"
        lateinit var Notification_chat:String
        var USER_IDValue = "userId"
        const val SECURITY_KEY_VALUE = "eteacher007"
        const val DEVICE_TYPE_VALUE = "1"
        const val USER_ID = "userid"
        const val deviceType = "1"
        const val notificationStatus = "notificationStatus"

        //Api Constants

        const val name = "name"
        const val user_type = "userType" // 1 for student and 2 for tutor
        const val password = "password"
        const val email = "email"
        const val Social_login = "socialLogin"
        const val message = "message"
        const val time = "time"

        @SuppressLint("SimpleDateFormat")
        fun ConvertTimeStampToDate(timestamp: Long, format: String): String? {
            val calendar = Calendar.getInstance()
            val tz = TimeZone.getDefault()
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.timeInMillis))
            val sdf = SimpleDateFormat(format)
            sdf.timeZone = tz
            val currenTimeZone = Date(timestamp * 1000)
            return sdf.format(currenTimeZone)
        }

        // to set Date to Rating Date in custom format
        fun convertDateToRatingTime(timestamp: Long): String? {
            val cal: Calendar = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = timestamp * 1000
            val outputFormat: DateFormat = SimpleDateFormat("EEE, MMM dd")
            //outputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return outputFormat.format(cal.time)
        }
  fun convertTime(timestamp: Long): String? {
            val cal: Calendar = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = timestamp * 1000
            val outputFormat: DateFormat = SimpleDateFormat("hh:mm a")
            //outputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return outputFormat.format(cal.time)
        }
        // automatically date and time formatting by get api
        fun getNotificationTime(time_stamp: Long): String {
            var date: Date? = null
            try {
                date = Date(time_stamp * 1000)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            println("dateeee" + date.toString())
            var string_date = ""
            val current = Calendar.getInstance().time
            var diffInSeconds = (current.time - date!!.time) / 1000
            val sec = if (diffInSeconds >= 60) diffInSeconds % 60 else diffInSeconds
            val min = if ((diffInSeconds / 60).also {
                    diffInSeconds = it
                } >= 60) diffInSeconds % 60 else diffInSeconds
            val hrs = if ((diffInSeconds / 60).also {
                    diffInSeconds = it
                } >= 24) diffInSeconds % 24 else diffInSeconds
            val days = if ((diffInSeconds / 24).also {
                    diffInSeconds = it
                } >= 30) diffInSeconds % 30 else diffInSeconds
            val weeks = days / 7
            val months = if ((diffInSeconds / 30).also {
                    diffInSeconds = it
                } >= 12) diffInSeconds % 12 else diffInSeconds
            val years = (diffInSeconds / 12).also { diffInSeconds = it }
            when {
                years > 0 -> {
                    string_date = if (years == 1L) {
                        "1 year"
                    } else {
                        "$years years"
                    }
                }
                months > 0 -> {
                    string_date = if (months == 1L) {
                        "1 month"
                    } else {
                        "$months months"
                    }
                }
                weeks > 0 -> {
                    string_date = if (weeks == 1L) {
                        "1 week"
                    } else {
                        "$weeks Weeks"
                    }
                }
                days > 0 -> {
                    string_date = if (days == 1L) {
                        "1 day"
                    } else {
                        "$days days"
                    }
                }
                hrs > 0 -> {
                    string_date = if (hrs == 1L) {
                        "1 hour"
                    } else {
                        "$hrs hours"
                    }
                }
                min > 0 -> {
                    string_date = if (min == 1L) {
                        "1 minute"
                    } else {
                        "$min minutes"
                    }
                }
            }
            string_date = "$string_date ago"
            if (string_date == " ago") {
                string_date = "1 sec" + " ago"
            }
            return string_date
        }



        //hh:mm a
        fun convertOneFormatToAnother(eventDay:String,oldDateformat: String, newdateformat:String): String
        {
            val clickedDayCalendar = eventDay
            val dateParser = SimpleDateFormat(oldDateformat)
            val date = dateParser.parse(clickedDayCalendar.toString())
            val dateFormatter = SimpleDateFormat(newdateformat)
            return  dateFormatter.format(date)
        }
        @SuppressLint("ClickableViewAccessibility")
        fun scrollEditText(s: EditText) {
            s.setOnTouchListener(View.OnTouchListener { v, event ->

                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK)
                {
                    MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
                }

                false
            })
        }
        fun convertTimestampToDate(timestamp: Long, dateFormateStyle: String): String {
            val calendar = Calendar.getInstance()
            val tz = TimeZone.getDefault()
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.timeInMillis))
            val sdf = SimpleDateFormat(dateFormateStyle)
            sdf.timeZone = tz
            val currenTimeZone = Date(timestamp * 1000)
            return sdf.format(currenTimeZone)
        }

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

        fun personVirtual(value: Int): String {
            var check: String
            if (value == 1) {
                check = "In-Person Session"
            } else {
                check = "Virtual Session"
            }
            return check
        }
        fun isCertifiedOrtutor(value: Int): String {
            val check: String
            if (value == 1)
            {
                check = "Certified Teacher"
            }
            else if(value == 2) {
                check = "Tutor"
            }
            else if(value == 3)
            {
                check = "Specialist"
            }
            else
            {
                check = ""
            }
            return check
        }
        //     0=pending,1=accepted,2=completed,3 =cancel , 4 = cancelbyuser

        fun Orderstatus(value: Int): String {
            val check: String
            if (value == 0)
            {
                check = "Requested"
            }
            else if(value == 1) {
                check = "Accepted"
            }
            else if(value == 2) {
                check = "Completed"
            }
            else if(value == 3)
            {
                check = "Cancelled"
            }
            else if(value == 4)
            {
                check = "Cancelled by Student"
            }
            else if(value == 6)
            {
                check = "Completed by Stydent"
            }
            else
            {
                check = ""
            }
            return check
        }

     fun getBitmapFromURL(src: String): Bitmap? {
             try
            {
                val url = URL(src)
                val connection = url.openConnection() as HttpURLConnection
                
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                return   BitmapFactory.decodeStream(input)
            } catch (e: IOException)
            {
                e.printStackTrace()
                return  null
            }
        }

    fun getImageUriFromBitmap(inContext: Context, inImage: Bitmap): Uri
    {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, System.currentTimeMillis().toString(), null)
        return Uri.parse(path)
    }
        fun isGooglePhotosUri(uri: Uri): Boolean
        {
            return "com.google.android.apps.photos.content" == uri.authority
        }
        fun isMediaDocument(uri: Uri): Boolean
        {
            return "com.android.providers.media.documents" == uri.authority
        }

        fun isDownloadsDocument(uri: Uri): Boolean
        {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        fun isExternalStorageDocument(uri: Uri): Boolean
        {
            return "com.android.externalstorage.documents" == uri.authority
        }

        fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String?
        {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)
            try
            {
                cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst())
                {
                    val index: Int = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally
            {
                if (cursor != null) cursor.close()
            }
            return null
        }
        @SuppressLint("NewApi")
        fun getPath(context: Context, uri: Uri): String?
        {
            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri))
            {
                if (isExternalStorageDocument(uri))
                {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true))
                    {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }

                    // TODO handle non-primary volumes
                } else if (isDownloadsDocument(uri))
                {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                    return getDataColumn(context, contentUri, null, null)
                } else if (isMediaDocument(uri))
                {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    if ("image" == type)
                    {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type)
                    {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type)
                    {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true))
            {
                // Return the remote address
                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
            } else if ("file".equals(uri.scheme, ignoreCase = true))
            {
                return uri.path
            }
            return null
        }


    }
}
