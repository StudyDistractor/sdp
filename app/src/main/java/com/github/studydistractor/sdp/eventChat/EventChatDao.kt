package com.github.studydistractor.sdp.eventChat

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.github.studydistractor.sdp.data.Message

@Entity(tableName = "daomessages")
public data class DaoMessage(
    @PrimaryKey(autoGenerate = true)
    val eventId: String? = null,
    val message: Message? = null
)

@Dao
public interface EventChatDao {
    @Query("SELECT m.message FROM daomessages m WHERE m.eventId = :eventId")
    public abstract fun getAllMessages(eventId: String): List<Message>;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(messages: DaoMessage): List<Long>;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<DaoMessage>): List<Long>;
}