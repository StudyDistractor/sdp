package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    onRegistered: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .testTag("register-screen__main-container")
    ) {
        Text(text = "Register screen")
        TextButton(
            onClick = onRegistered,
            modifier = Modifier.testTag("register-screen__registered-button")
        ) {
            Text(text = "Registered")
        }
    }
}