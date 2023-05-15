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
    Surface(
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            TopAppBar(
                title = { Text(text = "Chat") },
                modifier = Modifier.testTag("event-chat-screen__title")
            )
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(uiState.messages) { message ->
                    Surface(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 8.dp
                        )
                            .testTag("event-chat-screen__message-${message.messageId}")
                        ,
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
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
                            .format(LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(message.timeStamp), ZoneId.systemDefault())),
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
                    modifier = Modifier.weight(1f)
                        .testTag("event-chat-screen__text-field")

                )
                IconButton(
                    onClick = {
                        viewModel.postMessage()
                    },
                    modifier = Modifier.padding(start = 16.dp)
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