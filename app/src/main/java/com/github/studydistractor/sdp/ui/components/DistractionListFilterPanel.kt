package com.github.studydistractor.sdp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.studydistractor.sdp.data.Distraction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistractionListFilterPanel(
    isExpanded: Boolean,
    availableTags: List<String>,
    selectedTags: Set<String>,
    selectedLength: Distraction.Length?,
    addTag: (tag: String) -> Unit,
    removeTag: (tag: String) -> Unit,
    updateLength: (length: Distraction.Length) -> Unit,
    updateExpansion: (expanded: Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Filters",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        IconButton(
            onClick = { updateExpansion(!isExpanded) },
            modifier = Modifier.testTag("distraction-list-screen__filter-Button")
        ) {
            Icon(
                imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (isExpanded) "Collapse filters" else "Expand filters"
            )
        }
    }
    if (isExpanded) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .testTag("tags"),
        ) {
            Text(
                "Tags",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f)
            )

            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                items(availableTags) { tag ->
                    val selected: Boolean = selectedTags.contains(tag)
                    FilterChip(
                        selected = selected,
                        onClick = {
                            if (selected) {
                                removeTag(tag)
                            } else {
                                addTag(tag)
                            }
                        },
                        modifier = Modifier
                            .testTag("distraction-list-screen__button-select-tag")
                            .padding(horizontal = 4.dp),
                        label = { Text(tag) }
                    )
                }
            }
        }

        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Length filter
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .testTag("length"),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Length",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f)
            )

            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(Distraction.Length.values()) {
                    FilterChip(
                        selected = it == selectedLength,
                        onClick = { updateLength(it) },
                        label = {
                            Text(
                                it.toString().lowercase()
                                    .replaceFirstChar { c -> c.uppercase() })
                        },
                        modifier = Modifier
                            .testTag("distraction-list-screen__button-select-tag")
                            .padding(horizontal = 4.dp),
                    )
                }
            }
        }

        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}