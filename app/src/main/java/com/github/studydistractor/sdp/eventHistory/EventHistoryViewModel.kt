package com.github.studydistractor.sdp.eventHistory

import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.ui.state.EventHistoryUiState
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EventHistoryViewModel(
    eventHistoryModel: EventHistoryModel
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventHistoryUiState())
    private val _eventHistoryModel = eventHistoryModel

    val uiState: StateFlow<EventHistoryUiState> = _uiState.asStateFlow()

    fun setUserId(userId: String) {
        _uiState.update { it.copy(userId = userId) }
        _eventHistoryModel.observeHistory(
            userId = userId,
            onHistoryChange = { history ->
                _uiState.update { it.copy(eventHistory = history) }
            }
        )
    }

    /**
     * Claim the points of a particular event and give them to the currentUser
     * if not already claimed
     * @param eventId the id of the particular event
     * @return a task
     */
    fun claimPoints(eventId: String): Task<Void> {
        return _eventHistoryModel.claimPoints(
            userId = uiState.value.userId,
            eventId = eventId
        )
    }
}