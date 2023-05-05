package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
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
    onMagicClick: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier
            .testTag("app-bar-bottom"),
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onMagicClick,
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
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