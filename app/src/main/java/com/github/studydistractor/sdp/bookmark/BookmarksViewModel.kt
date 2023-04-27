package com.github.studydistractor.sdp.bookmark

import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.distraction.Distraction

class BookmarksViewModel(
    private val bookmarksService: Bookmarks = FirebaseBookmarks()
): ViewModel() {

    fun addToBookmark(distraction: Distraction,
                      onSuccess: () -> Unit,
                      onFailed: () -> Unit) {
        bookmarksService.addDistractionToBookmark(distraction, onSuccess, onFailed)
    }

    fun removeFromBookmark(distraction: Distraction,
                      onSuccess: () -> Unit,
                      onFailed: () -> Unit) {
        bookmarksService.removeDistractionFromBookmark(distraction, onSuccess, onFailed)
    }
}