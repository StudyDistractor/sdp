package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.studydistractor.sdp.distractionStat.DistractionStatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistractionStatScreen(viewModel : DistractionStatViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    viewModel.refreshModel()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(26.dp),
    ) {
        /**
         * Like and dislike count
         */
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(text = "Likes : ${uiState.likes}", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Dislikes :${uiState.dislikes}", fontSize = 20.sp)
        }
        /**
         * Like and dislike buttons
         */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = {
                    viewModel.like()
                },
                Modifier.testTag("distraction-stat-screen__like")

            ) {
                Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "Like")
            }

            IconButton(
                onClick = {
                    viewModel.dislike()
                },
                Modifier.testTag("distraction-stat-screen__dislike"),

            ) {
                Icon(imageVector = Icons.Default.ThumbDown, contentDescription = "Like")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        /**
         * Feedback text field and button
         */
        OutlinedTextField(
            value = uiState.feedback,
            onValueChange = { viewModel.updateFeedback(it) },
            label = { Text("Add feedback") },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Filled.Forum, contentDescription = null)
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .testTag("distraction-stat-screen__feedback-text-field")
        )
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = {
                viewModel.postFeedback()
                viewModel.refreshModel()
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("distraction-stat-screen__feedback-button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )

        ) {
            Text("Validate")
        }


        /**
         * Tags text field and button
         */
        OutlinedTextField(
            value = uiState.tag,
            onValueChange = { viewModel.updateTag(it) },
            label = { Text("Add tag") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            leadingIcon = {
                Icon(Icons.Filled.Tag, contentDescription = null)
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .testTag("distraction-stat-screen__tag-text-field")
        )
        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                viewModel.postTag()
                viewModel.refreshModel()
            },
            modifier = Modifier
                .testTag("distraction-stat-screen__tag-button")
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Text("Validate")
        }
        Spacer(modifier = Modifier.height(4.dp))
        /**
         * List of tags
         */

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
        ) {
            Text(
                text = "Feedbacks : ",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
            )
            LazyColumn() {
                items(uiState.feedbacks) { i ->
                    Text(
                        "- $i",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .testTag("distraction-stat-screen__feedback-$i")
                            .padding(horizontal = 14.dp, vertical = 4.dp)
                    )
                }
            }
        }
            Spacer(modifier = Modifier.height(4.dp))
            /**
             * List of feedbacks
             */
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
        ) {
            Text(
                "Tags: ",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
            )
            LazyColumn() {
                items(uiState.tags) { i ->
                    Text(
                        "- $i",
                        style = MaterialTheme.typography.bodySmall,
                        modifier =
                        Modifier
                            .testTag("distraction-stat-screen__tag-$i")
                            .padding(horizontal = 14.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}