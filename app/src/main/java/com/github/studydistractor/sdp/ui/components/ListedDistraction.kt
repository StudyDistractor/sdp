package com.github.studydistractor.sdp.ui.components

import androidx.compose.foundation.clickable
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
import com.github.studydistractor.sdp.data.Distraction

@Composable
fun ListedDistraction(
    distraction: Distraction,
    onClick: () -> Unit
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
                    .fillMaxHeight()
            ) {
                Text(
                    text = distraction.name!!,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.testTag("name")
                )
            }
        }
        Divider(
            modifier = Modifier.padding(horizontal = 14.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
        )
    }
}