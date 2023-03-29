package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview("Login Screen")
fun LoginScreen() {
    Screen {
        Column() {
            Text(
                text = "Login screen.",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.testTag("login")
            )
        }
    }
}