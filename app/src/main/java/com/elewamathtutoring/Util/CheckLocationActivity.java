package com.elewamathtutoring.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

abstract public class CheckLocationActivity extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ResultCallback<LocationSettingsResult> {
    protected GoogleApiClient mGoogleApiClient;
    protected Location mCurrentLocation;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    //private Activity mContext;
    private int REQUEST_PERMISSIONS_LOCATION = 2;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    PermissionListener permissionlistener;
    Activity activity;
    private static final long INTERVAL = 1000 * 60 * 1; //1 minute
    private static final long FASTEST_INTERVAL = 1000 * 60 * 1; // 1 minute
    private static final float SMALLEST_DISPLACEMENT = 0.25F; //quarter of a meter
    public void checkPermissionLocation(Activity activity) {
        this.activity = activity;
        permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                            getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requiredPermissionLocation();
                        //   return false;
                    } else {
                        initialize();
                        //  return true;
                    }
                } else {
                    initialize();
                    // return true;
                }
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
   //     initialize();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                activity.finishAffinity();
                Log.e("T525AG", "onPermissionDenied: " );
            }


        };

        tedPermission();

    }


    public void requiredPermissionLocation() {

        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, 14758);
    }


    public void tedPermission() {
        TedPermission.with(activity)
                .setPermissionListener(permissionlistener)
                .setRationaleConfirmText("Permissions")
                .setRationaleTitle("Permission required.")
                .setRationaleMessage("We need this permission for location picker..")
                .setDeniedTitle("Permission denied")
                .setDeniedMessage(
                        "If you reject permission,you can not use location picker\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButtonText("Settings")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 14758: {


                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    initialize();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requiredPermissionLocation();
                            }

                        }
                    }
                }
                break;
            }
        }
    }


    public void initialize() {
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        connectGoogleClient();
        onPermissionGranted();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            connectGoogleClient();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null)
            disConnectGoogleClient();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient != null && mLocationRequest != null && mLocationSettingsRequest != null)
            requestLocationUpdate();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mLocationRequest != null && mLocationSettingsRequest != null)
            stopLocationUpdate();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(requireContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT);
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
         /*  mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(10000 / 2);*/
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    public void connectGoogleClient() {
        mGoogleApiClient.connect();
    }

    public void requestLocationUpdate() {
        checkLocationSettings();
        if (mGoogleApiClient.isConnected() && mLocationRequest != null) {
            startLocationUpdates();
        }
    }

    public void stopLocationUpdate() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this
            ).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {

                }
            });
        }
    }

    public void disConnectGoogleClient() {
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("location", "change " + location.getLatitude());
        mCurrentLocation = location;
        onLocationGet(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
    }

    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mCurrentLocation == null) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mCurrentLocation != null) {
                onLocationGet(String.valueOf(mCurrentLocation.getLatitude()), String.valueOf(mCurrentLocation.getLongitude()));
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(requireActivity(), REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {

                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                break;
        }
    }


    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        if (mGoogleApiClient.isConnected()) {


            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this
            ).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {

                }
            });
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                break;
        }
    }



    abstract public void onPermissionGranted();

    abstract public void onLocationGet(String latitude, String longitude);

}
