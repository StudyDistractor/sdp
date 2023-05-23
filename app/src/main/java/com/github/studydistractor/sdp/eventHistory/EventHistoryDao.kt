package com.github.studydistractor.sdp.eventHistory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.studydistractor.sdp.data.EventEntity
@Dao
interface EventHistoryDao {
    @Query("SELECT *  FROM event e WHERE e.history = 1")
    fun getAllHistory(): List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(messages: EventEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<EventEntity>): List<Long>

    @Query("DELETE FROM event")
    fun delete()
}