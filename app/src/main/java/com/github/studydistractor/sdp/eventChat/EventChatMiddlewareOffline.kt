package com.github.studydistractor.sdp.eventChat

import com.github.studydistractor.sdp.data.Message
import com.github.studydistractor.sdp.roomdb.RoomDatabase
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class EventChatMiddlewareOffline constructor(
    private val database: RoomDatabase,
    private val service: EventChatModel,
): EventChatModel {
    private val messages: MutableList<Message> = mutableListOf()
    private var onlineStatus: Boolean = false
    private var eventId: String = ""
    private var onMessagesChange: (List<Message>) -> Unit = {}

    override fun observeMessages(onChange: (List<Message>) -> Unit) {
        this.onMessagesChange = onChange
        // If we were always offline, we added the data to `messages` in `changeCurrentChat`
        // but it might not have been given to the viewModel. We give it (maybe again) here.
        onMessagesChange(messages)
        service.observeMessages {
            messages.clear()
            messages.addAll(it)
            MainScope().launch {
                database.eventChatDao().insertAll(
                    messages.map { message -> DaoMessage(eventId, message) }
                )
            }
            onChange(messages)
        }
    }

    override fun postMessage(message: String): Task<Void> {
        if (!onlineStatus) return Tasks.forException(Exception("Not connected to the internet"))
        return service.postMessage(message)
    }

    override fun changeCurrentChat(eventId: String): Task<Void> {
        this.eventId = eventId
        this.messages.clear()
        val cachedMessages = database.eventChatDao().getAllMessages(eventId)
        messages.addAll(cachedMessages)
        onMessagesChange(messages)
        return service.changeCurrentChat(eventId)
    }

    override fun observeOnlineStatus(onChange: (Boolean) -> Unit) {
        service.observeOnlineStatus { newOnlineStatus ->
            onlineStatus = newOnlineStatus
            onChange(newOnlineStatus)
        }
    }
}