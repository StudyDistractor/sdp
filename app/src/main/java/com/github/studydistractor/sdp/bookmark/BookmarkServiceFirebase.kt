package com.github.studydistractor.sdp.bookmark

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.Distraction
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class BookmarkServiceFirebase @Inject constructor(bookmarkPath: String): BookmarkModel {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    private var bookmarks = SnapshotStateList<String>()
    private val BOOKMARKSPATH = bookmarkPath
    private var databaseRef: DatabaseReference = db.getReference(BOOKMARKSPATH).child("")
    private var hasListener = false

    private var bookmarksListener =  object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
           bookmarks.clear()
            for (id in snapshot.children) {
                if (id.exists()) {
                    bookmarks.add(id.getValue(String::class.java)!!)
                    Log.d("bookmark", id.getValue(String::class.java)!! )
                }
            }
        }
        override fun onCancelled(error: DatabaseError) {
            Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
        }
    }

    init {
        isLoggedIn()
    }

    override fun addDistractionToBookmark(distraction: Distraction): Task<Void> {
        if(!isLoggedIn()) {
            return Tasks.forException(Exception("User not logged in"))
        }
        Log.d("boomarked", "added")
        return databaseRef.child(distraction.distractionId!!).setValue(distraction.distractionId)
    }

    override fun removeDistractionFromBookmark(distraction: Distraction): Task<Void> {
        if(!isLoggedIn()) {
            return Tasks.forException(Exception("User not logged in"))
        }
        Log.d("boomarked", "removed")
        return databaseRef.child(distraction.distractionId!!).removeValue()
    }

    override fun fetchBookmarks(): SnapshotStateList<String> {
        return bookmarks
    }

    override fun isBookmarked(distraction: Distraction): Boolean {
        return fetchBookmarks().contains(distraction.distractionId)
    }

    private fun isLoggedIn(): Boolean {
        return if(auth.uid != null && !hasListener) {
            databaseRef = db.getReference(BOOKMARKSPATH).child(auth.uid!!)
            databaseRef.addValueEventListener(bookmarksListener)
            hasListener = true
            true
        } else auth.uid != null
    }
}
