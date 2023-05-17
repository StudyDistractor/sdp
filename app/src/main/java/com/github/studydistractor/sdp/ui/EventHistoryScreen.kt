package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.eventChat.EventChatViewModel
import com.github.studydistractor.sdp.eventHistory.EventHistoryViewModel
import com.github.studydistractor.sdp.ui.components.EventHistoryCard

/**
 * Screen representing the history of the event that are finished and in which the current user has
 * taken part
 * @param eventHistoryViewModel the view model
 * @param chatViewModel the chat view model. It is useful when clicking on the button to see the
 * chat of an event
 * @param onChatButtonClicked the function to perform when the user click on the chat button of an
 * event
 */
@Composable
fun EventHistoryScreen(
    eventHistoryViewModel: EventHistoryViewModel,
    chatViewModel: EventChatViewModel,
    onChatButtonClicked: () -> Unit
){
    val uiState by eventHistoryViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Your event history",
            modifier = Modifier
                .padding(vertical = 32.dp)
                .testTag("event-history-screen__title"),
            style = MaterialTheme.typography.titleLarge,
        )
        LazyColumn(){
            items(uiState.eventHistory) {i->
                EventHistoryCard(i, chatViewModel, onChatButtonClicked)
            }
        }
    }
}