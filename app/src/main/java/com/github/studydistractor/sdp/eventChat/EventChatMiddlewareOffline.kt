package com.github.studydistractor.sdp.eventChat

import android.content.Context
import com.github.studydistractor.sdp.data.Message
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import dagger.hilt.android.internal.Contexts.getApplication
import io.github.irgaly.kottage.Kottage
import io.github.irgaly.kottage.KottageEnvironment
import io.github.irgaly.kottage.KottageStorage
import io.github.irgaly.kottage.platform.contextOf
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

class EventChatMiddlewareOffline constructor(
    private val service: EventChatModel,
    private val context: Context // it does not correspond to MVVM but kottage requires it.
): EventChatModel {
    private val messages: MutableList<Message> = mutableListOf()
    private var onlineStatus: Boolean = false
    private var eventId: String = ""
    private var onMessagesChange: (List<Message>) -> Unit = {}

    val databaseDirectory: String = "kottage-database-directory"
    val kottageEnvironment: KottageEnvironment = KottageEnvironment(
        context = contextOf(getApplication().applicationContext),
    )
    // Initialize with Kottage database information.
    val kottage: Kottage = Kottage(
        name = "kottage-event-chat", // This will be database file name
        directoryPath = databaseDirectory,
        environment = kottageEnvironment,
        scope = MainScope(),
        json = Json.Default
    )

    val cache: KottageStorage = kottage.cache("event-chat")

    override fun observeMessages(onChange: (List<Message>) -> Unit) {
        this.onMessagesChange = onChange
        // If we were always offline, we added the data to `messages` in `changeCurrentChat`
        // but it might not have been given to the viewModel. We give it (maybe again) here.
        onMessagesChange(messages)
        service.observeMessages {
            messages.clear()
            messages.addAll(it)
            MainScope().launch {
                cache.put<List<Message>>(eventId, messages, typeOf<List<Message>>())
            }
            onChange(messages)
        }
    }

    override fun postMessage(message: String): Task<Void> {
        if (!onlineStatus) return Tasks.forException(Exception("Not connected to the internet"))
        return service.postMessage(message)
    }


    @Serializable
    data class MessageList(val messages: List<Message>)

    override fun changeCurrentChat(eventId: String): Task<Void> {
        this.eventId = eventId
        this.messages.clear()
        MainScope().launch {
            val cachedMessages = cache.get<List<Message>>(eventId,  typeOf<List<Message>>())
            messages.addAll(cachedMessages)
        }
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