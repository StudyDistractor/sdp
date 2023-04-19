package com.github.studydistractor.sdp.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.distraction.Distraction
import com.github.studydistractor.sdp.distraction.DistractionViewModel
import com.github.studydistractor.sdp.distraction.DistractionService

/**
 *  Screen showing the list of current available distraction on the DistractionService
 */
@Composable
fun DistractionListScreen(distractionService : DistractionService,
                          onDistractionClick  : () -> Unit = {},
                          distractionViewModel : DistractionViewModel
                      ) {
    val distractions = distractionService.fetchDistractions()
    LazyColumn(){
        items(distractions) {distraction ->
            DistractionLayout(distraction, onDistractionClick , distractionViewModel)
            Divider(
                modifier = Modifier.padding(horizontal = 14.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
            )
        }
    }
}

/**
 * Distraction layout
 */
@Composable
fun DistractionLayout(activity : Distraction,
                      onDistractionClick : () -> Unit = {},
                      distractionViewModel : DistractionViewModel) {
    Box(
        modifier = Modifier
            .clickable {
                distractionViewModel.addDistraction(activity)
                onDistractionClick () }
            .testTag("distractionLayout")
    ) {
        Row(modifier = Modifier
            .padding(all = 14.dp)
            .fillMaxWidth()
            .fillMaxHeight()
        ) {
            Text(
                text = activity.name!!,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.testTag("name")
            )
        }
    }
}