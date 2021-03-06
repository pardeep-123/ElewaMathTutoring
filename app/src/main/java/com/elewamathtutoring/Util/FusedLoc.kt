package com.elewamathtutoring.Util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class FusedLoc(val c: Context) : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private lateinit var listener: LocationListener
    private lateinit var googleApiClient: GoogleApiClient
    private lateinit var locationRequest: LocationRequest
    private var UPDATE_INTERVAL: Long = 5000
    private var FASTEST_INTERVAL: Long = 5000
    private var loc: Location? = null
    fun connectClient() {
        googleApiClient = GoogleApiClient.Builder(c)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build()
        googleApiClient.connect()
    }

    fun getClient(): GoogleApiClient? {
        return GoogleApiClient.Builder(c)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build()

    }

    fun getLastLoc(): Location {
        if (ActivityCompat.checkSelfPermission(
                c,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                c,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }

        return LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
    }

    fun getCurrentLoc(myinterface: MyInterface): Location? {

        return startLocationUpdates(myinterface)
    }

    fun isConnected(): Boolean {
        return googleApiClient.isConnected
    }

    fun disConnectClient() {
        if (googleApiClient.isConnected) {
            googleApiClient.connect()
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("TAG", "onConnectionFailed")

    }

    override fun onConnected(p0: Bundle?) {

    }

    override fun onConnectionSuspended(p0: Int) {
        Log.d("TAG", "onConnectionSuspended")
    }

    private fun startLocationUpdates(myinterface:MyInterface): Location? {
        listener = LocationListener {
            Log.d("TAG", "location" + it.latitude + it.longitude)
            loc = it
            myinterface.getloc(it)
            googleApiClient.disconnect()
        }
        if (ActivityCompat.checkSelfPermission(
                c,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                c,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            getLocationRequest()?.let {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                    it,listener)
            }

        }
        return loc
    }


    fun getLocationRequest(): LocationRequest? {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = UPDATE_INTERVAL
        locationRequest.fastestInterval = FASTEST_INTERVAL
        return locationRequest
    }


}
