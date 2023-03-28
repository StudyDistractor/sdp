package com.github.studydistractor.sdp.procrastinationActivity

import androidx.compose.runtime.snapshots.SnapshotStateList

interface ProcrastinationActivityService {
    fun fetchProcrastinationActivities() : SnapshotStateList<ProcrastinationActivity>
}