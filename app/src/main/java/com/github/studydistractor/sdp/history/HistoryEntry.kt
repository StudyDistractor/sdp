package com.github.studydistractor.sdp.history

import java.util.*

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