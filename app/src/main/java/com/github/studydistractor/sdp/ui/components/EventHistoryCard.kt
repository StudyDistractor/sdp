package com.github.studydistractor.sdp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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

    Row(modifier = Modifier.padding(all = 8.dp)) {
        var isExpanded by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .clickable { isExpanded = !isExpanded }
                .testTag("entry " + event.eventId)

        ) {
            Text(
                text = event.name,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.testTag("name " + event.name)
            )
            Text(
                text = "Started on ${event.start} / Ended on ${event.end}",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.testTag("start " + event.start + " end " + event.end)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp),
            ) {
                Text(
                    text = event.description,
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .testTag("description ${event.description}"),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    chatViewModel.changeEventChat(event.eventId!!)
                    onChatClicked()
                },
                modifier = Modifier.testTag("Button ${event.eventId}"),
            ) {
                Text(
                    "See Chat",
                    modifier = Modifier.testTag("Test Button ${event.eventId}")
                )
            }
        }
    }

}