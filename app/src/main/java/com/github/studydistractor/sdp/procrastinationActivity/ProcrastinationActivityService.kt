package com.github.studydistractor.sdp.procrastinationActivity

import androidx.compose.runtime.snapshots.SnapshotStateList

interface ProcrastinationActivityService {
    fun fetchProcrastinationActivities(callback: (List<ProcrastinationActivity>) -> Unit)

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