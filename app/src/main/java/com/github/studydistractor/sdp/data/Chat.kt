package com.github.studydistractor.sdp.data

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
    fun toChat(): Chat {
        return Chat(
            chatId, messageIds!!
        )
    }
}

/**
 * Represent a message that is sent in the chat
 *
 * @property messageId id of the message
 * @property timeStamp timestamp of the message (time when the message was sent)
 * @property userId id of the user that sent the message
 */
data class Message(
    val messageId: String?,
    val timeStamp: String,
    val userId: String)

/**
 * Represent a message that is received or sent from/to the firebase database
 *
 * @property messageId id of the message
 * @property timeStamp timestamp of the message (time when the message was sent)
 * @property userId id of the user that sent the message
 */
data class FirebaseMessage(
    val messageId: String? = null,
    val timeStamp: String? = null,
    val userId: String? = null) {

    fun toMessage(): Message {
        return Message(
            messageId, timeStamp!!, userId!!
        )
    }
}