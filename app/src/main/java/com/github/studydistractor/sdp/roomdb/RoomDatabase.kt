package com.github.studydistractor.sdp.roomdb

import androidx.room.Database
import com.github.studydistractor.sdp.data.EventEntity
import com.github.studydistractor.sdp.data.Message
import com.github.studydistractor.sdp.eventChat.EventChatDao
import com.github.studydistractor.sdp.eventHistory.EventHistoryDao
import com.github.studydistractor.sdp.eventList.EventListDao

/**
 * The RoomDatabase of the StudyDistractorApp
 */
@Database(entities = [Message::class, EventEntity::class], version = 1)
abstract class RoomDatabase: androidx.room.RoomDatabase() {
     /**
      * Dao for the EventChat
      */
     abstract fun eventChatDao(): EventChatDao
     /**
      * Dao for the EventHistory
      */
     abstract fun eventHistoryDao(): EventHistoryDao
     /**
      * Dao for the EventList
      */
     abstract fun eventListDao(): EventListDao
}