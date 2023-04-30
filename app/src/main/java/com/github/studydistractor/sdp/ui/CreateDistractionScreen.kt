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
import com.github.studydistractor.sdp.distraction.DistractionTags

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
            val tags = remember { mutableListOf<String>() }

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
                    .padding(top = 16.dp).testTag("tags_row"),
                horizontalArrangement = Arrangement.Start) {
                Text(text = "Tags", modifier = Modifier.padding(end = 8.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Start) {
               for (tag in DistractionTags.values()) {
                   CreateInputChip(tag, tags)
               }
            }
            Button(onClick = {createDistraction(name.value.text, description.value.text, tags, context, distractionService)},
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
    tags: List<String>,
    context: Context,
    service: DistractionService
) {
    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)) {
        Toast.makeText(context, "Please fill the blanks", Toast.LENGTH_SHORT).show()
        return
    }

    val activity = Distraction(name, description, null, null, null, tags)
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

/**
 * Create an input chip for the given tag
 *
 * @param tag tag to create the input chip for
 * @param tags list of tags to add the tag to
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateInputChip(
    tag: DistractionTags,
    tags: MutableList<String>
) {
    val selected = remember { mutableStateOf(false) }
    InputChip(
        selected = selected.value,
        onClick = {
            selected.value = !selected.value
            if (selected.value) {
                        tags.add(tag.toString())
                    } else {
                        tags.remove(tag.toString())
            }
          },
        label = { Text(tag.name) },
        modifier = Modifier
            .padding(end = 8.dp)
            .testTag(tag.name)
    )
}