package com.github.studydistractor.sdp.procrastinationActivity

interface ProcrastinationActivityService {

    /**
     * Fetch the activities from the database
     * @param callback the callback to be called when the activities are fetched
     */
    fun fetchProcrastinationActivities(callback: (List<ProcrastinationActivity>) -> Unit)


    /**
     * Post the activity to the database
     * @param activity the activity to be posted to the database
     */
    fun postProcastinationActivities(activity : ProcrastinationActivity, onSuccess: () -> Unit, onFailure: () -> Unit)
}