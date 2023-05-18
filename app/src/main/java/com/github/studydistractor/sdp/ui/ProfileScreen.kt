package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun ProfileScreen(){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("login-screen__main-container"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Log In",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 32.dp)
        )
    }
}