package com.github.studydistractor.sdp.eventList

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.studydistractor.sdp.data.EventEntity

@Dao
interface EventListDao {
    @Query("SELECT *  FROM event e Where e.history = 0")
    fun getAllHistory(): List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(messages: EventEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<EventEntity>): List<Long>

    @Query("DELETE FROM event")
    fun delete()
}