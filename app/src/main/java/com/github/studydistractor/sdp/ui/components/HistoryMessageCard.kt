package com.github.studydistractor.sdp.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.data.HistoryEntry
import java.text.SimpleDateFormat
import java.util.*

/**
 * Create a card for the entry
 *
 * Author: Bluedrack
 */
@SuppressLint("SimpleDateFormat")
@Composable
fun MessageCard(entry: HistoryEntry) {

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ){
        Row(modifier = Modifier.padding(all = 8.dp)) {
            var isExpanded by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded }
                    .testTag("entry" + entry.date)

            ) {
                Text(
                    text = entry.name,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = SimpleDateFormat("hh:mm MM/dd/yy").format(Date(entry.date)),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = entry.description,
                        maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
            }
        }
    }
}