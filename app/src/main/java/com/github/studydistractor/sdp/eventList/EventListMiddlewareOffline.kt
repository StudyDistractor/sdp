package com.github.studydistractor.sdp.eventList

import android.net.ConnectivityManager
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.roomdb.RoomDatabase
import com.github.studydistractor.sdp.utils.OnlineStatus

class EventListMiddlewareOffline constructor(
    private val service: EventListModel,
    private val database: RoomDatabase,
    private val connectivityManager : ConnectivityManager
)  : EventListModel{


    override fun getAllEvents(): List<Event> {
        if(OnlineStatus().isOnline(connectivityManager)){
            val events = service.getAllEvents()
            val eventEntity = events.map { it.toEventEntity() }
            for(e in eventEntity){
                e.history = 0
            }
            database.eventListDao().delete()
            database.eventListDao().insertAll(eventEntity)
            return events
        }
        return database.eventListDao().getAllHistory().map { it.toEvent() }
    }

    override fun subscribeToEventParticipants(
        eventId: String,
        successListener: (Int) -> Unit,
        failureListener: (String) -> Unit
    ) {
        if(OnlineStatus().isOnline(connectivityManager)){
            service.subscribeToEventParticipants(eventId, successListener, failureListener)
        } else {
            failureListener("Not connected to wifi")
        }
    }
    fun deleteCache(){
        database.eventChatDao().delete()
    }
}