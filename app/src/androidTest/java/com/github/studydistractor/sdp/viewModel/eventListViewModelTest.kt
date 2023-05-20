package com.github.studydistractor.sdp.viewModel

import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.eventList.EventListViewModel
import com.github.studydistractor.sdp.fakeServices.EventListServiceFake
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EventListViewModelTest {

    private val eventListViewModel = EventListViewModel(EventListServiceFake())
    private val time = Calendar.getInstance()
    private val format = SimpleDateFormat(
        "dd-MM-yyyy hh:mm",
        Locale.ENGLISH);
    private var before: String
    private var after: String
    init {
        time.add(Calendar.YEAR, -1)
        before = format.format(time.time)
        time.add(Calendar.YEAR, 2)
        after = format.format(time.time)
    }
    @Test
    fun uiStateIsUpdatedAtInit(){
        val events = eventListViewModel.uiState.value.eventList
        assertEquals(2, events.size)
    }

    @Test
    fun invalidDateAreFalse() {
        val eventWithWrongDataFormat = Event(
            "id",
            "event name",
            "desciption",
            0.0,
            0.0,
            "wrong date format",
            "wrong date format",
            false,
            0,
            "chatId"
        )

        assertEquals(false, eventListViewModel.isEventBeforeTime(eventWithWrongDataFormat))
    }

    @Test
    fun eventThatStartsAfterIsTrue(){
        val stillValidEvent = Event(
            "id",
            "event name",
            "desciption",
            0.0,
            0.0,
            after,
            after,
            false,
            0,
            "chatId"
        )
        assertEquals(true, eventListViewModel.isEventBeforeTime(stillValidEvent))
    }

    @Test
    fun lateEventThatEndsAfterIsTrue() {
        val lateEvent = Event(
            "id",
            "event name",
            "desciption",
            0.0,
            0.0,
            before,
            after,
            true,
            0,
            "chatId"
        )
        assertEquals(true, eventListViewModel.isEventBeforeTime(lateEvent))
    }

    @Test
    fun nonlateEventThatStartBeforeIsFalse() {
        val lateEvent = Event(
            "id",
            "event name",
            "desciption",
            0.0,
            0.0,
            before,
            after,
            false,
            0,
            "chatId"
        )
        assertEquals(false, eventListViewModel.isEventBeforeTime(lateEvent))
    }
}