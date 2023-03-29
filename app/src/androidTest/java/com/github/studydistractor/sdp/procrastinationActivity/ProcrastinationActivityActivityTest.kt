package com.github.studydistractor.sdp.procrastinationActivity

import android.content.Intent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.github.studydistractor.sdp.launch
import com.github.studydistractor.sdp.register.RegisterActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@HiltAndroidTest
class ProcrastinationActivityActivityTest {
    @get:Rule(order = 0)
    var rule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createEmptyComposeRule()
    @Before
    fun setup() {
        rule.inject()

    }

    @Test
    fun testLaunchProcrastinationActivityActivity(){
        composeTestRule.launch<ProcrastinationActivityActivity>(
            intentFactory = {
                val intent2 = Intent(
                    it, ProcrastinationActivityActivity::class.java
                )
                intent2.putExtra("activity", ProcrastinationActivity("salut", "bonjour"))
                intent2
            },
            onAfterLaunched = {
                val button = composeTestRule.onNodeWithTag("done")
                button.performClick()
            })

    }
}