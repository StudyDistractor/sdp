package com.github.studydistractor.sdp.distractionList

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.Distraction

interface DistractionListModel {
    /**
     * Get all available distractions
     */
    fun getAllDistractions() : SnapshotStateList<Distraction>

    /**
     * Get all available tags
     */
    fun getTags() : List<String>
}