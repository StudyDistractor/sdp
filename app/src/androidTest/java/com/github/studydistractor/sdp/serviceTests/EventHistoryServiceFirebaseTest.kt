package com.github.studydistractor.sdp.serviceTests

import com.github.studydistractor.sdp.eventHistory.EventHistoryServiceFirebase
import org.junit.Test

class EventHistoryServiceFirebaseTest {
    private val service = EventHistoryServiceFirebase("TestEvents", "TestEventParticipants", "TestUsers", "TestEventClaimPoints")

    @Test
    fun testObserveHistory() {
        val task = service.observeHistory("uid1") { history ->
            assert(history.isNotEmpty())
        }

        val task2 = service.observeHistory("uid2") { history ->
            assert(history.isEmpty())
        }
    }

    @Test
    fun testClaimPoints() {
        val task = service.claimPoints("uid1", "-NWKIKAfohBgEMt4MxaB")
        Thread.sleep(500)
        assert(task.exception != null)
    }

}