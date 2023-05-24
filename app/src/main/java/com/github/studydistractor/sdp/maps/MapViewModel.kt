package com.github.studydistractor.sdp.maps

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.distractionList.DistractionListModel
import com.github.studydistractor.sdp.distractionList.DistractionListServiceFirebase
import com.github.studydistractor.sdp.eventList.EventListModel
import com.github.studydistractor.sdp.eventList.EventListServiceFirebase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState

class MapViewModel(
    private val eventListService: EventListModel,
    private val distractionListService: DistractionListModel
): ViewModel() {
    val DEFAULT_ZOOM = 15f
    private val defaultLocation = LatLng(46.520544, 6.567825) // EPFL

    fun initCameraPosition(): CameraPosition {
        return CameraPosition.fromLatLngZoom(defaultLocation, DEFAULT_ZOOM)
    }

    fun getEvents(): List<Event> {
        return eventListService.getAllEvents()
    }

    fun getDistractions(): List<Distraction> {
        return distractionListService.getAllDistractions().toList()
    }
}