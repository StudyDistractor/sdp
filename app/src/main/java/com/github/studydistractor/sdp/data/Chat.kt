package com.github.studydistractor.sdp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represent a chat (place where users can exchange messages)
 *
 * @param chatId id of the chat
 * @param messageIds list of messageId that were sent in the chat
 */
data class Chat(
    val chatId: String?,
    val messageIds: List<String>
)

/**
 * Represent a chat that is received or sent from/to the firebase database
 *
 * @param chatId id of the chat
 * @param messageIds list of messageId that were sent in the chat
 */
data class FirebaseChat(
    val chatId: String?,
    val messageIds: List<String>? = null
) {

}

/**
 * Represent a message that is sent in the chat
 *
 * @property messageId id of the message
 * @property timeStamp timestamp of the message (time when the message was sent)
 * @property userId id of the user that sent the message
 * @property message the message sent
 */
@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val messageId: String,
    val timeStamp: Long,
    val userId: String,
    val message : String,
    var eventId : String,
)

/**
 * Represent a message that is received or sent from/to the firebase database
 *
 * @property messageId id of the message
 * @property timeStamp timestamp of the message (time when the message was sent)
 * @property userId id of the user that sent the message
 * @property message the message sent
 */
data class FirebaseMessage(
    val messageId: String? = null,
    val timeStamp: Long? = null,
    val userId: String? = null,
    val message : String? = null,
) {
    fun toMessage(): Message {
        return Message(
            messageId!!, timeStamp!!, userId!!, message!!, ""
        )
    }
}