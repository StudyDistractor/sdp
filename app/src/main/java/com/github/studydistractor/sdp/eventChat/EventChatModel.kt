package com.github.studydistractor.sdp.eventChat

import com.github.studydistractor.sdp.data.Message
import com.google.android.gms.tasks.Task

interface EventChatModel {
    /**
     * Observe the updates of the messages and call the method onChange each time the messages
     * update
     * @param onChange A fonction that take a like of message and is call each time the messages
     * is updated
     */
    fun observeMessages(onChange : (List<Message>) -> Unit)

    /**
     * Post the given message to the chat.
     * @param message The message to post
     * @return A task successful if the message as been post
     */
    fun postMessage(message : String) : Task<Void>

    /**
     * Change the current chat to fetch messages
     * @param eventId the id of the event to fetch messages from
     * @return Task successful is the currentChat as been updated.
     */
    fun changeCurrentChat(eventId : String) : Task<Void>

    /**
     * Observe online status
     * @param onChange A function that take a boolean and is called each time the online status
     */
    fun observeOnlineStatus(onChange : (Boolean) -> Unit)
}