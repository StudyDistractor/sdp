package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview("Home Screen")
fun HomeScreen() {
    val authenticated: Boolean = true;
    val firstname: String = "Aizen"

    if(!authenticated) {
        return LoginScreen()
    }

    Screen {
        Column() {
            Text(
                text = "Welcome ${firstname}!",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}