package com.github.studydistractor.sdp.eventList

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.ui.state.EventListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EventListViewModel(
    private val eventListModel: EventListModel
) : ViewModel() {
    private val _uiState = MutableStateFlow(EventListUiState())
    val uiState: StateFlow<EventListUiState> = _uiState.asStateFlow()
    private val currentTime = Calendar.getInstance()
    private val timeFormat = SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.ENGLISH);
    private var pairEventCount: MutableList<Pair<Event, Int>>
    init {
        pairEventCount =
            getAvailableEvents(eventListModel.getAllEvents()).map { event -> Pair(event, 0) } as MutableList<Pair<Event, Int>>
        updateEvents()
    }

    /**
     * Show all the events that should be displayed in the list
     */
    fun updateEvents() {
        val events = getAvailableEvents(eventListModel.getAllEvents())
        //Check if we have a new event that has no listener for the count
        for(event in events) {
            if(!pairEventCount.map { p -> p.first.eventId }.contains(event.eventId)) {
                pairEventCount.add(Pair(event, 0))
                subscribeToParticipantCount(event)
            }
        }

        _uiState.update {
            it.copy(eventList = pairEventCount.toList())
        }
    }

    private fun getAvailableEvents(events: List<Event>, time: Calendar = currentTime): List<Event> {
        return events.filter { event -> isEventBeforeTime(event, time) }
    }

    /**
     * Check if an event is in the past or if the event allows late participation then check
     * if it is no ended yet
     */
    fun isEventBeforeTime(event: Event, time: Calendar = currentTime): Boolean{
        val cal = Calendar.getInstance();
        return try {
            if(event.lateParticipation) {
                cal.time = timeFormat.parse(event.end)!!
                time < cal
            } else {
                cal.time = timeFormat.parse(event.start)!!
                time < cal
            }
        } catch (e : Exception) {
            Log.d("Events date","can't convert to time format")
            false
        }
    }

    /**
     * Subscribe to the count so that the count on the screen is updated live
     */
    private fun subscribeToParticipantCount(event: Event) {
        eventListModel.subscribeToEventParticipants(
            eventId = event.eventId!!,
            successListener = {
                count ->
                    Log.d("Count", "Success for : " + event.name)
                    for(i in 1 until pairEventCount.count()) {
                        if(pairEventCount[i].first.eventId.equals(event.eventId)) {
                            pairEventCount[i] = Pair(pairEventCount[i].first, count)
                        }
                    }
                    updateEvents()
            }
        )
    }
}