package com.github.studydistractor.sdp.bookmarks

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.bookmark.BookmarkModel
import com.github.studydistractor.sdp.data.Distraction
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class BookmarksServiceFake: BookmarkModel {

    val bookmarks = mutableStateListOf<String>("test")

    override fun addDistractionToBookmark(distraction: Distraction): Task<Void> {
        bookmarks.add(distraction.distractionId!!)
        return Tasks.forResult(null)
    }

    override fun removeDistractionFromBookmark(distraction: Distraction): Task<Void> {
        bookmarks.remove(distraction.distractionId!!)
        return Tasks.forResult(null)
    }

    override fun fetchBookmarks(): SnapshotStateList<String> {
        return bookmarks
    }

    override fun isBookmarked(distraction: Distraction): Boolean {
        val distractionId = distraction.distractionId!!
        return bookmarks.contains(distractionId)
    }
}