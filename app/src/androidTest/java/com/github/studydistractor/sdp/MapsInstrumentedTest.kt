package com.github.studydistractor.sdp
//
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
//import androidx.test.espresso.matcher.ViewMatchers.withId
//import androidx.test.ext.junit.rules.ActivityScenarioRule
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import org.junit.Assert.*
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.util.concurrent.CountDownLatch
//import java.util.concurrent.TimeUnit
//
///**
// * Instrumented test, which will execute on an Android device.
// *
// * See [testing documentation](http://d.android.com/tools/testing).
// */
//@RunWith(AndroidJUnit4::class)
//class MapsInstrumentedTest {
//
//
//
//    @get:Rule
//    var activityRule: ActivityScenarioRule<MapsActivity> = ActivityScenarioRule(MapsActivity::class.java)
//
////    Test that the map is displayed when the activity is started:
//
//    //TODO: The following tests were removed because of their bad design, they will be coded again in #40
////    @Test
////    fun testMapIsDisplayed() {
////        onView(withId(R.id.map)).check(matches(isDisplayed()))
////    }
////
////
////    @Test
////    fun displayPlacesCorrectlyLaunchesIntent() {
////        val activity = activityRule.scenario
////        activity.onActivity { activity ->
////            val mapFragment = activity.supportFragmentManager
////                .findFragmentById(R.id.map) as SupportMapFragment?
////            val signal = CountDownLatch(1)
////            mapFragment?.getMapAsync(object : OnMapReadyCallback {
////                override fun onMapReady(googleMap: GoogleMap) {
////                    activity.map = googleMap
////                    activity.displayPlaces()
////                    signal.countDown()
////                }
////            })
////            signal.await(10, TimeUnit.SECONDS)
////            val intent = activity.intent
////            assertEquals("com.github.studydistractor.sdp.MapsActivity", intent.component?.className)
////        }
////    }
//
//
//
//
//}