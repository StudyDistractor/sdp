package com.github.studydistractor.sdp.procrastinationActivity

import androidx.lifecycle.ViewModel

class ProcrastinationActivityActivityViewModel : ViewModel(){

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