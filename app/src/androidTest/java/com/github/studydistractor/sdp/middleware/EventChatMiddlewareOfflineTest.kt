package com.github.studydistractor.sdp.middleware

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.studydistractor.sdp.eventChat.EventChatMiddlewareOffline
import com.github.studydistractor.sdp.fakeServices.EventChatOfflineServiceFake
import com.github.studydistractor.sdp.roomdb.RoomDatabase
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventChatMiddlewareOfflineTest {

    private lateinit var middle : EventChatMiddlewareOffline
    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        val connectivityManager =
            ApplicationProvider.getApplicationContext<Context>()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        middle = EventChatMiddlewareOffline(
            Room.databaseBuilder(
                ApplicationProvider.getApplicationContext(),
                RoomDatabase::class.java,
                "study-distractor-db-test"
            ).allowMainThreadQueries().build(),
            EventChatOfflineServiceFake(),
            connectivityManager
        )
    }

    @Test
    fun testOffline(){
        middle.deleteCache()
        middle.changeCurrentChat("test")
        middle.postMessage("salut").continueWith{}
    }
}