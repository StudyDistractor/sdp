package com.github.studydistractor.sdp.roomdb

import androidx.room.Database
import com.github.studydistractor.sdp.data.EventEntity
import com.github.studydistractor.sdp.data.Message
import com.github.studydistractor.sdp.eventChat.EventChatDao
import com.github.studydistractor.sdp.eventHistory.EventHistoryDao
import com.github.studydistractor.sdp.eventList.EventListDao

@Database(entities = [Message::class, EventEntity::class], version = 1)
abstract class RoomDatabase: androidx.room.RoomDatabase() {
     abstract fun eventChatDao(): EventChatDao
     abstract fun eventHistoryDao(): EventHistoryDao
     abstract fun eventListDao(): EventListDao
}