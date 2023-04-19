package com.github.studydistractor.sdp.distraction

import org.junit.Assert.assertEquals
import org.junit.Test

class DistractionTest {
    @Test
    fun createOneDistraction(){
        val name = "salut"
        val description = "bonjour"
        val act = Distraction(name, description,0.0, 0.0)
        assertEquals(act.name, name)
        assertEquals(act.description, description)
    }
    @Test
    fun createOneDistractionWithoutName(){
        val name = "salut"
        val description = "bonjour"
        val act = Distraction(description = description)
        assertEquals(act.description, description)
    }
    @Test
    fun createOneDistractionWithoutDescription(){
        val name = "salut"
        val description = "bonjour"
        val act = Distraction(name = name)
        assertEquals(act.name, name)
    }
    @Test
    fun createArrayOfDistraction(){
        val size = 1
        val a = Distraction.CREATOR.newArray(size)
        assertEquals(size, a.size)
    }
    @Test
    fun testDescribeContent(){
        val name = "salut"
        val description = "bonjour"
        val act = Distraction(name = name)
        assertEquals(0,act.describeContents())
    }

}