package com.github.studydistractor.sdp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MapsInstrumentedTest {


    @Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

//    Test that the map is displayed when the activity is started:
    @Test
    fun testMapIsDisplayed() {
        onView(withId(R.id.map)).check(matches(isDisplayed()))
    }

//
//    @get:Rule
//    val composeTestRule = createAndroidTestRule(MainActivity::class.java)
//
//    @Test
//    fun testButtonClick() {
//        val button = composeTestRule.onNode(hasTestTag("yourTestTag"), useUnmergedTree = true)
//        button.assertIsDisplayed()
//        button.performClick()
//    }
//    @Test
//    fun whenButtonIsClickedIntentIsSent() {
//        Intents.init()
//
//        Espresso.onView(withId(R.id.mainName)).perform(ViewActions.typeText("John"))
//        Espresso.onView(withId(R.id.mainGoButton)).perform(ViewActions.click())
//        intended(hasComponent(GreetingActivity::class.java.getName()))
//        Intents.release()
//    }
}