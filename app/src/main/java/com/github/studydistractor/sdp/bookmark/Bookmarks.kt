package com.github.studydistractor.sdp.bookmark

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.distraction.Distraction

/**
 * Interface to handle bookmarks (To add and remove them)
 *
 */
interface Bookmarks {

    /**
     * Add distraction to bookmark
     *
     * @param distraction distraction to be added to this user's bookmark
     */
    fun addDistractionToBookmark(
        distraction: Distraction,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
        )

    /**
     * Remove distraction from bookmark
     *
     * @param distraction distraction to be removed from user's bookmark
     */
    fun removeDistractionFromBookmark(
        distraction: Distraction,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    )

    /**
     * Fetch all bookmarks
     */
    fun fetchBookmarks(): SnapshotStateList<String>
}