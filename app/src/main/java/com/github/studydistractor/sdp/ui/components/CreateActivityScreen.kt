package com.github.studydistractor.sdp.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.createActivity.CreateActivityViewModel
import com.github.studydistractor.sdp.ui.state.CreateActivityUiState


/**
 * Screen to create activity (event/distraction)
 *
 * @param createActivityViewModel the activity service where to add the activity
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateActivityNameAndDescriptionFields(
    createActivityViewModel: CreateActivityViewModel
) {


    val uiState by createActivityViewModel.uiState.collectAsState()


    OutlinedTextField(
        value = uiState.name,
        label = { Text("name") },
        onValueChange = { createActivityViewModel.updateName(it) },
        modifier = Modifier
            .testTag("name")
            .fillMaxWidth(),
        supportingText = {
            Text(
                text = uiState.supportingTextName,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("nameSupport"),
                textAlign = TextAlign.End,
            )
        },
    )

    OutlinedTextField(
        value = uiState.description,
        label = { Text("description") },
        onValueChange = { createActivityViewModel.updateDescription(it) },
        modifier = Modifier
            .testTag("description")
            .fillMaxWidth(),
        supportingText = {
            Text(
                text = uiState.supportingTextDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("descriptionSupport"),
                textAlign = TextAlign.End
            )
        },
    )


}


@Composable
fun CreateActivityButton(
    createActivityViewModel: CreateActivityViewModel,
    onActivityCreated: () -> Unit,
    buttonText: String
) {
    val context = LocalContext.current
    Button(
        onClick = {
            createActivityViewModel.createActivity()
                .addOnSuccessListener { onActivityCreated() }
                .addOnFailureListener { showFailureToast(context, it.message.orEmpty()) }
        },
        modifier = Modifier.testTag("addActivity")
    ) {
        Text(buttonText)
    }
}

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

fun showFailureToast(context: Context, message: String) {
    Toast.makeText(context, "Failure: $message", Toast.LENGTH_SHORT)
        .show()
}