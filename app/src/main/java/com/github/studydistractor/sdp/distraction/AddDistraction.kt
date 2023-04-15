package com.github.studydistractor.sdp.distraction

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Activity to add new distractions to the database.
 */
@AndroidEntryPoint(AppCompatActivity::class)
class AddDistraction : Hilt_AddDistraction() {
    @Inject
    lateinit var service: DistractionService
    companion object {
        const val MAX_NAME_LENGTH = 20
        const val MAX_DESCRIPTION_LENGTH = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateNewActivityLayout()
        }
    }

    /**
     * Compose the layout for this activity
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun CreateNewActivityLayout() {
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
                onValueChange = { if (it.text.length <= MAX_NAME_LENGTH) name.value = it },
                modifier = Modifier
                    .testTag("name")
                    .fillMaxWidth(),
                        supportingText = {
                    Text(
                        text = "${name.value.text.length}/$MAX_NAME_LENGTH",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                },
            )

            OutlinedTextField(
                value = description.value,
                label = {Text("description")},
                onValueChange = { if (it.text.length <= MAX_DESCRIPTION_LENGTH) description.value = it },
                modifier = Modifier
                    .testTag("description")
                    .fillMaxWidth(),
                supportingText = {
                    Text(
                        text = "${description.value.text.length}/$MAX_DESCRIPTION_LENGTH",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                },
            )

            Button(onClick = {createNewDistraction(name.value.text, description.value.text)},
                modifier = Modifier.testTag("addActivity")
            ) {
                Text("Create new activity")
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
    private fun createNewDistraction(name: String, description: String) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)) {
            Toast.makeText(this@AddDistraction, "Please fill the blanks", Toast.LENGTH_SHORT).show()
            return
        }

//        TODO: add the possibility to add a location to the activity
        val activity = Distraction(name, description, null, null, length = Distraction.Length.SHORT))
        service.postDistraction(activity,
            {
                Toast.makeText(
                    this@AddDistraction,
                    "Activity added",
                    Toast.LENGTH_SHORT
                ).show()
            }, {
                Toast.makeText(
                    this@AddDistraction,
                    "Error while adding activity",
                    Toast.LENGTH_SHORT
                ).show(
                )
            })
    }
}