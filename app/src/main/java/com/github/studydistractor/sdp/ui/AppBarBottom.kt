package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp


@Composable
fun AppBarBottom(
    onHomeClick: () -> Unit,
    onMapClick: () -> Unit,
    onFriendsClick : () -> Unit,
    onListClick: () -> Unit,
    onEventListClick: () -> Unit,
    onEventClick: () -> Unit,
    onEventHistoryClick: () -> Unit,
    onMagicClick: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier
            .testTag("app-bar-bottom"),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        actions = {
            IconButton(
                onClick = onHomeClick,
                modifier = Modifier
                    .testTag("app-bar-bottom__home-button")
            ) {
                Icon(
                    Icons.Outlined.AccountCircle,
                    contentDescription = "Home"
                )
            }
            IconButton(
                onClick = onMapClick,
                modifier = Modifier.testTag("app-bar-bottom__map-button")
            ) {
                Icon(
                    ImageVector.vectorResource(id = com.github.studydistractor.sdp.R.drawable.pin_drop),
                    contentDescription = "Map",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(
                onClick = onListClick,
                modifier = Modifier.testTag("app-bar-bottom__list-button")
            ) {
                Icon(
                    Icons.Outlined.List,
                    contentDescription = "List of ideas",
                )
            }
            IconButton(
                onClick = onFriendsClick,
                modifier = Modifier.testTag("app-bar-bottom__friend-list-button")
            ) {
                Icon(
                    Icons.Outlined.Group,
                    contentDescription = "List of friends",
                )
            }
            IconButton(
                onClick = onEventClick,
                modifier = Modifier.testTag("app-bar-bottom__event-button")
            ) {
                Icon(
                    Icons.Outlined.Event,
                    contentDescription = "Event",
                )
            }
            IconButton(
                onClick = onEventHistoryClick,
                modifier = Modifier.testTag("app-bar-bottom__event-history-button")
            ) {
                Icon(
                    Icons.Outlined.AccessTime,
                    contentDescription = "History of events",
                )
            }
            IconButton(
                onClick = onEventListClick,
                modifier = Modifier.testTag("app-bar-bottom__event-list-button")
            ) {
                Icon(
                    Icons.Outlined.Groups,
                    contentDescription = "List of events",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onMagicClick,
                containerColor = MaterialTheme.colorScheme.inversePrimary,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                modifier = Modifier.testTag("app-bar-bottom__magic-button")
            ) {
                Icon(ImageVector.vectorResource(id = com.github.studydistractor.sdp.R.drawable.magic_button),
                    contentDescription = "Surprise me",
                    modifier = Modifier.size(32.dp))
            }
        }
    )
}