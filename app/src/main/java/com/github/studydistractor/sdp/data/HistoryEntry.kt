package com.github.studydistractor.sdp.data

/**
 * This data class is the base class for history entry, each entry correspond to one
 * ProcrastinationActivity did by the user.
 */
data class HistoryEntry (
    /**
     * The name of the procrastination activity in the history
     */
    var name : String = "",
    /**
     * The description of the procrastination activity in the history
     */
    var description : String = "",
    /**
     * The date when the procrastination activity has been added to the history
     */
    var date : Long = 0L
    )