package com.github.studydistractor.sdp.procrastinationActivity

interface ProcrastinationActivityService {
    fun fetchProcrastinationActivities(callback: (List<ProcrastinationActivity>) -> Unit)
}