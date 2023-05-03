package com.github.studydistractor.sdp.distractionList

import com.github.studydistractor.sdp.distraction.Distraction

interface DistractionListModel {
    /**
     * Get all available distractions
     */
    fun getAllDistractions() : List<Distraction>

    fun getTags() : List<String>
}