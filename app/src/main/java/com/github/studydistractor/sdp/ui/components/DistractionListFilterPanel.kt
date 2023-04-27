package com.github.studydistractor.sdp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.studydistractor.sdp.data.Distraction

/**
 * Screens that represent the filter display in the list of distractions
 */
@Composable
fun DistractionListFilterPanel(
    isExpanded: Boolean,
    availableTags: List<String>,
    selectedTags: Set<String>,
    selectedLength: Distraction.Length?,
    bookmarksOnly: Boolean,
    addTag: (tag: String) -> Unit,
    removeTag: (tag: String) -> Unit,
    updateLength: (length: Distraction.Length) -> Unit,
    updateExpansion: (expanded: Boolean) -> Unit,
    updateBookmarksOnly: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
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
            Column(
                modifier = Modifier.testTag("filterIsOpen")
            ){
                // Tags filter
                Row(modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .testTag("tags"),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Tags",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(1f)
                    )

                    LazyRow(modifier = Modifier.padding(horizontal = 16.dp)) {
                        items(availableTags) { tag ->
                            val selected: Boolean = selectedTags.contains(tag)
                            Button(
                                onClick = {
                                    if (selected) {
                                        removeTag(tag)
                                    } else {
                                        addTag(tag)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if(selected) {
                                        MaterialTheme.colors.primary
                                    } else {
                                        MaterialTheme.colors.surface
                                    }
                                ),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .testTag("distraction-list-screen__button-select-tag")
                            ) {
                                Text(
                                    tag,
                                    color = if (selected) Color.White else MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                }

                Divider(
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Length filter
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .testTag("length"),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Length",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(1f)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(Distraction.Length.values()) {
                            DistractionListLengthButton(
                                text = it.toString().lowercase().replaceFirstChar { c -> c.uppercase() },
                                isSelected = it == selectedLength,
                                onClick = {
                                    updateLength(it)
                                }
                            )
                        }
                    }
                }

                Divider(
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("distraction-list-screen__filter-panel__bookmark-row"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { updateBookmarksOnly(!bookmarksOnly)},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (bookmarksOnly) MaterialTheme.colors.primary else MaterialTheme.colors.surface
                        )
                    ) {
                        Text("Bookmarks only",
                            color = if (bookmarksOnly) Color.White else MaterialTheme.colors.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Apply button
                Button(
                    onClick = {
                        updateExpansion(false)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag("distraction-list-screen__button-apply-button"),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Apply Filters", color = Color.White)
                }
            }
        }
    }
}