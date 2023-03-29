package com.github.studydistractor.sdp.history

import android.util.Log
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HistoryActivityTest {

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HistoryActivity>()

    @Before
    fun setup() {
        rule.inject()
    }
    @Test
    fun testHistory(){
        val name = "test"
        val description = "test"
        val date = 1000L

        var h = HistoryEntry(name, description,date)
        assertEquals(date, h.date)
        assertEquals(name, h.name)
        assertEquals(description, h.description)
        h = HistoryEntry()
        assertEquals(0, h.date)
        assertEquals("", h.name)
        assertEquals("", h.description)

        h.date = date
        h.name = name
        h.description = description
        assertEquals(date, h.date)
        assertEquals(name, h.name)
        assertEquals(description, h.description)
    }
    @Test
    fun testHistoryFetchWithoutUid(){
        val hi = FirebaseHistory()
        val res = hi.getHistory("")


        assertEquals(res.size, 0)
    }
    @Test
    fun testHistoryAddWithoutUid(){
        val name = "test"
        val description = "test"

        var h = HistoryEntry(name, description,0L)
        val hi = FirebaseHistory()
        val res = hi.addHistoryEntry(h, "")
        assertEquals(false, res)
    }
    @Test
    fun testHistoryAddWithUid(){
        val name = "test"
        val description = "test"

        var h = HistoryEntry(name, description,0L)
        val hi = FirebaseHistory()
        val res = hi.addHistoryEntry(h, "placeholder")
        assertEquals(true, res)
    }
    @Test
    fun testHistoryCurrentUidNull(){
        val hi = FirebaseHistory()
        assertNotNull(hi.getCurrentUid())
    }
    @Test
    fun testHistoryClickable(){
        composeTestRule.onNodeWithTag("entry0").performClick()
    }
}