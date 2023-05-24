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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MapViewModel(
    private val eventListService: EventListModel,
    private val distractionListService: DistractionListModel
): ViewModel() {
    val DEFAULT_ZOOM = 15f
    private val defaultLocation = LatLng(46.520544, 6.567825) // EPFL
    private val currentTime = Calendar.getInstance()
    private val timeFormat = SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.ENGLISH);

    /**
     * Start position of the camera on Map
     */
    fun initCameraPosition(): CameraPosition {
        return CameraPosition.fromLatLngZoom(defaultLocation, DEFAULT_ZOOM)
    }

    fun getEvents(): List<Event> {
        return getAvailableEvents(eventListService.getAllEvents())
    }

    fun getDistractions(): List<Distraction> {
        return distractionListService.getAllDistractions().toList()
    }

    private fun getAvailableEvents(events: List<Event>, time: Calendar = currentTime): List<Event> {
        return events.filter { event -> isEventBeforeTime(event, time) }
    }

    /**
     * Check if an event is in the past or if the event allows late participation then check
     * if it is no ended yet
     */
    private fun isEventBeforeTime(event: Event, time: Calendar = currentTime): Boolean{
        val cal = Calendar.getInstance();
        return try {
            if(event.lateParticipation) {
                cal.time = timeFormat.parse(event.end)!!
                time < cal
            } else {
                cal.time = timeFormat.parse(event.start)!!
                time < cal
            }
        } catch (e : Exception) {
            Log.d("Events date","can't convert to time format")
            false
        }
    }
}