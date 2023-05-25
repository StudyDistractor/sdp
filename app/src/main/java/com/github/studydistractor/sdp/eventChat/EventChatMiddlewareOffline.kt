package com.github.studydistractor.sdp.eventChat

import android.net.ConnectivityManager
import android.os.AsyncTask
import com.github.studydistractor.sdp.data.Message
import com.github.studydistractor.sdp.roomdb.RoomDatabase
import com.github.studydistractor.sdp.utils.OnlineStatus
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class EventChatMiddlewareOffline constructor(
    private val database: RoomDatabase,
    private val service: EventChatModel,
    private val connectivityManager : ConnectivityManager
): EventChatModel {
    private val messages: MutableList<Message> = mutableListOf()
    private var eventId: String = ""
    private var onMessagesChange: (List<Message>) -> Unit = {}

    override fun observeMessages(onChange: (List<Message>) -> Unit) {
        this.onMessagesChange = onChange
        // If we were always offline, we added the data to `messages` in `changeCurrentChat`
        // but it might not have been given to the viewModel. We give it (maybe again) here.
        service.observeMessages {
            AsyncTask.execute {
                messages.clear()
                messages.addAll(it)
                for(i in messages){
                    database.eventChatDao().insert(i)
                }
            }
            onChange(it)
        }
    }

    override fun postMessage(message: String): Task<Void> {
        if(OnlineStatus().isOnline(connectivityManager)) return Tasks.forException(Exception())
        return service.postMessage(message)
    }

    override fun changeCurrentChat(eventId: String): Task<Void> {
        this.eventId = eventId
        AsyncTask.execute{
            val cachedMessages = database.eventChatDao().getAllMessages(eventId)
            this.messages.clear()
            messages.addAll(cachedMessages)
            onMessagesChange(messages)
        }
        return service.changeCurrentChat(eventId)
    }
    fun deleteCache(){
        database.eventChatDao().delete()
    }
}