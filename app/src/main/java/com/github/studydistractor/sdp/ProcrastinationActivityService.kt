package com.github.studydistractor.sdp

import androidx.compose.runtime.snapshots.SnapshotStateList

interface ProcrastinationActivityService {
    fun fetchProcrastinationActivities() : SnapshotStateList<ProcrastinationActivity>

    fun postProcastinationActivities(activity : ProcrastinationActivity)
}