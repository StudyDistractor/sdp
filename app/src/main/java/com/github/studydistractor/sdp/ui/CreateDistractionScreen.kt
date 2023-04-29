package com.github.studydistractor.sdp.ui

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
import com.github.studydistractor.sdp.ui.DistractionScreenConstants.MAX_LATITUDE
import com.github.studydistractor.sdp.ui.DistractionScreenConstants.MAX_LONGITUDE
import com.github.studydistractor.sdp.ui.DistractionScreenConstants.MIN_LATITUDE
import com.github.studydistractor.sdp.ui.DistractionScreenConstants.MIN_LONGITUDE

object DistractionScreenConstants {
    const val MAX_NAME_LENGTH = 20
    const val MAX_DESCRIPTION_LENGTH = 200
    const val MAX_LATITUDE = 90.0
    const val MIN_LATITUDE = -90.0
    const val MAX_LONGITUDE = 180.0
    const val MIN_LONGITUDE = -180.0
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
            val latitude = remember { mutableStateOf(TextFieldValue("")) }
            val longitude = remember { mutableStateOf(TextFieldValue("")) }
            val locationFieldIsVisible = remember { mutableStateOf(false) }

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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("locationRow"),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = locationFieldIsVisible.value,
                    modifier = Modifier.testTag("checkbox"),
                    onCheckedChange = {
                        locationFieldIsVisible.value = it
                    }
                )
                Text(text = "Add location to activity")
            }

            if (locationFieldIsVisible.value) {
                OutlinedTextField(
                    value = latitude.value,
                    label = {Text("latitude")},
                    onValueChange = { latitude.value = it },
                    modifier = Modifier
                        .testTag("latitude")
                        .fillMaxWidth().padding(top = 16.dp)
                )

                OutlinedTextField(
                    value = longitude.value,
                    label = {Text("longitude")},
                    onValueChange = {
                        longitude.value = it
                    },
                    modifier = Modifier
                        .testTag("longitude")
                        .fillMaxWidth().padding(top = 16.dp, bottom = 16.dp),
                )
            } else {
                latitude.value = TextFieldValue("")
                longitude.value = TextFieldValue("")
            }

            Button(onClick = {createDistraction(name.value.text, description.value.text, latitude.value.text, longitude.value.text, context, distractionService)},
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
    latitude: String,
    longitude: String,
    context: Context,
    service: DistractionService
) {
    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)) {
        Toast.makeText(context, "Please fill the blanks", Toast.LENGTH_SHORT).show()
        return
    }

    val activity: Distraction
    if (latitude != "" && longitude != "") {
        try {
            val lat = latitude.toDouble()
            val long = longitude.toDouble()
            if (lat !in MIN_LATITUDE..MAX_LATITUDE) {
                Toast.makeText(context, "Latitude must be between $MIN_LATITUDE and $MAX_LATITUDE", Toast.LENGTH_SHORT).show()
                return
            }

            if (long !in MIN_LONGITUDE..MAX_LONGITUDE) {
                Toast.makeText(context, "Longitude must be between $MIN_LONGITUDE and $MAX_LONGITUDE", Toast.LENGTH_SHORT).show()
                return
            }
            activity = Distraction(name, description, lat, long)
        } catch (e: NumberFormatException) {
            Toast.makeText(context, "Invalid latitude or longitude", Toast.LENGTH_SHORT).show()
            return
        }

    } else if ((latitude != "") xor (longitude != "")) {
        Toast.makeText(context, "Please fill both latitude and longitude", Toast.LENGTH_SHORT).show()
        return
    } else {
        activity = Distraction(name, description)
    }
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