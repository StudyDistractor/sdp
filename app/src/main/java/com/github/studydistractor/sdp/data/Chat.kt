package com.github.studydistractor.sdp.data

/**
 * Represent a chat (place where users can exchange messages)
 *
 * @param chatId id of the chat
 * @param messages list of messages that were sent in the chat
 */
data class Chat(
    val chatId: String? = null,
    val messages: List<Message> = listOf()
)

/**
 * Represent a message that is sent in the chat
 *
 * @property messageId id of the message
 * @property timeStamp timestamp of the message (time when the message was sent)
 * @property userId id of the user that sent the message
 */
data class Message(
    val messageId: String? = null,
    val timeStamp: String? = null,
    val userId: userId? = null
)