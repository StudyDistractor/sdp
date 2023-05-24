package com.github.studydistractor.sdp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.github.studydistractor.sdp.data.Distraction

/**
 * Screens that represents how one distraction is displayed in the list of distraction
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListedDistraction(
    distraction: Distraction,
    onClick: () -> Unit,
    isBookmarked: (Distraction) -> Boolean
) {

    Card(
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .testTag("event-list-screen__event-card"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .clickable { onClick() }
                    .testTag("distraction-list-screen__box-distraction")
            ) {
                Row(
                    modifier = Modifier
                        .padding(all = 14.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = distraction.name!!,
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.testTag("name")
                            )
                            if (isBookmarked(distraction)) {
                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription = "Bookmark button",
                                    tint = Color.Red,
                                    modifier = Modifier.testTag("distraction-list-screen__bookmarked-icon")
                                )
                            }
                        }

                        Text(
                            text = distraction.description!!,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.testTag("name")
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier.padding(horizontal = 14.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
            )
        }
    }
}