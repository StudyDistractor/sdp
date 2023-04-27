package com.github.studydistractor.sdp.bookmark

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.distraction.Distraction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class FirebaseBookmarks @Inject constructor() : Bookmarks {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    private var bookmarks = SnapshotStateList<String>()
    private val BOOKMARKSPATH = "Bookmarks"

    private var bookmarksListener =  object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val newBookmark =  mutableStateListOf<String>()
            for (id in snapshot.children) {
                if (id.exists()) {
                    newBookmark.add(id.getValue(String::class.java)!!)
                }
                bookmarks = newBookmark
            }
        }
        override fun onCancelled(error: DatabaseError) {
            Log.d("Firebase", "loadPost:onCancelled " + error.toException().toString())
        }
    }

    init {
        val databaseRef = db.getReference(BOOKMARKSPATH).child(auth.uid!!)
        databaseRef.addListenerForSingleValueEvent(bookmarksListener)
    }

    override fun addDistractionToBookmark(
        distraction: Distraction,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    ) {
        db.getReference(BOOKMARKSPATH).child(auth.uid!!).push().setValue(distraction.distractionId)
            .addOnCompleteListener { t ->
                if (t.isSuccessful){
                    onSuccess()
                } else {
                    onFailed()
                }
            }
    }

    override fun removeDistractionFromBookmark(
        distraction: Distraction,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    ) {
        db.getReference(BOOKMARKSPATH).child(auth.uid!!).child(distraction.distractionId!!).removeValue()
            .addOnCompleteListener { t ->
                if (t.isSuccessful){
                    onSuccess()
                } else {
                    onFailed()
                }
            }
    }

    override fun fetchBookmarks(): SnapshotStateList<String> {
        return bookmarks
    }
}