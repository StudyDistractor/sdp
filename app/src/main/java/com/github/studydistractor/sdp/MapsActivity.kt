package com.github.studydistractor.sdp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.studydistractor.sdp.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sat = LatLng(46.520544, 6.567825)
        mMap.addMarker(MarkerOptions().position(sat).title("Satellite"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sat))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17f))

        mMap.setOnInfoWindowClickListener {
            val lat = it.position.latitude
            val lng = it.position.longitude
            val toast = Toast.makeText(applicationContext, "Latitude: $lat, Longitude: $lng", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}