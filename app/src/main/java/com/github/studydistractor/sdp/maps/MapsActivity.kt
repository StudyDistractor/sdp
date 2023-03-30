package com.github.studydistractor.sdp.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.studydistractor.sdp.BuildConfig
import com.github.studydistractor.sdp.R
import com.github.studydistractor.sdp.procrastinationActivity.FireBaseProcrastinationActivityService
import com.github.studydistractor.sdp.procrastinationActivity.ProcrastinationActivity
import com.github.studydistractor.sdp.procrastinationActivity.ProcrastinationActivityActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var placesClient: PlacesClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermissionGranted = false
    private var map: GoogleMap? = null
    private var lastKnownLocation: Location? = null
    private val defaultLocation = LatLng(46.520544, 6.567825)

    object Constants {
        const val TAG = "MapsActivity"
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
        const val DEFAULT_ZOOM = 17f
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_maps)

        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(this)


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.map = googleMap
        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()
        displayPlaces()
    }

    /**
     * Displays a single ProcrastinationActivity on a Google Map and sets up a listener to
     * open a detailed view of the activity when its associated marker is clicked.
     *
     * @param activity The ProcrastinationActivity to display on the map.
     */
    private fun displayActivity(activity: ProcrastinationActivity) {
        val latLng = activity.lat?.let { activity.long?.let { it1 -> LatLng(it, it1) } }
        val marker = latLng?.let {
            MarkerOptions()
                .position(it)
                .title(activity.name)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
        }?.let {
            map?.addMarker(
                it
            )
        }
        marker?.tag = activity
        map?.setOnInfoWindowClickListener { activityMarker ->
            val procActivity = activityMarker.tag as ProcrastinationActivity
            val intent = Intent(this, ProcrastinationActivityActivity::class.java)
            intent.putExtra("activity", procActivity)
            startActivity(intent)
        }
    }

    /**
     * Displays the places on the map and launches the distraction activity when clicking on the marker
     */
    private fun displayPlaces() {
        FireBaseProcrastinationActivityService().fetchProcrastinationActivities { activities ->
            for (activity in activities) {
                displayActivity(activity)
            }
        }
    }

    /**
     * Gets permission to access the device location.
     */
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            Constants.LOCATION_PERMISSION_REQUEST_CODE -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        updateLocationUI()
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception:", e.message, e)
        }
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), Constants.DEFAULT_ZOOM
                                )
                            )
                        }
                    } else {
                        Log.d(Constants.TAG, "Current location is null. Using defaults.")
                        Log.e(Constants.TAG, "Exception: %s", task.exception)
                        map?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, Constants.DEFAULT_ZOOM)
                        )
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }
}