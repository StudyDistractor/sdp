package com.github.studydistractor.sdp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.createActivity.CreateActivityViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLatitudeAndLongitudeFields(
    createActivityViewModel: CreateActivityViewModel,
) {
    val uiState by createActivityViewModel.uiState.collectAsState()
    OutlinedTextField(
        value = uiState.latitude.orEmpty(),
        label = { Text("latitude") },
        onValueChange = { createActivityViewModel.updateLatitude(it) },
        modifier = Modifier
            .testTag("latitude")
            .fillMaxWidth()
            .padding(top = 16.dp),
    )
    OutlinedTextField(
        value = uiState.longitude.orEmpty(),
        label = { Text("longitude") },
        onValueChange = { createActivityViewModel.updateLongitude(it) },
        modifier = Modifier
            .testTag("longitude")
            .fillMaxWidth()
            .padding(top = 16.dp),
    )
}