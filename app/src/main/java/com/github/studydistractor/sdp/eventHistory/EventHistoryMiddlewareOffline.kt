package com.github.studydistractor.sdp.eventHistory

import android.net.ConnectivityManager
import android.util.Log
import com.github.studydistractor.sdp.data.Event
import com.github.studydistractor.sdp.roomdb.RoomDatabase
import com.github.studydistractor.sdp.utils.OnlineStatus
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import java.lang.Exception

class EventHistoryMiddlewareOffline constructor(
    private val service: EventHistoryModel,
    private val database: RoomDatabase,
    private val connectivityManager : ConnectivityManager
) : EventHistoryModel {

    override fun observeHistory(userId: String, onHistoryChange: (List<Event>) -> Unit) {
        Log.d("DEBUG", "IS CALLED")
        service.observeHistory(userId){
            if(OnlineStatus().isOnline(connectivityManager)){
                val h = it.map{it.toEventEntity()}
                for(e in h){
                    e.history = 1
                }
                database.eventHistoryDao().insertAll(h)
                onHistoryChange(it)
            } else {
                val h = database.eventHistoryDao().getAllHistory().map{it.toEvent()}
                onHistoryChange(h)
            }
        }
    }


    override fun claimPoints(userId: String, eventId: String): Task<Void> {
        if(OnlineStatus().isOnline(connectivityManager)) return service.claimPoints(userId, eventId)
        return Tasks.forException(Exception("Not able to claim point offline"))
    }
    fun deleteCache(){
        database.eventChatDao().delete()
    }
}