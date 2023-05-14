package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import com.github.studydistractor.sdp.StudyDistractorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarTop(
    currentScreen: StudyDistractorScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    goToMapActivity: () -> Unit,
    goToHistoryActivity: () -> Unit,
    goToCreateDistractionActivity: () -> Unit,
    goToCreateEventActivity: () -> Unit,
) {

    CenterAlignedTopAppBar(
        modifier = Modifier
            .testTag("app-bar-top"),
        title = {
            Text(
                "Study Distractor",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.testTag("app-bar-top__title")
            )
        },
        navigationIcon = {
            Row {
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
                }
                Box() {
                    var dropDownExpanded by remember { mutableStateOf(false) }
                    DropdownMenu(
                        expanded = dropDownExpanded,
                        onDismissRequest = { dropDownExpanded = false }
                    ) {
                        DropdownMenuItem(onClick = goToCreateDistractionActivity) {
                            Text("Create distraction")
                        }
                        DropdownMenuItem(onClick = goToCreateEventActivity) {
                            Text("Create event")
                        }

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

            }
        },
        actions = {
            IconButton(
                onClick = goToHistoryActivity,
                modifier = Modifier.testTag("app-bar-top__history-button")
            ) {
                Icon(
                    imageVector = Icons.Filled.HourglassFull,
                    contentDescription = "History"
                )
            }
            IconButton(
                onClick = goToMapActivity,
                modifier = Modifier.testTag("app-bar-top__map-button")
            ) {
                Icon(
                    imageVector = Icons.Filled.Map,
                    contentDescription = "Map"
                )
            }
        }
    )
}