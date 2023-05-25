package com.github.studydistractor.sdp.dailyChallenge

import com.github.studydistractor.sdp.data.Distraction

interface DailyChallengeModel {

    /* Fetch the daily challenge from the database */
    fun fetchDailyChallenge(dateString : String) : List<Distraction>

}