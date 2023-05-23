package com.github.studydistractor.sdp.roomdb

import androidx.room.Database
import com.github.studydistractor.sdp.data.Message
import com.github.studydistractor.sdp.eventChat.DaoMessage
import com.github.studydistractor.sdp.eventChat.EventChatDao
import java.io.Serializable

@Database(entities = [Message::class, DaoMessage::class], version = 1)
public abstract class RoomDatabase: androidx.room.RoomDatabase(), Serializable {
    public abstract fun eventChatDao(): EventChatDao;
}