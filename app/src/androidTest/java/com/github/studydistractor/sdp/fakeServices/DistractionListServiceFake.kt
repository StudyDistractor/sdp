package com.github.studydistractor.sdp.fakeServices

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.distractionList.DistractionListModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class DistractionListServiceFake : DistractionListModel {
    private val availableTags = setOf("food", "sports", "fun")
    val distractions = mutableStateListOf(
        Distraction("mediumfoodsports", "mediumfoodsports description", tags = listOf("sports", "food")),
        Distraction("mediumfood", "mediumfood description", tags = listOf("food")),
        Distraction("shortsports", "shortsports description", tags = listOf("sports"))
    )

    override fun getAllDistractions(): SnapshotStateList<Distraction> {
        return distractions
    }

    override fun getTags(): List<String> {
        return availableTags.toList()
    }
}