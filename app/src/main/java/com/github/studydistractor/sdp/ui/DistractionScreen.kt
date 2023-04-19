package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.distraction.DistractionViewModel

@Composable
fun DistractionScreen(distractionViewModel : DistractionViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(26.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = distractionViewModel.distraction!!.name!!,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.testTag("name")
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = distractionViewModel.distraction!!.description!!,
            modifier = Modifier.testTag("description")
        )
    }
}