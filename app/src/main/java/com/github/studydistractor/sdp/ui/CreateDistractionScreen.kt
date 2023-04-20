package com.github.studydistractor.sdp.ui

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.distraction.Distraction
import com.github.studydistractor.sdp.distraction.DistractionService

object DistractionScreenConstants {
    const val MAX_NAME_LENGTH = 20
    const val MAX_DESCRIPTION_LENGTH = 200
}
/**
 * Screen to create distraction
 *
 * @param distractionService distraction service where to add the distraction
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDistractionScreen(distractionService: DistractionService) {
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
            val name = remember { mutableStateOf(TextFieldValue("")) }
            val description = remember { mutableStateOf(TextFieldValue("")) }

            OutlinedTextField(
                value = name.value,
                label = {Text("name")},
                onValueChange = { if (it.text.length <= DistractionScreenConstants.MAX_NAME_LENGTH) name.value = it },
                modifier = Modifier
                    .testTag("name")
                    .fillMaxWidth(),
                supportingText = {
                    Text(
                        text = "${name.value.text.length}/${DistractionScreenConstants.MAX_NAME_LENGTH}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                },
            )

            OutlinedTextField(
                value = description.value,
                label = {Text("description")},
                onValueChange = { if (it.text.length <= DistractionScreenConstants.MAX_DESCRIPTION_LENGTH) description.value = it },
                modifier = Modifier
                    .testTag("description")
                    .fillMaxWidth(),
                supportingText = {
                    Text(
                        text = "${description.value.text.length}/${DistractionScreenConstants.MAX_DESCRIPTION_LENGTH}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                },
            )

            Button(onClick = {createDistraction(name.value.text, description.value.text, context, distractionService)},
                modifier = Modifier.testTag("addActivity")
            ) {
                Text("Create new distraction")
            }
        }
    }
}

/**
 * Create a distraction with parameters name and description, then post the activity
 * to the database.
 *
 * @param name name of the distraction
 * @param description description of the distraction
 */
private fun createDistraction(
    name: String,
    description: String,
    context: Context,
    service: DistractionService
) {
    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)) {
        Toast.makeText(context, "Please fill the blanks", Toast.LENGTH_SHORT).show()
        return
    }

    val activity = Distraction(name, description, null, null)
    service.postDistraction(activity,
        {
            displayMessage(context, "Distraction added")
        }, {
            displayMessage(context, "Error adding distraction")
        })

}

private fun displayMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}