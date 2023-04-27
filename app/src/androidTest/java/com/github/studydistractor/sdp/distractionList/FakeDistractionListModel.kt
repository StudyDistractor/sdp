package com.github.studydistractor.sdp.distractionList

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.Distraction


class FakeDistractionListModel : DistractionListModel{

    private val distractions = mutableStateListOf(
        Distraction("testDistraction", "testDescription", distractionId = "test"),
        Distraction("shortDistraction", "test", length = Distraction.Length.SHORT, distractionId = "short"),
        Distraction("mediumDistraction", "test", length = Distraction.Length.MEDIUM, distractionId = "medium"),
        Distraction("longDistraction", "test", length = Distraction.Length.LONG, distractionId = "long"),
        Distraction("foodDistraction", "test", tags = listOf("Food"), distractionId = "food"),
        Distraction("mixDistraction", "test", length = Distraction.Length.MEDIUM, tags = listOf("testTag"), distractionId = "mix")
    )

    override fun getAllDistractions(): SnapshotStateList<Distraction> {
        return distractions
    }

    override fun getTags(): List<String> {
        return listOf("Food", "Drink", "Sport")
    }
}