package com.github.studydistractor.sdp.middleware

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.studydistractor.sdp.eventHistory.EventHistoryMiddlewareOffline
import com.github.studydistractor.sdp.fakeServices.EventHistoryServiceFake
import com.github.studydistractor.sdp.roomdb.RoomDatabase
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventHistoryMiddlewareOfflineTest {
    private lateinit var middle : EventHistoryMiddlewareOffline
    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        val connectivityManager =
            ApplicationProvider.getApplicationContext<Context>()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        middle = EventHistoryMiddlewareOffline(
            EventHistoryServiceFake(),
            Room.databaseBuilder(
                ApplicationProvider.getApplicationContext(),
                RoomDatabase::class.java,
                "study-distractor-db-test"
            ).allowMainThreadQueries().build(),
            connectivityManager
        )
    }
    @Test
    fun testOffline(){
        middle.deleteCache()
        middle.claimPoints("test","test")
        middle.observeHistory("test", {
            assertEquals(5, it.size)
        })
    }
}