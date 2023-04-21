package com.github.studydistractor.sdp.distractionList

import com.github.studydistractor.sdp.data.Distraction
import com.google.android.gms.tasks.Task

interface DistractionListModel {
    /**
     * Get all available distractions
     */
    fun getAllDistractions() : Task<List<Distraction>>

    /**
     * Get distractions that match the given filters
     */
    fun getFilteredDistractions(
        length: Distraction.Length?,
        tags: Set<String>
    ) : Task<List<Distraction>>

    fun getAvailableTags() : List<String>
}