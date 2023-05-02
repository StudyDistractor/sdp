package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.studydistractor.sdp.history.HistoryModel
import com.github.studydistractor.sdp.history.HistoryViewModel
import com.github.studydistractor.sdp.ui.components.MessageCard

/**
 *  Screen showing the history of the current logged user fetching data online using the HistoryInterface.
 *
 * Author: Bluedrack
 */
@Composable
fun HistoryScreen(historyViewModel: HistoryViewModel) {
    val uiState by historyViewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(6.dp)){
        Text("History :",
            fontSize = 20.sp,
        )
        LazyColumn(){
            items(uiState.historyEntries) {i->
                MessageCard(i)
            }
        }

    }
}