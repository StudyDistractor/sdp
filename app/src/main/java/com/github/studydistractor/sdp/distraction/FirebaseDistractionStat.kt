package com.github.studydistractor.sdp.procrastinationActivity

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseDistractionStat : DistractionStatInterface {

    private val pathFeedback = "Feedback"
    private val pathLikes = "Likes"
    private val pathDislikes = "Dislikes"
    private val pathTags = "Tags"
    private val db = FirebaseDatabase.getInstance()

    override fun fetchDistractionFeedback(did: String): SnapshotStateList<String> {
        if(did.isEmpty()) throw  IllegalArgumentException()
        val databaseRef = db.getReference(pathFeedback).child(did)
        val result =  mutableStateListOf<String>()
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(id in snapshot.children) {
                    val tag = id.getValue(String::class.java)
                    if(tag != null) {
                        result.add(tag)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })

        return result
    }

    override fun fetchDistractionTags(did: String): SnapshotStateList<String> {
        if(did.isEmpty()) throw  IllegalArgumentException()
        val databaseRef = db.getReference(pathTags).child(did)
        val result =  mutableStateListOf<String>()
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(id in snapshot.children) {
                    val tag = id.getValue(String::class.java)
                    if(tag != null) {
                        result.add(tag)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })

        return result
    }

    override fun fetchLikeCount(did: String): MutableState<Int> {
        if(did.isEmpty()) throw  IllegalArgumentException()
        val c = mutableStateOf(0)
        val databaseRef = db.getReference(pathLikes).child(did)
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                c.value = snapshot.children.count()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })

        return c
    }

    override fun fetchDislikeCount(did: String): MutableState<Int> {
        if(did.isEmpty()) throw  IllegalArgumentException()
        val c = mutableStateOf(0)
        val databaseRef = db.getReference(pathDislikes).child(did)
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                c.value = snapshot.children.count()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })

        return c
    }

    override fun postNewFeedback(did: String, uid: String, feedback: String) {
        if(did.isEmpty()) throw  IllegalArgumentException()
        if(uid.isEmpty()) throw  IllegalArgumentException()
        if(feedback.isEmpty()) throw  IllegalArgumentException()
        db.getReference(pathFeedback).child(did).child(uid).setValue(feedback)
    }

    override fun postLike(did: String, uid: String) {
        if(did.isEmpty()) throw  IllegalArgumentException()
        if(uid.isEmpty()) throw  IllegalArgumentException()
        db.getReference(pathLikes).child(did).setValue(uid)
    }

    override fun postDislike(did: String, uid: String) {
        if(did.isEmpty()) throw  IllegalArgumentException()
        if(uid.isEmpty()) throw  IllegalArgumentException()
        db.getReference(pathDislikes).child(did).setValue(uid)
    }

    override fun addTag(did: String, tag: String) {
        if(did.isEmpty()) throw  IllegalArgumentException()
        if(tag.isEmpty()) throw  IllegalArgumentException()
        val key = db.getReference(pathTags).child(did).key ?: return
        db.getReference(pathTags).child(did).child(key).setValue(tag)
    }

    override fun removeTag(did: String, tag: String) {
        if(did.isEmpty()) throw  IllegalArgumentException()
        if(tag.isEmpty()) throw  IllegalArgumentException()
        db.getReference(pathTags).child(did).child(tag).removeValue()
    }
}