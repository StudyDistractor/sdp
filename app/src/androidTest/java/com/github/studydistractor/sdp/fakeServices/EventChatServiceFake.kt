package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.data.Chat
import com.github.studydistractor.sdp.data.Message
import com.github.studydistractor.sdp.eventChat.EventChatModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class EventChatServiceFake : EventChatModel {
    val listOfMessages = listOf(
        Message("1", 10000L, "ramond","comment ca va ?", ""),
        Message("2", 10001L, "michel","bien", "")
    )

    val listOfChat = listOf(
        Chat("1", listOf("1", "2")),
        Chat("2", listOf("1", "2"))
    )

    override fun observeMessages(onChange: (List<Message>) -> Unit) {
        onChange(listOfMessages)
    }

    override fun postMessage(message: String): Task<Void> {
        return Tasks.forResult(null)
    }

    override fun changeCurrentChat(eventId: String): Task<Void> {
        return Tasks.forResult(null)
    }
}