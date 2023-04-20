package com.github.studydistractor.sdp.distraction

import androidx.compose.runtime.snapshots.SnapshotStateList
interface DistractionService {

    /**
     * Fetch the distractions from the database
     */
    fun fetchDistractions() : SnapshotStateList<Distraction>

    /**
     * Post the distraction to the database
     * @param activity the distraction to be posted to the database
     */
    fun postDistraction(activity : Distraction, onSuccess: () -> Unit, onFailure: () -> Unit)
}