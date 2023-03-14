package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    content: @Composable ()->Unit
) {
    Scaffold(
        topBar = { AppBarTop() },
        bottomBar = { AppBarBottom() }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .padding(horizontal = 16.dp)) { content() }
    }
}