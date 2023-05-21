package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.StudyDistractorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarTop(
    currentScreen: StudyDistractorScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    openDrawer: () -> Unit,
) {

    CenterAlignedTopAppBar(
        modifier = Modifier
            .testTag("app-bar-top"),
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ),
        title = {

        },
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if(canNavigateBack) {
                    IconButton(
                        onClick = { if (canNavigateBack) navigateUp() },
                        modifier = Modifier.testTag("app-bar-top__back-button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back arrow"
                        )
                    }
                    Text(
                        text = currentScreen.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                } else {
                    IconButton(
                        onClick = { openDrawer() },
                        modifier = Modifier.testTag("app-bar-top__drawer-button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Drawer"
                        )
                    }
                }
            }
        },
        actions = {

        }
    )
}