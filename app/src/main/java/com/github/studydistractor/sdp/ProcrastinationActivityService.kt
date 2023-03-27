package com.github.studydistractor.sdp

import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * This interface is the abstraction to talk to the database for procrastination activities related
 * request.
 */
interface ProcrastinationActivityService {

    /**
     * Fetch all the procrastination activities available in the database
     * @return a snapshotStateList that contains the procrastination activities
     */
    fun fetchProcrastinationActivities() : SnapshotStateList<ProcrastinationActivity>

    /**
     * Post the activity to the database
     * @param activity the activity to be posted to the database
     */
    fun postProcastinationActivities(activity : ProcrastinationActivity)
}