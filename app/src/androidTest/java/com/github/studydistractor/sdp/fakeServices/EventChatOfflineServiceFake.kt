package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.data.Message
import com.github.studydistractor.sdp.eventChat.EventChatModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class EventChatOfflineServiceFake : EventChatModel {
    val listOfMessages = listOf(
        Message("1", 10000L, "ramond","comment ca va ?", ""),
        Message("2", 10001L, "michel","bien", "")
    )
    override fun observeMessages(onChange: (List<Message>) -> Unit) {
        onChange(listOfMessages)
    }

    override fun postMessage(message: String): Task<Void> {
        return Tasks.forException(Exception("Error no wifi"))
    }

    override fun changeCurrentChat(eventId: String): Task<Void> {
        return Tasks.forResult(null)
    }
}