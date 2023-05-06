package com.github.studydistractor.sdp.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.eventList.EventListViewModel
import com.github.studydistractor.sdp.ui.components.ListedEvent

@Composable
fun EventListScreen(
    onEventClicked: (Event) -> Unit,
    eventListViewModel: EventListViewModel
) {
    val uiState by eventListViewModel.uiState.collectAsState()
    eventListViewModel.updateEvents()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 4.dp)
    ) {
        LazyColumn{
            items(uiState.eventList){(event, count) ->
                ListedEvent(
                    event,
                    onEventMoreClick = { onEventClicked(event) },
                    countPeople = count
                )
            }
        }
    }
}