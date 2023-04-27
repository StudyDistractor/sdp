package com.github.studydistractor.sdp.createDistraction

import com.github.studydistractor.sdp.data.Distraction
import com.google.android.gms.tasks.Task

interface CreateDistractionModel {
    /**
     * Create a distraction in the db
     *
     * @distraction(Distraction): the distraction to add
     */
    fun createDistraction(distraction: Distraction): Task<Void>
}