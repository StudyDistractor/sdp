package com.github.studydistractor.sdp.bookmark

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.distraction.Distraction
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class FirebaseBookmarks @Inject constructor() : BookmarkModel {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    private var bookmarks = SnapshotStateList<String>()
    private val BOOKMARKSPATH = "Bookmarks"
    private val databaseRef = db.getReference(BOOKMARKSPATH).child(auth.uid!!)

    private var bookmarksListener =  object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val newBookmark =  mutableStateListOf<String>()
            for (id in snapshot.children) {
                if (id.exists()) {
                    newBookmark.add(id.getValue(String::class.java)!!)
                    Log.d("bookmark", id.getValue(String::class.java)!! )
                }
                bookmarks = newBookmark
            }
        }
        override fun onCancelled(error: DatabaseError) {
            Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
        }
    }

    init {
        databaseRef.addValueEventListener(bookmarksListener)
    }

    override fun addDistractionToBookmark(distraction: Distraction): Task<Void> {
       return databaseRef.child(distraction.distractionId!!).setValue(distraction.distractionId)
    }

    override fun removeDistractionFromBookmark(distraction: Distraction): Task<Void> {
        return databaseRef.child(distraction.distractionId!!).removeValue()
    }

    override fun fetchBookmarks(): SnapshotStateList<String> {
        return bookmarks
    }

    override fun isBookmarked(distraction: Distraction): Boolean {
        return fetchBookmarks().contains(distraction.distractionId)
    }
}