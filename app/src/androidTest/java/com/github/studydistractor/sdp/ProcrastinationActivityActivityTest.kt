package com.github.studydistractor.sdp

import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.github.studydistractor.sdp.procrastinationActivity.ProcrastinationActivity
import com.github.studydistractor.sdp.procrastinationActivity.ProcrastinationActivityActivity

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

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ProcrastinationActivityActivityTest {

    @get:Rule
    val composeRule = createEmptyComposeRule()

    @Test
    fun displaySimpleActivityTest() {
        val name = "name"
        val description = "description"
        val lat = 0.0
        val long = 0.0

        composeRule.launch<ProcrastinationActivityActivity>(
            {},
            intentFactory = {
                val intent = Intent(it, ProcrastinationActivityActivity::class.java)
                intent.putExtra("activity", ProcrastinationActivity(name, lat, long, description))
                intent
            },
            onAfterLaunched = {
                onNodeWithText(name).assertIsDisplayed()
                onNodeWithText(description).assertIsDisplayed()
            })
    }

}


