package com.github.studydistractor.sdp.bookmark

import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * Interface to handle bookmarks (To add and remove them)
 *
 */
interface Bookmarks {

    /**
     * Get the current uid of the user with firebase auth.
     */
    fun getCurrentUid() : String?

    /**
     * Add distraction to bookmark
     *
     * @param uid User with uid
     * @param distractionId distraction to be added to this user's bookmark
     */
    fun addDistractionToBookmark(
        uid: String,
        distractionId: String,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
        )

    /**
     * Remove distraction from bookmark
     *
     * @param uid User with uid
     * @param distractionId distraction to be removed from user's bookmark
     */
    fun removeDistractionFromBookmark(
        uid: String,
        distractionId: String,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    )

    /**
     * Fetch all bookmarks
     *
     * @param uid User with uid
     */
    fun fetchBookmarks(uid: String) : SnapshotStateList<String>
}