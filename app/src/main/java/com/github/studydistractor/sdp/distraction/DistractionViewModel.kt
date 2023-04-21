package com.github.studydistractor.sdp.distraction

import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.ui.state.DistractionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * This class help passing distraction data between views
 */
class DistractionViewModel(
    distractionModel: DistractionModel
) : ViewModel(){
    private val _distractionModel: DistractionModel = distractionModel
    private val _uiState = MutableStateFlow(DistractionUiState())
    val uiState: StateFlow<DistractionUiState> = _uiState.asStateFlow()

    fun updateDistraction(distraction: Distraction) {
        _uiState.update { it.copy(distraction = distraction) }
    }
}