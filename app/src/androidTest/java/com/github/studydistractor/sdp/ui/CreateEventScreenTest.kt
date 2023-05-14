package com.github.studydistractor.sdp.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.github.studydistractor.sdp.createDistraction.CreateDistractionViewModel
import com.github.studydistractor.sdp.createEvent.CreateEventModel
import com.github.studydistractor.sdp.createEvent.CreateEventViewModel
import com.github.studydistractor.sdp.fakeServices.CreateDistractionServiceFake
import com.github.studydistractor.sdp.fakeServices.CreateEventServiceFake
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CreateEventScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    private val createEventServiceFake = CreateEventServiceFake()
    private val viewModel = CreateEventViewModel(createEventServiceFake)
    private var successCount = 0

    @Before
    fun setup() {
        successCount = 0
        composeRule.setContent {
            CreateEventScreen(
                createEventViewModel = viewModel,
                onEventCreated = { successCount++ })
        }
    }

    @Test
    fun mainContainerExists() {
        composeRule.onNodeWithTag("create-activity-screen__main-container").assertExists()
    }

    @Test
    fun titleExistsAndIsDisplayedCorrectly() {
        composeRule.onNodeWithTag("create-event-screen__title").assertExists()
        composeRule.onNodeWithTag("create-event-screen__title").assertTextEquals("Create event")
    }

    @Test
    fun nameFieldExists() {
        composeRule.onNodeWithTag("name").assertExists()
        composeRule.onNodeWithTag("name").assert(hasText("name"))
    }

    @Test
    fun nameSupportingTextExists() {
        composeRule.onNodeWithTag("nameSupport", true).assertExists()
    }

    @Test
    fun descriptionFieldExists() {
        composeRule.onNodeWithTag("description").assertExists()
        composeRule.onNodeWithTag("description").assert(hasText("description"))
    }

    @Test
    fun descriptionSupportingTextExists() {
        composeRule.onNodeWithTag("descriptionSupport", true).assertExists()
    }

    @Test
    fun startDateTimeFieldExists() {
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field").assertExists()
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field").assert(hasText("Start date and time (dd-mm-yyyy hh:mm)"))
    }

    @Test
    fun startDateTimeButtonExists() {
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Button").assertExists()
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Button").assertHasClickAction()
    }

    @Test
    fun endDateTimeFieldExists() {
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field").assertExists()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field").assert(hasText("End date and time (dd-mm-yyyy hh:mm)"))
    }

    @Test
    fun endDateTimeButtonExists() {
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Button").assertExists()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Button").assertHasClickAction()
    }

    @Test
    fun latitudeFieldExists() {
        composeRule.onNodeWithTag("latitude").assertExists()
        composeRule.onNodeWithTag("latitude").assert(hasText("latitude"))
    }

    @Test
    fun longitudeFieldExists() {
        composeRule.onNodeWithTag("longitude").assertExists()
        composeRule.onNodeWithTag("longitude").assert(hasText("longitude"))
    }

    @Test
    fun pointsAwardedFieldExistsAndDisplaysCorrectly() {
        composeRule.onNodeWithTag("pointsAwardedRow").assertExists()
        composeRule.onNodeWithTag("pointsAwardedText").assertExists()
        composeRule.onNodeWithTag("pointsAwardedText").assert(hasText("This event awards"))
        composeRule.onNodeWithTag("pointsAwardedField").assertExists()
        composeRule.onNodeWithTag("pointsAwardedValueText").assertExists()
        composeRule.onNodeWithTag("pointsAwardedValueText").assert(hasText(" points."))
    }

    @Test
    fun lateParticipationExistsAndDisplaysCorrectly() {
        composeRule.onNodeWithTag("checkBoxRow").assertExists()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").assertExists()
        composeRule.onNodeWithTag("lateParticipationAllowedText").assert(hasText("Allow late participation to this event"))
        composeRule.onNodeWithTag("lateParticipationAllowedText").assertExists()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").assertHasClickAction()
    }

    @Test
    fun createEventButtonExists() {
        composeRule.onNodeWithTag("addActivity").assertExists()
        composeRule.onNodeWithTag("addActivity").assert(hasText("Create event"))
        composeRule.onNodeWithTag("addActivity").assertHasClickAction()
    }

    @Test
    fun nameFieldWorks(){
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("name").assert(hasText("name"))
    }

    @Test
    fun nameFieldDoesNotAcceptMoreThan20Characters(){
        composeRule.onNodeWithTag("name").performTextInput("123456789012345678901")
        composeRule.onNodeWithTag("name").assert(!hasText("123456789012345678901"))
    }

    @Test
    fun descriptionFieldDoesNotAcceptMoreThan200Characters(){
        composeRule.onNodeWithTag("description").performTextInput("12345678901234567890".repeat(11))
        composeRule.onNodeWithTag("description").assert(!hasText("12345678901234567890".repeat(11)))
    }
    @Test
    fun descriptionFieldWorks(){
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("description").assert(hasText("description"))
    }

    @Test
    fun startDateTimeFieldWorks(){
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field").assert(hasText("01-01-2021 12:00"))
    }

    @Test
    fun endDateTimeFieldWorks(){
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field").performScrollTo()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field").assert(hasText("01-01-2021 13:00"))
    }

    @Test
    fun latitudeFieldWorks(){
        composeRule.onNodeWithTag("latitude").performScrollTo()
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("latitude").assert(hasText("1.0"))
    }

    @Test
    fun longitudeFieldWorks(){
        composeRule.onNodeWithTag("longitude").performScrollTo()
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").assert(hasText("1.0"))
    }

    @Test
    fun pointsAwardedFieldWorks(){
        composeRule.onNodeWithTag("pointsAwardedField").performScrollTo()
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("pointsAwardedField").assert(hasText("1"))
    }

    @Test
    fun lateParticipationAllowedCheckboxWorks(){
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performScrollTo()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").assertIsOff()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").assertIsOn()
    }

    @Test
    fun eventWithAllInfoFilledInCanBeCreated() {
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field").performScrollTo()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("latitude").performScrollTo()
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performScrollTo()
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performScrollTo()
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performScrollTo()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performScrollTo()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(1, successCount)
    }

    @Test
    fun eventWithNoNameCannotBeCreated() {
        successCount = 0
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithNoDescriptionCannotBeCreated() {
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithNoStartDateTimeCannotBeCreated() {
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithNoEndDateTimeCannotBeCreated() {
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithNoLatitudeCannotBeCreated() {
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithNoLongitudeCannotBeCreated() {
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field").performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithNoPointsAwardedCannotBeCreated() {
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("longitude").performScrollTo()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("addActivity").performScrollTo()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithInvalidStartMonthCannotBeCreated(){
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-13-2021 12:00")
        composeRule.onNodeWithTag("longitude").performScrollTo()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("addActivity").performScrollTo()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithInvalidStartHourCannotBeCreated(){
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 25:00")
        composeRule.onNodeWithTag("longitude").performScrollTo()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("addActivity").performScrollTo()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithInvalidEndMonthCannotBeCreated(){
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("longitude").performScrollTo()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-13-2021 13:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("addActivity").performScrollTo()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithInvalidEndHourCannotBeCreated(){
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("longitude").performScrollTo()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 25:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("addActivity").performScrollTo()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithInvalidStartMinuteCannotBeCreated(){
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 12:60")
        composeRule.onNodeWithTag("longitude").performScrollTo()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("addActivity").performScrollTo()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithInvalidEndMinuteCannotBeCreated(){
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("longitude").performScrollTo()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 13:60")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("addActivity").performScrollTo()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithStartDateTimeAfterEndDateTimeCannotBeCreated(){
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("02-01-2021 12:00")
        composeRule.onNodeWithTag("longitude").performScrollTo()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("addActivity").performScrollTo()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithInvalidLatitudeCannotBeCreated(){
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("longitude").performScrollTo()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("latitude").performTextInput("91.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("addActivity").performScrollTo()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithInvalidLongitudeCannotBeCreated(){
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("longitude").performScrollTo()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("181.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("1")
        composeRule.onNodeWithTag("addActivity").performScrollTo()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }

    @Test
    fun eventWithInvalidPointsAwardedCannotBeCreated(){
        successCount = 0
        composeRule.onNodeWithTag("name").performTextInput("name")
        composeRule.onNodeWithTag("description").performTextInput("description")
        composeRule.onNodeWithTag("Start date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 12:00")
        composeRule.onNodeWithTag("longitude").performScrollTo()
        composeRule.onNodeWithTag("End date and time (dd-mm-yyyy hh:mm)Field")
            .performTextInput("01-01-2021 13:00")
        composeRule.onNodeWithTag("latitude").performTextInput("1.0")
        composeRule.onNodeWithTag("longitude").performTextInput("1.0")
        composeRule.onNodeWithTag("pointsAwardedField").performTextInput("-1")
        composeRule.onNodeWithTag("addActivity").performScrollTo()
        composeRule.onNodeWithTag("lateParticipationAllowedCheckbox").performClick()
        composeRule.onNodeWithTag("addActivity").performClick()
        Thread.sleep(500)
        Assert.assertEquals(0, successCount)
    }




}