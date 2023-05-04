package com.github.studydistractor.sdp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import com.github.studydistractor.sdp.data.Distraction

/**
 * Screens that represents how one distraction is displayed in the list of distraction
 */
@Composable
fun ListedDistraction(
    distraction: Distraction,
    onClick: () -> Unit,
    isBookmarked: (Distraction) -> Boolean
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
                Text(
                    text = distraction.name!!,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.testTag("name")
                )
                if(isBookmarked(distraction)) {
                    Icon(Icons.Filled.Favorite,
                        contentDescription = "Bookmark button",
                        tint = Color.Red,
                        modifier = Modifier.testTag("distraction-list-screen__bookmarked-icon")
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