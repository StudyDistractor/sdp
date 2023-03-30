package com.github.studydistractor.sdp.procrastinationActivity

import org.junit.Assert.assertEquals
import org.junit.Test

class ProcrastinationActivityTest {
    @Test
    fun createOneProcrastinationActivity(){
        val name = "salut"
        val description = "bonjour"
        val act = ProcrastinationActivity(name, 0.0, 0.0, description)
        assertEquals(act.name, name)
        assertEquals(act.description, description)
    }
    @Test
    fun createOneProcrastinationActivityWithoutName(){
        val name = "salut"
        val description = "bonjour"
        val act = ProcrastinationActivity(description = description)
        assertEquals(act.description, description)
    }
    @Test
    fun createOneProcrastinationActivityWithoutDescription(){
        val name = "salut"
        val description = "bonjour"
        val act = ProcrastinationActivity(name = name)
        assertEquals(act.name, name)
    }
    @Test
    fun createArrayOfProcrastinationActivity(){
        val size = 1
        val a = ProcrastinationActivity.CREATOR.newArray(size)
        assertEquals(size, a.size)
    }
    @Test
    fun testDescribeContent(){
        val name = "salut"
        val description = "bonjour"
        val act = ProcrastinationActivity(name = name)
        assertEquals(0,act.describeContents())
    }

}