package com.github.studydistractor.sdp.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.maps.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapsScreen(
    mapViewModel: MapViewModel,
    onDistractionClick: (Distraction) -> Unit,
    onEventClick: (Event) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState()
    cameraPositionState.position
    Box(
        modifier = Modifier
            .testTag("maps-screen__main-container")
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                //Move to default position
                cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(
                    mapViewModel.initCameraPosition().target, mapViewModel.DEFAULT_ZOOM
                ))
            }
        ) {
            for(event in mapViewModel.getEvents()) {
                Marker(
                    state = MarkerState(LatLng(event.lat, event.long)),
                    title = event.name,
                    onClick = {
                        onEventClick(event)
                        false
                    }
                )
            }

            for(distraction in mapViewModel.getDistractions()) {
                if(distraction.lat != null && distraction.long != null) {
                    Marker(
                        state = MarkerState(LatLng(distraction.lat, distraction.long)),
                        title = distraction.name,
                        onClick = {
                            onDistractionClick(distraction)
                            false
                        },
                        onInfoWindowClick = {
                            onDistractionClick(distraction) //Can't use this because the app is not able to recompose, Issue on google API side
                        }
                    )
                }
            }
        }
    }
}


