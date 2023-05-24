package com.github.studydistractor.sdp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.Settings
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
@OptIn(ExperimentalMaterial3Api::class)
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
                .padding(horizontal = 14.dp, vertical = 2.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
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
                ) {
                    Divider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                    // Tags filter
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.testTag("tags")
                    ) {
                        Icon(Icons.Filled.Sell, contentDescription = "tags icon")
                        Text(text = "Tags", style = MaterialTheme.typography.titleMedium)
                    }
                    LazyRow(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(availableTags) { tag ->
                            val selected: Boolean = selectedTags.contains(tag)
                            FilterChip(
                                onClick = {
                                    if (selected) {
                                        removeTag(tag)
                                    } else {
                                        addTag(tag)
                                    }
                                          },
                                label = { Text(text = tag)},
                                selected = selected,
                                leadingIcon = {
                                    if(selected) {
                                        Icon(Icons.Filled.Check,
                                            contentDescription = "Bookmark button"
                                        )
                                    }
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                                modifier = Modifier.testTag("distraction-list-screen__button-select-tag")
                            )
                        }
                    }

                    Divider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                    // Length filter
                    Row(
                        modifier = Modifier
                            .testTag("length"),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Filled.HourglassEmpty, contentDescription = "length icon")
                        Text(
                            "Length",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    LazyRow(modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(Distraction.Length.values()) {
                            FilterChip(
                                onClick = {
                                    updateLength(it)
                                },
                                label = { Text(text = it.toString().lowercase().replaceFirstChar { c -> c.uppercase() })},
                                selected = it == selectedLength,
                                leadingIcon = {
                                    if(it == selectedLength) {
                                        Icon(Icons.Filled.Check,
                                            contentDescription = "length button"
                                        )
                                    }
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                                modifier = Modifier.testTag("distraction-list-screen__select-length")
                            )
                        }
                    }

                    Divider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("distraction-list-screen__filter-panel__bookmark-row"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Filled.Favorite, contentDescription = "bookmark icon")
                            Text(text = "Bookmarks only", style = MaterialTheme.typography.titleMedium)
                        }

                        Switch(
                            checked = bookmarksOnly,
                            onCheckedChange = {updateBookmarksOnly(!bookmarksOnly)},
                            thumbContent = {
                                if(bookmarksOnly) {
                                    Icon(Icons.Filled.Check,
                                        contentDescription = "bookmark switch"
                                    )
                                }
                            }
                        )
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
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text("Apply Filters", color = Color.White)
                    }
                }
            }

    }
}