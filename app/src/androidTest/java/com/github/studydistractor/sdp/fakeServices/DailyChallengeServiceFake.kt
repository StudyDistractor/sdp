package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.dailyChallenge.DailyChallengeModel
import com.github.studydistractor.sdp.data.Distraction

class DailyChallengeServiceFake : DailyChallengeModel {
    override fun fetchDailyChallenge(dateString: String): List<Distraction> {
        return listOf(
            Distraction(name = "Name1", description = "Description1"),
            Distraction(name = "Name2", description = "Description2"),
            Distraction(name = "Name3", description = "Description3"),
        )
    }

    override fun postDailyChallenge(
        dailyChallenge: List<Distraction>,
        date: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        onSuccess()
    }
}