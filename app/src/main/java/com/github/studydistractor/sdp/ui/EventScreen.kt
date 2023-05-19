package com.github.studydistractor.sdp.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.event.EventViewModel
import com.github.studydistractor.sdp.ui.state.EventUiState

@Composable
fun EventScreen(
    eventViewModel : EventViewModel,
    onOpenChatClick: () -> Unit,
) {
    fun showFailureToast(context: Context, message: String) {
        Toast.makeText(context, "Failure: $message", Toast.LENGTH_SHORT)
            .show()
    }

    val uiState by eventViewModel.uiState.collectAsState()
    val context = LocalContext.current
    eventViewModel.updateUIParticipants()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 32.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
        ) {
            item { // Event infos
                Text(
                    text = uiState.event.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.testTag("event-name"),
                )

                Text(
                    text = uiState.event.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.testTag("event-description")
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "start: " + uiState.event.start,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.testTag("event-start")
                )

                Text(
                    text = "end: " + uiState.event.end,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.testTag("event-end")
                )

                if (uiState.event.lateParticipation) {
                    Text(
                        text = "late participation is allowed",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.testTag("event-lateparticipation")
                    )
                }
            }

            item {
                Divider(color = MaterialTheme.colorScheme.inversePrimary)
            }

            item { // Participants header
                Text(
                    text = uiState.participantsHeadingText,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.testTag("event-participants-headline")
                )
            }
            Log.d("UI", "nbr of participants " + uiState.participants.count())
            items(uiState.participants) {
                // Participants list
                Log.d("UI", "draw")
                Text(
                    text = it
                )
            }

            item {
                FloatingActionButtons(
                    uiState = uiState,
                    onOpenChatClick = onOpenChatClick,
                    onParticipateClick = {
                        eventViewModel.toggleParticipation().addOnFailureListener {
                            showFailureToast(context, "Failed to update participation !")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FloatingActionButtons(
    uiState: EventUiState,
    onOpenChatClick: () -> Unit,
    onParticipateClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.End,
    ) {
        if(uiState.participating) {
            ExtendedFloatingActionButton(
                onClick = onOpenChatClick,
                modifier = Modifier
                    .height(45.dp)
                    .testTag("open-chat"),
                text = { Text(text = "Open chat") },
                icon = { Icon(Icons.Outlined.Chat, "Open Chat") }
            )
        }
        Log.d("event", uiState.canParticipate.toString())
        if(uiState.canParticipate) {
            ExtendedFloatingActionButton(
                onClick = onParticipateClick,
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .height(45.dp)
                    .testTag("toggle-participate"),
                text = { Text(text = uiState.toggleParticipationButtonText) },
                icon = {
                    if (uiState.participating) {
                        Icon(Icons.Outlined.Cancel, uiState.toggleParticipationButtonText)
                    } else {
                        Icon(Icons.Outlined.Add, uiState.toggleParticipationButtonText)
                    }
                }
            )
        }
    }
}