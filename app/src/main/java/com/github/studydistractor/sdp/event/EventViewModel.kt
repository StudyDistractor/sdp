package com.github.studydistractor.sdp.event

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.ui.state.EventUiState
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class EventViewModel(eventModel: EventModel): ViewModel() {
    private val _eventModel: EventModel = eventModel
    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()

    init {
        _eventModel.subscribeToUserId(
            successListener = { uid ->
                _uiState.update { it.copy(userId = uid) }
                //setEventId(_uiState.value.eventId)
            }
        )
    }

    private fun getToggleParticipationButtonText(participating: Boolean): String {
        return if(participating) "Withdraw" else "Participate"
    }

    private fun getParticipantsHeadingText(isParticipantsEmpty: Boolean): String {
        return if(isParticipantsEmpty) {
            "Be the first to participate!"
        } else {
            "Participants"
        }
    }

    private fun canParticipate(
        event: Event = _uiState.value.event
    ): Boolean {
        val currentDate = Date()
        return try {
            val startDate = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(event.start)
            val endDate = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(event.end)

            (currentDate.before(startDate)
                    || (event.lateParticipation && currentDate.before(endDate)))
        } catch (e: ParseException) {
            Log.d("EventViewModel","Corrupt database: unparseable date")
            false
        }
    }

    private fun participateToEvent(): Task<Void> {
      if(!canParticipate()) return Tasks.forException(Exception("Cannot participate to this event."))
      return _eventModel.addParticipant(
            eventId = uiState.value.eventId,
            userId = uiState.value.userId
        ).continueWith {
            updateParticipationUiState(true)
            null// fsm update returns unit and that does not cast to void
        }
    }

    private fun withdrawFromEvent(): Task<Void> {
         return _eventModel
                .removeParticipant(
                    eventId = uiState.value.eventId,
                    userId = uiState.value.userId
                )
                .continueWith {
                    updateParticipationUiState(false)
                    null// fsm update returns unit and that does not cast to void
                }
    }

    fun setEventId(eventId: String) {
//        _uiState.update { it.copy(eventId = eventId) }

        _eventModel.unsubscribeFromAllEvents()
        _eventModel.unsubscribeFromAllEventParticipants()

        _eventModel.subscribeToEvent(
            eventId = eventId,
            successListener = {event ->
                _eventModel.isParticipating(eventId, _uiState.value.userId)
                    .addOnSuccessListener { participating ->
                        _uiState.update { it.copy(
                            event = event,
                            participating = participating,
                            toggleParticipationButtonText = getToggleParticipationButtonText(participating),
                            canParticipate = canParticipate(event),
                            eventId = eventId
                        )
                    }
                }
            }
        )

        _eventModel.subscribeToEventParticipants(
            eventId = eventId,
            successListener = {eventParticipants ->
                _uiState.update {
                    it.copy(
                        participants = eventParticipants,
                        participantsHeadingText = getParticipantsHeadingText(eventParticipants.isEmpty())
                    )
                }
            }
        )
    }

    private fun updateParticipationUiState(participating: Boolean) {
        _uiState.update {
            it.copy(
                participating = participating,
                toggleParticipationButtonText =
                    getToggleParticipationButtonText(participating)
            )
        }
    }

    /**
     * Toggle participation of the current user to the current event.
     */
    fun toggleParticipation(): Task<Void> {
        return if (uiState.value.participating) {
            withdrawFromEvent()
        } else {
            participateToEvent()
        }
    }
}