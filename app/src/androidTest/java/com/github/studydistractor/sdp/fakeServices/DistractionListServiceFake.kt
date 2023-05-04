package com.github.studydistractor.sdp.fakeServices

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.distractionList.DistractionListModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class DistractionListServiceFake : DistractionListModel {
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