package com.github.studydistractor.sdp.eventChat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.studydistractor.sdp.data.Message

@Dao
interface EventChatDao {
    @Query("SELECT *  FROM messages m WHERE m.eventId like :eventId")
    fun getAllMessages(eventId : String): List<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(messages: Message): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<Message>): List<Long>
    @Query("DELETE FROM messages")
    fun delete()
}