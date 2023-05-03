package com.github.studydistractor.sdp.distractionList

import com.github.studydistractor.sdp.distraction.Distraction

class FakeDistractionListModel : DistractionListModel{

    private val distractions = listOf(
        Distraction("testDistraction", "testDescription", distractionId = "test"),
        Distraction("shortDistraction", "test", length = Distraction.Length.SHORT, distractionId = "short"),
        Distraction("mediumDistraction", "test", length = Distraction.Length.MEDIUM, distractionId = "medium"),
        Distraction("longDistraction", "test", length = Distraction.Length.LONG, distractionId = "long"),
        Distraction("foodDistraction", "test", tags = listOf("Food"), distractionId = "food"),
        Distraction("mixDistraction", "test", length = Distraction.Length.MEDIUM, tags = listOf("testTag"), distractionId = "mix")
    )

    override fun getAllDistractions(): List<Distraction> {
        return distractions
    }

    override fun getTags(): List<String> {
        return listOf("Food", "Drink", "Sport")
    }
}