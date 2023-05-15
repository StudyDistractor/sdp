package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.createDistraction.CreateDistractionViewModel
import com.github.studydistractor.sdp.ui.components.CreateActivityButton
import com.github.studydistractor.sdp.ui.components.CreateActivityNameAndDescriptionFields
import com.github.studydistractor.sdp.ui.components.CreateLatitudeAndLongitudeFields


/**
 * Screen to create distraction
 *
 * @param createDistractionViewModel distraction service where to add the distraction
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDistractionScreen(
    createDistractionViewModel: CreateDistractionViewModel,
    onDistractionCreated: () -> Unit
) {
    val uiState = createDistractionViewModel.uiState.collectAsState().value
    Box(
        modifier = Modifier
            .padding(16.dp)
            .testTag("create-activity-screen__main-container")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                androidx.compose.material.Text(
                    text = "Create distraction",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(vertical = 32.dp)
                        .testTag("create-distraction-screen__title")
                )
            }
            CreateActivityNameAndDescriptionFields(
                createActivityViewModel = createDistractionViewModel
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("locationRow"),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = uiState.locationFieldIsVisible,
                    modifier = Modifier.testTag("checkbox"),
                    onCheckedChange = {
                        createDistractionViewModel.updateLocationFieldIsVisible(it)
                    }
                )
                Text(text = "Add location to activity")
            }

            if (uiState.locationFieldIsVisible) {
                CreateLatitudeAndLongitudeFields(createActivityViewModel = createDistractionViewModel, uiState = uiState)
            } else {
                createDistractionViewModel.updateLatitude("")
                createDistractionViewModel.updateLongitude("")
            }


            CreateActivityButton(
                createActivityViewModel = createDistractionViewModel,
                onActivityCreated = onDistractionCreated,
                buttonText = "Create new distraction"
            )
        }
    }
}