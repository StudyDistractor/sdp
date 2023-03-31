package com.github.studydistractor.sdp.procrastinationActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.github.studydistractor.sdp.launch
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Uses a [ComposeTestRule] created via [createEmptyComposeRule] that allows setup before the activity
 * is launched via [onBefore]. Assertions on the view can be made in [onAfterLaunched].
 *
 * source : https://stackoverflow.com/questions/68267861/add-intent-extras-in-compose-ui-test
 */
inline fun <reified A: Activity> ComposeTestRule.launch(
    onBefore: () -> Unit = {},
    intentFactory: (Context) -> Intent = { Intent(ApplicationProvider.getApplicationContext(), A::class.java) },
    onAfterLaunched: ComposeTestRule.() -> Unit
) {
    onBefore()

    val context = ApplicationProvider.getApplicationContext<Context>()
    ActivityScenario.launch<A>(intentFactory(context))

    onAfterLaunched()
}

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
                intent2.putExtra("activity", ProcrastinationActivity("salut", "salut",0.0, 0.0))
                intent2
            },
            onAfterLaunched = {
                val button = composeTestRule.onNodeWithTag("done")
                button.performClick()
            })

    }
}