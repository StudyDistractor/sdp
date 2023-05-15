package com.github.studydistractor.sdp.eventChat

import com.github.studydistractor.sdp.data.Message
import com.google.android.gms.tasks.Task

interface EventChatModel {

    fun observeMessages(onChange : (List<Message>) -> Unit)

    fun getMessages() : Task<List<Message>>

    fun postMessage(message : String) : Task<Void>

    fun changeCurrentChat(eventId : String) : Task<Void>
}