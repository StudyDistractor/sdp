package com.github.studydistractor.sdp.serviceTests

import android.util.Log
import com.github.studydistractor.sdp.distractionList.DistractionListServiceFirebase
import org.junit.Test

class DistractionLIstServiceFirebaseTest {

    private val service = DistractionListServiceFirebase("TestProcrastinationActivities", "TestTags")

    @Test
    fun testGetAllDistractions() {
        val distractions = service.getAllDistractions()
        Thread.sleep(500)
        assert(distractions.size == 1)
    }

    @Test
    fun testGetTags() {
        val tags = service.getTags()
        Thread.sleep(500)
        assert(tags.size == 1)
    }
}