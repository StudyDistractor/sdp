package com.github.studydistractor.sdp.ui

import android.graphics.drawable.VectorDrawable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController


@Composable
@Preview("App Bar Bottom")
fun AppBarBottom() {
    val navController = rememberNavController()

    BottomAppBar(
        actions = {
            IconButton(
                onClick = { println("home") },
                modifier = Modifier.testTag("home")
            ) {
                Icon(
                    Icons.Outlined.AccountCircle,
                    contentDescription = "Home"
                )
            }
            IconButton(
                onClick = { println("map") },
                modifier = Modifier.testTag("map")
            ) {
                Icon(
                    ImageVector.vectorResource(id = com.github.studydistractor.sdp.R.drawable.pin_drop),
                    contentDescription = "Map",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(
                onClick = { println("list") },
                modifier = Modifier.testTag("list")
            ) {
                Icon(
                    Icons.Outlined.List,
                    contentDescription = "List of ideas",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { println("Get surprised") },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                modifier = Modifier.testTag("surprise")
            ) {
                Icon(ImageVector.vectorResource(id = com.github.studydistractor.sdp.R.drawable.magic_button),
                    contentDescription = "Surprise me",
                    modifier = Modifier.size(32.dp))
            }
        }
    )
}