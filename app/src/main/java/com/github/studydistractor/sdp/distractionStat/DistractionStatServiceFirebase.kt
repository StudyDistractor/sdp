package com.github.studydistractor.sdp.distractionStat

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DistractionStatServiceFirebase : DistractionStatModel {

    private val pathFeedback = "Feedback"
    private val pathLikes = "Likes"
    private val pathDislikes = "Dislikes"
    private val pathTags = "TagsUsers"
    private val db = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()
    override fun fetchDistractionFeedback(did: String): Task<List<String>> {
        return db.getReference(pathFeedback).child(did).get().onSuccessTask { t->
            val feedbacks= arrayListOf<String>()
            for(id in t.children) {
                for(child in id.children){
                    val feedback = child.getValue(String::class.java)
                    if(feedback != null) {
                        feedbacks.add(feedback)
                    }

                }
            }
            Tasks.forResult(feedbacks)
        }
    }

    override fun fetchDistractionTags(did: String): Task<List<String>> {
        return db.getReference(pathTags).child(did).get().onSuccessTask { t->
            val tags = arrayListOf<String>()
            for(id in t.children) {
                val tag = id.getValue(String::class.java)
                if(tag != null) {
                    tags.add(tag)
                }
            }
            Tasks.forResult(tags)
        }
    }

    override fun fetchLikeCount(did: String): Task<Int> {
        return db.getReference(pathLikes).child(did).get().onSuccessTask {
            t -> Tasks.forResult(t.children.count())
        }
    }

    override fun fetchDislikeCount(did: String): Task<Int> {
        return db.getReference(pathDislikes).child(did).get().onSuccessTask {
                t -> Tasks.forResult(t.children.count())
        }
    }

    override fun postNewFeedback(did: String, feedback: String) : Task<Void>{
        if(feedback.isEmpty()) throw  IllegalArgumentException()
        val uid = auth.uid
        return db.getReference(pathFeedback)
            .child(did)
            .child(uid!!)
            .child(feedback)
            .setValue(feedback)
    }

    override fun postLike(did: String) : Task<Void> {
        val uid = auth.uid
        return db.getReference(pathDislikes).child(did).child(uid!!).get().continueWithTask{
                j ->
                    if(j.isSuccessful && j.result.value == uid){
                        db.getReference(pathDislikes).child(did).child(uid).removeValue()
                    }
                    db.getReference(pathLikes).child(did).child(uid).get().continueWithTask{
                        i ->
                        if(i.result.value == uid){
                            db.getReference(pathLikes).child(did).child(uid).removeValue()
                        }
                        db.getReference(pathLikes).child(did).child(uid).setValue(uid)
                    }
        }
    }

    override fun postDislike(did: String) : Task<Void>{
        val uid = auth.uid
        return db.getReference(pathLikes).child(did).child(uid!!).get().continueWithTask{
                j ->
            if(j.isSuccessful && j.result.value == uid){
                db.getReference(pathLikes).child(did).child(uid).removeValue()
            }
            db.getReference(pathDislikes).child(did).child(uid).get().continueWithTask{
                    i ->
                if(i.result.value == uid){
                    db.getReference(pathDislikes).child(did).child(uid).removeValue()
                }
                db.getReference(pathDislikes).child(did).child(uid).setValue(uid)
            }
        }
    }

    override fun addTag(did: String, tag: String): Task<Void> {
        return db.getReference(pathTags).child(did).child(tag).setValue(tag)
    }

    override fun removeTag(did: String, tag: String) : Task<Void> {
        return db.getReference(pathTags).child(did).child(tag).removeValue()
    }
    override fun updateCurrentDistraction(did: String) {

    }
}