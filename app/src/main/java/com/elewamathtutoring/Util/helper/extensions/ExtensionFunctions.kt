package com.elewamathtutoring.Util.helper.extensions

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.TimeUnit

fun isMyServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            Log.d("TAG?", true.toString() + "isMyServiceRunning")
            return true
        }
    }
    Log.d("TAG?", false.toString() + "isMyServiceRunning")
    return false
}

inline fun <reified T : Any> Context.launchActivity(body: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.body()
    startActivity(intent)
}

fun AppCompatActivity.launchCamera(requestCode: Int) {
    val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
    startActivityForResult(cameraIntent, requestCode)
}


fun AppCompatActivity.pickImage(requestCode: Int) {
    var ar = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    if (!isPermissionsGranted(this, ar))
        askForPermissions(this, ar)
    else {
        val galleryIntent =
            Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        // Start the Intent
        startActivityForResult(galleryIntent, requestCode)
    }
}

/**
 * Extension method to set View's height.
 */
fun View.setHeight(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.height = value
        layoutParams = lp
    }
}


/**
 * Extension method underLine for TextView.
 */
fun TextView.underLine() {
    paint.flags = paint.flags or Paint.UNDERLINE_TEXT_FLAG
    paint.isAntiAlias = true
}

/**
 * Extension method to check if String is Email.
 */
fun String.isEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches() && this.contains("com")
}


/**
 * Extension method to get value from EditText.
 */
val EditText.value
    get() = text.toString()

val TextView.value
    get() = text.toString()

/**
 * Extension method to run block of code after specific Delay.
 */
fun runDelayed(delay: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, action: () -> Unit) {
    Handler().postDelayed(action, timeUnit.toMillis(delay))
}


/**
 * Extension method to check String equalsIgnoreCase
 */
fun String.equalsIgnoreCase(other: String) = this.toLowerCase().contentEquals(other.toLowerCase())


/**
 * Extension method to check if String is Number.
 */
fun String.isNumeric(): Boolean {
    val p = "^[0-9]+$".toRegex()
    return matches(p)
}

fun showLog(message: String, title: String) {
    Log.e(title, message)
}

fun fullscreen(activity: Activity) {
    activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
}


fun isPermissionsGranted(context: Context, p0: Array<String>): Boolean {
    p0.forEach {
        if (ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_DENIED)
            return false
    }
    return true
}

fun askForPermissions(activity: Activity, array: Array<String>) {
    ActivityCompat.requestPermissions(activity, array, 0)
}


fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
