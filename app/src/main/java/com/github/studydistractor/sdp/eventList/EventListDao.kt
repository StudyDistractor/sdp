package com.github.studydistractor.sdp.eventList

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.studydistractor.sdp.data.EventEntity

@Dao
interface EventListDao {

    /**
     * Get all Event from the EventHistory specified to the function
     */
    @Query("SELECT *  FROM event e Where e.history = 0")
    fun getAllHistory(): List<EventEntity>

    /**
     * Insert the specific event to the database
     * @param event The EventEntity to add to the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: EventEntity): Long

    /**
     * Insert all the specified events to the database
     * @param events to add to the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(events: List<EventEntity>): List<Long>

    /**
     * Delete all the events in the database
     */
    @Query("DELETE FROM event")
    fun delete()
}