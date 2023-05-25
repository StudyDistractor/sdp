package com.github.studydistractor.sdp.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertEquals
import org.junit.Test

class OnlineStatusTest {
    val connectivityManager =
        ApplicationProvider.getApplicationContext<Context>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    @Test
    fun testOnline(){
        assertEquals(true, OnlineStatus().isOnline(connectivityManager))
    }
}