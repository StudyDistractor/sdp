package com.github.studydistractor.sdp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.testTag
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint(AppCompatActivity::class)
class AddProcrastinationActivityActivity : Hilt_AddProcrastinationActivityActivity() {
    @Inject
    lateinit var service: ProcrastinationActivityService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateNewActivityLayout()
        }
    }

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
                onValueChange = { name.value = it },
                modifier = Modifier.testTag("name")
            )

            OutlinedTextField(
                value = description.value,
                label = {Text("description")},
                onValueChange = { description.value = it },
                modifier = Modifier.testTag("description")
            )

            Button(onClick = {createNewActivity(name.value.text, description.value.text)},
                modifier = Modifier.testTag("addActivity")
            ) {
                Text("Create new activity")
            }
        }
    }

    private fun createNewActivity(name: String, description: String) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)) {
            Toast.makeText(this@AddProcrastinationActivityActivity, "Please fill the blanks", Toast.LENGTH_SHORT).show()
            return
        }

        val activity = ProcrastinationActivity(name, description)
        service.postProcastinationActivities(activity)
    }
}