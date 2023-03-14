package com.github.studydistractor.sdp

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
class ProcrastinationActivityActivityViewModelTest {
    @Test
    fun rightIntentReturnsProcrastinationActivity() {
        val name = "name"
        val description = "description"
        val activity = ProcrastinationActivity(name, description)
        val returnedProcrastinationActivity = ProcrastinationActivityActivityViewModel().processActivity(activity)

        assertEquals(name, returnedProcrastinationActivity.name)
        assertEquals(description, returnedProcrastinationActivity.description)
    }

    @Test
    fun nullActivityThrowsException() {
        val activity = null

        assertThrows(java.lang.NullPointerException::class.java) {
            ProcrastinationActivityActivityViewModel().processActivity(activity)
        }
    }

    @Test
    fun nullNameThrowsException() {
        val activity = ProcrastinationActivity(null, "")

        assertThrows(java.lang.NullPointerException::class.java) {
            ProcrastinationActivityActivityViewModel().processActivity(activity)
        }
    }

    @Test
    fun nullDescriptionThrowsException() {
        val activity = ProcrastinationActivity("", null)

        assertThrows(java.lang.NullPointerException::class.java) {
            ProcrastinationActivityActivityViewModel().processActivity(activity)
        }
    }



}