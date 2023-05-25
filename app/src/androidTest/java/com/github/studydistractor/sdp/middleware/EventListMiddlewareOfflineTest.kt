package com.github.studydistractor.sdp.middleware

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.studydistractor.sdp.eventList.EventListMiddlewareOffline
import com.github.studydistractor.sdp.fakeServices.EventListServiceFake
import com.github.studydistractor.sdp.roomdb.RoomDatabase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventListMiddlewareOfflineTest {

    private lateinit var middle : EventListMiddlewareOffline
    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        val connectivityManager =
            ApplicationProvider.getApplicationContext<Context>()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        middle = EventListMiddlewareOffline(
            EventListServiceFake(),
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
        assertEquals(3,middle.getAllEvents().size)
        middle.subscribeToEventParticipants("test", {}, {})
    }
}