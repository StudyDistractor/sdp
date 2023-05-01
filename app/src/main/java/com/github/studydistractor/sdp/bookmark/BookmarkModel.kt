package com.github.studydistractor.sdp.bookmark

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.distraction.Distraction
import com.google.android.gms.tasks.Task

/**
 * Interface to handle bookmarks (To add and remove them)
 *
 */
interface BookmarkModel {

    /**
     * Add distraction to bookmark
     *
     * @param distraction distraction to be added to this user's bookmark
     */
    fun addDistractionToBookmark(distraction: Distraction): Task<Void>

    /**
     * Remove distraction from bookmark
     *
     * @param distraction distraction to be removed from user's bookmark
     */
    fun removeDistractionFromBookmark(distraction: Distraction): Task<Void>

    /**
     * Fetch all bookmarks from the current user
     */
    fun fetchBookmarks(): SnapshotStateList<String>

    /**
     * Check if the distraction is bookmarked by the current user
     */
    fun isBookmarked(distraction: Distraction): Boolean
}