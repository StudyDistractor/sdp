package com.github.studydistractor.sdp

import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
//import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @get:Rule
    val rule = ActivityScenarioRule(
        MainActivity::class.java
    )

//    @Test fun testImageFragmentContainsImage() {
//        launchFragmentInContainer<ImageFragment>()
//        onView(withClassName(
//            containsString(ImageView::class.simpleName)
//        )).check(matches(isDisplayed()))
//    }

    @Test fun testTextFragmentAppearsAtLaunch() {
        onView(
            withId(R.id.textFragmentTitle)
        ).check(matches(isDisplayed()))
    }
}