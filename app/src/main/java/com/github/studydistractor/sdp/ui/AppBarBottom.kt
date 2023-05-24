package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties


@Composable
fun AppBarBottom(
    onMapClick: () -> Unit,
    onListClick: () -> Unit,
    onEventListClick: () -> Unit,
    onCreateDistractionActivityClick: () -> Unit,
    onCreateEventActivityClick: () -> Unit,
    onMagicClick: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier
            .testTag("app-bar-bottom"),
        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onListClick,
                    modifier = Modifier
                        .testTag("app-bar-bottom__list-button")
                ) {
                    Icon(
                        Icons.Outlined.List,
                        contentDescription = "List of ideas",
                    )
                }
                IconButton(
                    onClick = onEventListClick,
                    modifier = Modifier
                        .testTag("app-bar-bottom__event-list-button")
                ) {
                    Icon(
                        Icons.Outlined.Groups,
                        contentDescription = "List of events",
                    )
                }
                Box(
                    modifier = Modifier.testTag("app-bar-bottom__create-activity-dropdown")
                ) {
                    var dropDownExpanded by remember { mutableStateOf(false) }
                    DropdownMenu(
                        expanded = dropDownExpanded,
                        onDismissRequest = { dropDownExpanded = false },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primaryContainer)

                    ) {
                        DropdownMenuItem({ Text("Create distraction") },
                            modifier = Modifier
                                .testTag("app-bar-bottom__create-distraction-activity-button"),
                            onClick = {
                            dropDownExpanded = false
                            onCreateDistractionActivityClick()
                        })
                        DropdownMenuItem({ Text("Create event") },
                            modifier = Modifier
                                .testTag("app-bar-bottom__create-event-activity-button"),
                            onClick = {
                            dropDownExpanded = false
                            onCreateEventActivityClick()
                        })
                    }
                    IconButton(
                        onClick = { dropDownExpanded = true },
                        modifier = Modifier.testTag("app-bar-top__create-activity-button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Create activity"
                        )
                    }
                }
                IconButton(
                    onClick = onMapClick,
                    modifier = Modifier
                        .testTag("app-bar-bottom__map-button")
                ) {
                    Icon(
                        ImageVector.vectorResource(id = com.github.studydistractor.sdp.R.drawable.pin_drop),
                        contentDescription = "Map",
                        modifier = Modifier.size(24.dp)
                    )
                }

                FloatingActionButton(
                    onClick = onMagicClick,
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                    modifier = Modifier
                        .testTag("app-bar-bottom__magic-button")
                ) {
                    Icon(ImageVector.vectorResource(id = com.github.studydistractor.sdp.R.drawable.magic_button),
                        contentDescription = "Surprise me",
                        modifier = Modifier.size(32.dp))
                }
            }
        }
    )
}