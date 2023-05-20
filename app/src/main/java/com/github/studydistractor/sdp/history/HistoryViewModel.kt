package com.github.studydistractor.sdp.history

import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.ui.state.HistoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HistoryViewModel(
    historyModel: HistoryModel
) : ViewModel() {
    private val _historyModel: HistoryModel = historyModel
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    fun refreshHistoryEntries() {
        if(_historyModel.getCurrentUid() == null) return
        val uid = _historyModel.getCurrentUid()!!
        _uiState.update {
            it.copy(
                historyEntries = _historyModel.getHistory(uid)
            )
        }
    }

    init {
        refreshHistoryEntries()
    }
}
