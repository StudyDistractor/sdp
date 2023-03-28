package com.github.studydistractor.sdp

import android.app.NotificationManager
import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
class RandomProcrastinationActivityActivityTest {

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<RandomProcrastinationActivityActivity>()

    @Before
    fun setup() {
        rule.inject()
    }

    @Test
    fun correctActivityNotificationIsDisplayed() {

        val context: Context = composeRule.activity.applicationContext
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        assertEquals(true, manager.areNotificationsEnabled())
        composeRule.onNodeWithTag("permission").performClick()
        composeRule.onNodeWithTag("notification").performClick()

        composeRule.waitUntil { manager.activeNotifications.isNotEmpty() }

        val notification = manager.activeNotifications.first()
        assertEquals(1, notification.id)
        assertEquals(RandomActivityNotificationService.RANDOM_ACTIVITY_CHANNEL_ID, notification.notification.channelId)

        manager.activeNotifications.first().notification.contentIntent.send()

        composeRule.onNodeWithText("Test").assertExists()
        composeRule.onNodeWithText("test description").assertExists()
    }
}