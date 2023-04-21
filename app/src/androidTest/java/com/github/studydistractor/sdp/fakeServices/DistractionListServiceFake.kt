package com.github.studydistractor.sdp.fakeServices

import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.distractionList.DistractionListModel
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

class DistractionListServiceFake : DistractionListModel {
    private val availableTags = setOf("food", "sports", "fun")
    val distractions = setOf(
        Distraction("mediumfoodsports", "mediumfoodsports description", tags = listOf("sports", "food")),
        Distraction("mediumfood", "mediumfood description", tags = listOf("food")),
        Distraction("shortsports", "shortsports description", tags = listOf("sports"))
    )

    override fun getAllDistractions(): Task<List<Distraction>> {
        return Tasks.forResult(distractions.toList())
    }

    override fun getFilteredDistractions(
        length: Distraction.Length?,
        tags: Set<String>
    ): Task<List<Distraction>> {
        return Tasks.forResult(distractions.filter {
            (length == null || length == it.length) &&
                    (tags.isEmpty() || it.tags == null || it.tags!!.containsAll(tags))
        })
    }

    override fun getAvailableTags(): List<String> {
        return availableTags.toList()
    }
}