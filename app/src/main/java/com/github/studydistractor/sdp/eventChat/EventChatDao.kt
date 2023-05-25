package com.github.studydistractor.sdp.eventChat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.studydistractor.sdp.data.Message

/**
 * EventChat interface that handle the call to the room database
 */
@Dao
interface EventChatDao {
    /**
     * Get all message from the EventChat specified to the function
     * @param eventId The id of the EventChat to retrieve the messages from
     */
    @Query("SELECT *  FROM messages m WHERE m.eventId like :eventId")
    fun getAllMessages(eventId : String): List<Message>

    /**
     * Add the message to the room database
     * @param message to add to the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: Message): Long

    /**
     * Add all the message on the list in the room database
     * @param messages to add to the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<Message>): List<Long>

    /**
     * Delete all the messages present in the room database to clear the cache
     */
    @Query("DELETE FROM messages")
    fun delete()
}