package com.github.studydistractor.sdp.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.history.HistoryEntry
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

    Row(modifier = Modifier.padding(all = 8.dp)) {
        var isExpanded by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.clickable { isExpanded = !isExpanded }
                .testTag("entry"+entry.date)

        ) {
            Text(
                text = entry.name,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = SimpleDateFormat("hh:mm MM/dd/yy").format(Date(entry.date)),
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.animateContentSize().padding(1.dp),
            ) {
                Text(
                    text = entry.description,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}