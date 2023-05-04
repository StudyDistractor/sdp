package com.github.studydistractor.sdp.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.createDistraction.CreateDistractionViewModel

object DistractionScreenConstants {
    const val MAX_NAME_LENGTH = 20
    const val MAX_DESCRIPTION_LENGTH = 200
}
/**
 * Screen to create distraction
 *
 * @param distractionModel distraction service where to add the distraction
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDistractionScreen(
    createDistractionViewModel: CreateDistractionViewModel,
    onDistractionCreated: () -> Unit
) {
    fun showFailureToast(context: Context, message: String) {
        Toast.makeText(context, "Failure: $message", Toast.LENGTH_SHORT)
            .show()
    }

    val uiState by createDistractionViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(16.dp)
            .testTag("create-distraction-screen__main-container")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = uiState.name,
                label = {Text("name")},
                onValueChange = { createDistractionViewModel.updateName(it) },
                modifier = Modifier
                    .testTag("name")
                    .fillMaxWidth(),
                supportingText = {
                    Text(
                        text = uiState.supportingTextName,
                        modifier = Modifier.fillMaxWidth()
                            .testTag("nameSupport"),
                        textAlign = TextAlign.End,
                    )
                },
            )

            OutlinedTextField(
                value = uiState.description,
                label = {Text("description")},
                onValueChange = { createDistractionViewModel.updateDescription(it) },
                modifier = Modifier
                    .testTag("description")
                    .fillMaxWidth(),
                supportingText = {
                    Text(
                        text = uiState.supportingTextDescription,
                        modifier = Modifier.fillMaxWidth()
                            .testTag("descriptionSupport"),
                        textAlign = TextAlign.End
                    )
                },
            )

            Button(
                onClick = {
                    createDistractionViewModel.createDistraction()
                        .addOnSuccessListener { onDistractionCreated() }
                        .addOnFailureListener { showFailureToast(context, it.message.orEmpty()) }
                },
                modifier = Modifier.testTag("addActivity")
            ) {
                Text("Create new distraction")
            }
        }
    }
}