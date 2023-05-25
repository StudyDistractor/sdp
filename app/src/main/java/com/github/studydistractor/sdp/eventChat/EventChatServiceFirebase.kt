package com.github.studydistractor.sdp.eventChat

import android.util.Log
import com.github.studydistractor.sdp.data.FirebaseMessage
import com.github.studydistractor.sdp.data.Message
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.NullPointerException
import java.util.Date

class EventChatServiceFirebase : EventChatModel {
    private val CHATPATH = "ChatEvent"

    private val db = FirebaseDatabase.getInstance().getReference(CHATPATH)
    private val auth = FirebaseAuth.getInstance()

    private var currentChat : String = "test"

    private var onChange : (List<Message>)-> Unit = {}

    private val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val list = arrayListOf<Message>()
            for(m in snapshot.children){
                try {
                    val message = m.getValue(FirebaseMessage::class.java)
                    val m = message!!.toMessage()
                    m.eventId = currentChat
                    list.add(m)
                } catch (_: NullPointerException){
                    Log.d("ERROR",
                        "Unable to fetch data from firebase due to structure difference"
                    )
                }
            }
            onChange(list)
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    init {
        db.child(currentChat).addValueEventListener(listener)
    }

    override fun observeMessages(onChange: (List<Message>) -> Unit) {
        this.onChange = onChange
    }

    override fun postMessage(message: String): Task<Void> {
        if(auth.uid == null) return Tasks.forException(Exception())
        val newMessage = db.child(currentChat).push()
        val m = FirebaseMessage(
            messageId = newMessage.key!!,
            message = message,
            userId = auth.uid!!,
            timeStamp = Date().time,
        )
        return newMessage.setValue(m)
    }

    override fun changeCurrentChat(eventId: String): Task<Void> {
        db.child(currentChat).removeEventListener(listener)
        currentChat = eventId
        db.child(currentChat).addValueEventListener(listener)
        return Tasks.forResult(null)
    }
}