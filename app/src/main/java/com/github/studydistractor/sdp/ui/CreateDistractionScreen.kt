package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.createDistraction.CreateDistractionViewModel
import com.github.studydistractor.sdp.ui.components.CreateActivityButton
import com.github.studydistractor.sdp.ui.components.CreateActivityNameAndDescriptionFields

object DistractionScreenConstants {
    const val MAX_NAME_LENGTH = 20
    const val MAX_DESCRIPTION_LENGTH = 200
}
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

    Box(
        modifier = Modifier
            .padding(16.dp)
            .testTag("create-activity-screen__main-container")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                androidx.compose.material.Text(
                    text = "Create distraction",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 32.dp)
                        .testTag("create-distraction-screen__title")
                )
            }
            CreateActivityNameAndDescriptionFields(
                createActivityViewModel = createDistractionViewModel
            )
            CreateActivityButton(
                createActivityViewModel = createDistractionViewModel,
                onActivityCreated = onDistractionCreated,
                buttonText = "Create new distraction"
            )
        }
    }
}