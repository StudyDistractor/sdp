package com.github.studydistractor.sdp

import androidx.lifecycle.ViewModel

/**
 * This class help processing data for the procrastination activity
 */
class ProcrastinationActivityActivityViewModel : ViewModel(){

    /**
     * Check the activity and returns a non-null field activity
     * @param activity an activity that may be null or with null content
     * @return A procrastination activity
     */
    fun processActivity(activity : ProcrastinationActivity?): ProcrastinationActivity {

        if(activity == null) {
            throw NullPointerException()
        }

        if(activity.description == null) {
            throw NullPointerException("Activity's description is null")
        }

        if(activity.name == null) {
            throw NullPointerException("Activity's name is null")
        }

        return activity
    }
}