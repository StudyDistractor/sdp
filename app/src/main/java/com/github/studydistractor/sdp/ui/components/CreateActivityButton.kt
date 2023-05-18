package com.github.studydistractor.sdp.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import com.github.studydistractor.sdp.createActivity.CreateActivityViewModel


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