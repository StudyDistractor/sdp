package com.github.studydistractor.sdp.bookmarks

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.bookmark.BookmarkModel
import com.github.studydistractor.sdp.distraction.Distraction
import com.google.android.gms.tasks.Task

class fakeBookmarksService: BookmarkModel {

    override fun addDistractionToBookmark(distraction: Distraction): Task<Void> {
        TODO("Not yet implemented")
    }

    override fun removeDistractionFromBookmark(distraction: Distraction): Task<Void> {
        TODO("Not yet implemented")
    }

    override fun fetchBookmarks(): SnapshotStateList<String> {
        TODO("Not yet implemented")
    }

    override fun isBookmarked(distraction: Distraction): Boolean {
        TODO("Not yet implemented")
    }
}