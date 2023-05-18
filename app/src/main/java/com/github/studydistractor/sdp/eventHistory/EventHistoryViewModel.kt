package com.github.studydistractor.sdp.eventHistory

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.data.EventClaimPoints
import com.github.studydistractor.sdp.data.EventParticipants
import com.github.studydistractor.sdp.data.UserData
import com.github.studydistractor.sdp.ui.state.EventHistoryUiState
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
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
    private var eventsClaimPoints: List<EventClaimPoints> = listOf()
    private var currentUser : UserData = UserData()
    private val usersEvent : SnapshotStateList<Event> = mutableStateListOf()

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
        _eventHistoryModel.observeEventClaimPoints {
            t -> eventsClaimPoints = t
            refreshEventHistory()
        }

        _eventHistoryModel.observeCurrentUser {
            t -> currentUser = t
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

    private fun refreshEventHistory(){
        usersEvent.clear()

        for (eP in eventParticipants){
            if (eP.participants == null) continue
            if (eP.participants.contains(currentUser.id)){
                for (ev in events){
                    if (ev.eventId == eP.eventId && eventIsFinished(ev)){
                        usersEvent.add(ev)
                    }
                }
            }
        }

        _uiState.update { EventHistoryUiState(usersEvent) }
    }

    /**
     * Claim the points of a particular event and give them to the currentUser
     * if not already claimed
     * @param eventId the id of the particular event
     * @return a task
     */
    fun claimPoints(eventId: String): Task<Void> {
        if (eventId.isEmpty()) return Tasks.forException(Exception("Empty eventId"))
        val event = getEvent(eventId) ?: return Tasks.forException(Exception("Null event"))
        for (cp in eventsClaimPoints){
            if (cp.eventId == eventId){
                if (cp.claimUser.contains(currentUser.id)){
                    return Tasks.forException(Exception("Points already claimed"))
                }

                val newClaimUser = mutableStateListOf<String>()
                newClaimUser.addAll(cp.claimUser)
                newClaimUser.add(currentUser.id)

                val newClaimPoints = EventClaimPoints(cp.eventId, newClaimUser)

                val newUser = currentUser.copy()
                newUser.updateScore(event.numberOfPoints)

                return _eventHistoryModel
                    .postUser(newUser) .continueWithTask{
                        _eventHistoryModel.postEventClaimPoints(newClaimPoints)}
            }
        }

        val newClaimPoints = EventClaimPoints(eventId, listOf(currentUser.id))

        val newUser = currentUser.copy()
        newUser.updateScore(event.numberOfPoints)


        return _eventHistoryModel
            .postUser(newUser) .continueWithTask{
                _eventHistoryModel.postEventClaimPoints(newClaimPoints)}
    }

    private fun getEvent(eventId: String): Event? {
        for (ev in usersEvent){
            if (ev.eventId == eventId) return ev
        }
        return null
    }


}