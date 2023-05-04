package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
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

    LazyColumn(){
        items(uiState.historyEntries) {i->
            MessageCard(i)
        }
    }
}