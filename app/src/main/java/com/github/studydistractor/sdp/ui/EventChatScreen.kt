package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.eventChat.EventChatViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventChatScreen(
    viewModel: EventChatViewModel
){
    val uiState by viewModel.uiState.collectAsState()

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(uiState.messages) { message ->
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .testTag("event-chat-screen__message-${message.messageId}"),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                    ) {
                        Text(
                            text = message.message,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Text(
                        text = message.userId,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Text(
                        text = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
                            .format(
                                LocalDateTime.ofInstant(
                                    Instant.ofEpochMilli(message.timeStamp), ZoneId.systemDefault()
                                )
                            ),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = uiState.message,
                    onValueChange = { viewModel.updateMessage(it) },
                    label = { Text(text = "Enter your message") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("event-chat-screen__text-field")

                )
                IconButton(
                    onClick = {
                        viewModel.postMessage()
                    },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .testTag("event-chat-screen__icon-button")
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Send,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Send message"
                    )
                }
            }
        }
    }
}