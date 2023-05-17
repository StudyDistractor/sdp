package com.github.studydistractor.sdp.eventHistory

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.data.EventParticipants
import com.github.studydistractor.sdp.ui.state.EventHistoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class EventHistoryViewModel(
    eventHistoryModel: EventHistoryModel
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventHistoryUiState())
    private val _eventHistoryModel = eventHistoryModel
    private var events : List<Event> = listOf()
    private var eventParticipants: List<EventParticipants> = listOf()

    val uiState: StateFlow<EventHistoryUiState> = _uiState.asStateFlow()

    init {
        _eventHistoryModel.observeEvents {
            t -> events = t
            refreshEventHistory()
        }
        _eventHistoryModel.observeEventsParticipants {
            t -> eventParticipants = t
            refreshEventHistory()
        }

    }

    private fun eventIsFinished(event: Event): Boolean {
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm")
        val date = Date()
        val endDate : Date
        try {
            endDate = formatter.parse(event.end)
        } catch (ep: ParseException) {
            return false
        }

        return endDate.before(date)
    }

    fun refreshEventHistory(){
        val uid = _eventHistoryModel.getCurrentUid().result
        val usersEvent : SnapshotStateList<Event> = mutableStateListOf()

        for (eP in eventParticipants){
            if (eP.participants == null) continue
            if (eP.participants.contains(uid)){
                for (ev in events){
                    if (ev.eventId == eP.eventId && eventIsFinished(ev)){
                        usersEvent.add(ev)
                    }
                }
            }
        }

        _uiState.update { EventHistoryUiState(usersEvent) }
    }

}