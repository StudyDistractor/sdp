package com.github.studydistractor.sdp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.eventChat.EventChatViewModel

/**
 * The function representing an event in the history of events
 */
@Composable
fun EventHistoryCard(
    event: Event,
    chatViewModel: EventChatViewModel,
    onChatClicked: () -> Unit
){

    Row(modifier = Modifier.padding(all = 8.dp)
    ) {
        var isExpanded by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .clickable { isExpanded = !isExpanded }
                .fillMaxSize()
                .testTag("event-history-card__title " + event.eventId),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = event.name,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .testTag("event-history-card__name " + event.eventId)
            )
            Text(
                text = "From ${event.start} to ${event.end}",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .testTag("event-history-card__date " + event.eventId)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = event.description,
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .testTag("event-history-card__description " + event.eventId),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    chatViewModel.changeEventChat(event.eventId!!)
                    onChatClicked()
                },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .testTag("event-history-card__chat-button " + event.eventId),
            ) {
                Text(
                    "See Chat",
                    modifier = Modifier
                        .testTag("event-history-card__chat-button-text " + event.eventId),
                )
            }

            Button(
                onClick = {

                },
            ) {
                Text(
                    "Claim Points"
                )
            }


        }
    }

}