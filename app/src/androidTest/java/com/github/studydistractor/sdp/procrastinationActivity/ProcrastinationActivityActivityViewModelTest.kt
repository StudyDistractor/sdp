package com.github.studydistractor.sdp.procrastinationActivity

import org.junit.Assert.*
import org.junit.Test

class ProcrastinationActivityActivityViewModelTest {
    val p = ProcrastinationActivityActivityViewModel()
    val name = "salut"
    val description = "bonjour"
    @Test
    fun noDescription(){
        assertThrows(NullPointerException::class.java, {
            p.processActivity(ProcrastinationActivity(name, null, null, null))
        })
    }
    @Test
    fun noName(){
        assertThrows(NullPointerException::class.java, {
            p.processActivity(ProcrastinationActivity(null, null, null, description))
        })
    }
    @Test
    fun nameAndDescription(){
        p.processActivity(ProcrastinationActivity(name, null, null, description))
    }
}