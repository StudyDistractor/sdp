package com.github.studydistractor.sdp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.data.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListedEvent(
    event: Event,
    countPeople: Int,
    onEventMoreClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .testTag("event-list-screen__event-card"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 4.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            //Name and Description
            Text(
                text = event.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.testTag("event-list-screen__event-name-"+event.eventId)
            )
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.testTag("event-list-screen__event-description")
            )

            // Time and 'More' Button
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column {
                    Text(
                        text = "start : " + event.start,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.testTag("event-list-screen__event-start")

                    )
                    Text(
                        text = "end : " + event.end,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.testTag("event-list-screen__event-end")
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "Count of people"
                        )
                        Text(
                            text = countPeople.toString(),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Button(
                        onClick = onEventMoreClick,
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.inversePrimary)
                    ) {
                        Text(
                            text = "More",
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.testTag("event-list-screen__more-button")
                        )
                    }
                }
            }
        }
    }
}