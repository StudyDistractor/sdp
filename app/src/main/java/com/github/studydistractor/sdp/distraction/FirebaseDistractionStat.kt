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

class FirebaseDistractionStat constructor(did: String): DistractionStatInterface {

    private val pathFeedback = "Feedback"
    private val pathLikes = "Likes"
    private val pathDislikes = "Dislikes"
    private val pathTags = "Tags"
    private val db = FirebaseDatabase.getInstance()

    private val feedbacks = mutableStateListOf<String>()
    private val tags = mutableStateListOf<String>()
    private val likes = mutableStateOf(0)
    private val dislikes = mutableStateOf(0)

    init{
        val feedbackRef = db.getReference(pathFeedback).child(did)
        feedbackRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                feedbacks.clear()
                for(id in snapshot.children) {
                    val feedback = id.getValue(String::class.java)
                    if(feedback != null) {
                        feedbacks.add(feedback)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })
        val tagsRef = db.getReference(pathTags).child(did)
        tagsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tags.clear()
                for(id in snapshot.children) {
                    val tag = id.getValue(String::class.java)
                    if(tag != null) {
                        tags.add(tag)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })

        val likesRef = db.getReference(pathLikes).child(did)
        likesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                likes.value = snapshot.children.count()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })
        val dislikesRef = db.getReference(pathDislikes).child(did)
        dislikesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dislikes.value = snapshot.children.count()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
            }
        })

    }
    override fun fetchDistractionFeedback(did: String): SnapshotStateList<String> {
        if(did.isEmpty()) throw  IllegalArgumentException()
        return feedbacks
    }

    override fun fetchDistractionTags(did: String): SnapshotStateList<String> {
        if(did.isEmpty()) throw  IllegalArgumentException()
        return tags
    }

    override fun fetchLikeCount(did: String): MutableState<Int> {
        if(did.isEmpty()) throw  IllegalArgumentException()
        return likes
    }

    override fun fetchDislikeCount(did: String): MutableState<Int> {
        if(did.isEmpty()) throw  IllegalArgumentException()
        return dislikes
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
        db.getReference(pathLikes).child(did).child(uid).setValue(uid)
    }

    override fun postDislike(did: String, uid: String) {
        if(did.isEmpty()) throw  IllegalArgumentException()
        if(uid.isEmpty()) throw  IllegalArgumentException()
        db.getReference(pathDislikes).child(did).child(uid).setValue(uid)
    }

    override fun addTag(did: String, tag: String) {
        if(did.isEmpty()) throw  IllegalArgumentException()
        if(tag.isEmpty()) throw  IllegalArgumentException()
        db.getReference(pathTags).child(did).child(tag).setValue(tag)
    }

    override fun removeTag(did: String, tag: String) {
        if(did.isEmpty()) throw  IllegalArgumentException()
        if(tag.isEmpty()) throw  IllegalArgumentException()
        db.getReference(pathTags).child(did).child(tag).removeValue()
    }
}