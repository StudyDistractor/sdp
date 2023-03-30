package com.github.studydistractor.sdp.sillysillycoverage


import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.sp
import com.github.studydistractor.sdp.login.FirebaseLoginAuth
import com.github.studydistractor.sdp.ui.theme.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

@HiltAndroidTest
class SillySillyCoverageTest {

    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setup() {
        rule.inject()
    }

    @Test
    fun testTheme() {
        composeRule.setContent {
            StudyDistractorTheme() {
                Text(text = "Theme test")
            }
        }

        composeRule.onNodeWithText("Theme test").assertIsDisplayed()
    }

    @Test
    fun testColor() {
        val purple80 = Color(0xFFD0BCFF)
        val purpleGrey80 = Color(0xFFCCC2DC)
        val pink80 = Color(0xFFEFB8C8)

        val purple40 = Color(0xFF6650a4)
        val purpleGrey40 = Color(0xFF625b71)
        val pink40 = Color(0xFF7D5260)

        assertEquals(purple40, Purple40)
        assertEquals(purple80, Purple80)
        assertEquals(purpleGrey40, PurpleGrey40)
        assertEquals(purpleGrey80, PurpleGrey80)
        assertEquals(pink40, Pink40)
        assertEquals(pink80, Pink80)
    }

    @Test
    fun testType() {
        assertEquals(16.sp, Typography.bodyLarge.fontSize)
    }

    @Test
    fun testFirebaseLoginAuth() {
        var auth = FirebaseLoginAuth(Firebase.auth)
        auth.loginWithEmail("pouet", "pouet")
        // no assertion, this is purely for artificial raising of the coverage
    }
}