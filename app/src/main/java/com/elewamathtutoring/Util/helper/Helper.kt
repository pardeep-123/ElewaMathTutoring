package com.elewamathtutoring.Util.helper

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.FusedLoc
import com.elewamathtutoring.Util.OnNoInternetConnectionListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes

import com.tapadoo.alerter.Alerter


object Helper {
    val REQUEST_GPS_ENABLE = 199
    @JvmStatic
    fun showToast(context: Context, msg: Int) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun showSuccessToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun removeExtraString(toString: String): String {
        return toString
                .replace("[", "") //remove the right bracket
                .replace("]", "") //remove the left bracket
                .trim { it <= ' ' }
    }

    fun checkLocPermission(c: Activity): Boolean {
        var check = false
        val PERMISSION_CALLBACK_CONSTANT = 100
        val permissionsRequired = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (ActivityCompat.checkSelfPermission(c, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED) {
            when {
                ActivityCompat.shouldShowRequestPermissionRationale(c, permissionsRequired[0]) -> {
                    Log.d("TAG", "if permission")
                    ActivityCompat.requestPermissions(c, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
                }
                else -> {
                    Log.d("TAG", "else permission")
                    ActivityCompat.requestPermissions(c, permissionsRequired, PERMISSION_CALLBACK_CONSTANT)
                }
            }
        } else {
            check = true
            Log.d("TAG", "else permission already granted")
        }

        return check
    }

    fun checkGps(c: Activity) {
        val client = FusedLoc(c)
        var googleApiClient = client.getClient()
        googleApiClient?.connect()
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 1000
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result = LocationServices.SettingsApi.checkLocationSettings(
            googleApiClient,
            builder.build()
        )
        result.setResultCallback { result ->
            val status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {

                    status.startResolutionForResult(c, REQUEST_GPS_ENABLE)
                } catch (e: IntentSender.SendIntentException) {
                    Toast.makeText(c, "Errror", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    @JvmStatic
    fun showErrorAlert(context: Activity, msg: String) {
        Alerter.create(context)
                .setTitle(context.getString(R.string.error_))
                .setTitleAppearance(R.style.AlertTextAppearanceTitle)
                .setText(msg)
                .setTextAppearance(R.style.AlertTextAppearanceText)
                .setBackgroundColorRes(R.color.red)
                .show()

    }
    @JvmStatic
    fun showErrorAlertWithoutTitle(context: Activity, msg: String) {
        Alerter.create(context)
                .setText(msg)
                .setTextAppearance(R.style.AlertTextAppearanceText)
                .setBackgroundColorRes(R.color.red)
                .show()

    }

    @JvmStatic
    fun showSuccessAlert(context: Activity, msg: String) {
        Alerter.create(context)
                .setText(msg)
                .setTextAppearance(R.style.AlertTextAppearanceText)
                .setBackgroundColorRes(R.color.teal_700)
                .show()
    }


    @JvmStatic
    fun showNoInternetAlert(
            context: Activity,
            msg: String,
            listener: OnNoInternetConnectionListener
    ) {
        Alerter.create(context)
                .setTitle(context.getString(R.string.error_))
                .setTitleAppearance(R.style.AlertTextAppearanceTitle)
                .setText(msg)
                .setTextAppearance(R.style.AlertTextAppearanceText)
                .setBackgroundColorRes(R.color.teal_700)
                .addButton(
                        context.getString(R.string.retry),
                        R.style.AlertButton,
                        View.OnClickListener {
                            listener.onRetryApi()
                        })
                .show()
    }


    @JvmStatic
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    @SuppressLint("Recycle")
    fun getAbsolutePath(activity: Context, uri: Uri): String {
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            val projection = arrayOf("_data")
            val cursor: Cursor?
            try {
                cursor = activity.contentResolver.query(uri, projection, null, null, null)
                val columnIndex = cursor!!.getColumnIndexOrThrow("_data")
                if (cursor.moveToFirst()) {
                    return cursor.getString(columnIndex)
                }
            } catch (e: Exception) {
                // Eat it
                e.printStackTrace()
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path!!
        }
        return ""
    }

}