package com.github.studydistractor.sdp.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import com.github.studydistractor.sdp.createEvent.CreateEventViewModel
import com.github.studydistractor.sdp.ui.components.CreateActivityButton
import com.github.studydistractor.sdp.ui.components.CreateActivityNameAndDescriptionFields
import com.github.studydistractor.sdp.ui.components.CreateLatitudeAndLongitudeFields
import com.github.studydistractor.sdp.ui.state.CreateEventUiState



/**
 * Screen to create a new event
 *
 * @param createEventViewModel ViewModel for this screen
 * @param onEventCreated Callback to be called when event is created
 */
@Composable
fun CreateEventScreen(
    createEventViewModel: CreateEventViewModel,
    onEventCreated: () -> Unit
) {
    val uiState by createEventViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val startDateTimePicker = dateTimePickerDialog(
        context,
        { y, m, d, h, min -> createEventViewModel.setStartDateTime(y, m, d, h, min) },
        uiState.startYear,
        uiState.startMonth,
        uiState.startDayOfMonth,
        uiState.startHour,
        uiState.startMinute
    )
    val endDateTimePicker = dateTimePickerDialog(
        context,
        { y, m, d, h, min -> createEventViewModel.setEndDateTime(y, m, d, h, min) },
        uiState.endYear,
        uiState.endMonth,
        uiState.endDayOfMonth,
        uiState.endHour,
        uiState.endMinute
    )



    Box(
        modifier = Modifier
            .padding(16.dp)
            .testTag("create-activity-screen__main-container")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .testTag("create-event-screen__column"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Row {
                androidx.compose.material.Text(
                    text = "Create event",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(vertical = 32.dp)
                        .testTag("create-event-screen__title")
                )
            }

            CreateActivityNameAndDescriptionFields(
                createActivityViewModel = createEventViewModel
            )

            CreateDateTimeField(
                valueText = uiState.startDateTime,
                onValueChange = { createEventViewModel.updateStartDateTimeText(it) },
                labelText = "Start date and time (dd-mm-yyyy hh:mm)",
                onClickButton = { startDateTimePicker.show() }
            )

            CreateDateTimeField(
                valueText = uiState.endDateTime,
                onValueChange = { createEventViewModel.updateEndDateTimeText(it) },
                labelText = "End date and time (dd-mm-yyyy hh:mm)",
                onClickButton = { endDateTimePicker.show() }
            )

            CreateLatitudeAndLongitudeFields(
                createActivityViewModel = createEventViewModel,
                uiState = uiState
            )

            PointsAwardedField(createActivityViewModel = createEventViewModel, uiState = uiState)
            LateParticipationCheckbox(
                createEventViewModel = createEventViewModel,
                uiState = uiState
            )

            CreateActivityButton(
                createActivityViewModel = createEventViewModel,
                onActivityCreated = onEventCreated,
                buttonText = "Create event"
            )
        }
    }
}

/**
 * Creates a date picker dialog
 * @param context Context
 * @param setDateTime Callback to be called when date and time is set
 * @param year Year
 * @param month Month
 * @param dayOfMonth Day of month
 * @param hour Hour
 * @param minute Minute
 * @return DatePickerDialog
 */
private fun dateTimePickerDialog(
    context: Context,
    setDateTime: (year: Int, month: Int, day: Int, h: Int, m: Int) -> Unit,
    year: Int,
    month: Int,
    dayOfMonth: Int,
    hour: Int,
    minute: Int
): DatePickerDialog {
    return DatePickerDialog(
        context,
        { _, y, m, d ->
            val timePicker = TimePickerDialog(
                context,
                { _, h, min -> setDateTime(y, m, d, h, min) },
                hour,
                minute,
                true
            )
            timePicker.show()
        },
        year,
        month,
        dayOfMonth
    )
}

/**
 * Creates a text field to allow user to enter date and time
 * @param valueText Text to be displayed in the text field
 * @param onValueChange Callback to be called when text is changed
 * @param labelText Label text
 * @param onClickButton Callback to be called when button is clicked
 * @return TextField
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDateTimeField(
    valueText: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    onClickButton: () -> Unit,
) {
    OutlinedTextField(
        value = valueText,
        onValueChange = { onValueChange(it) },
        label = { Text(labelText) },
        trailingIcon = {
            Button(
                onClick = { onClickButton() },
                colors = ButtonDefaults.buttonColors(contentColor = Color.White),
                modifier = Modifier
                    .testTag(labelText + "Button")
                    .padding(8.dp),
                shape = MaterialTheme.shapes.small,
            ) {
                Icon(Icons.Filled.CalendarMonth, contentDescription = null)
            }
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag(labelText + "Field")
    )
}

/**
 * Creates a checkbox to allow user to select if late participation is allowed
 * @param createEventViewModel ViewModel
 * @param uiState UI state
 * @return Row
 */
@Composable
fun LateParticipationCheckbox(
    createEventViewModel: CreateEventViewModel,
    uiState: CreateEventUiState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("checkBoxRow"),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = uiState.lateParticipationAllowed,
            onCheckedChange = { createEventViewModel.updateLateParticipationAllowed(it) },
            modifier = Modifier
                .testTag("lateParticipationAllowedCheckbox")
        )
        Text(
            text = "Allow late participation to this event",
            modifier = Modifier
                .testTag("lateParticipationAllowedText")
                .padding(8.dp)
        )
    }
}

/**
 * Creates a text field to allow user to enter points awarded by the event
 * @param createActivityViewModel ViewModel
 * @param uiState UI state
 * @return Row
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointsAwardedField(
    createActivityViewModel: CreateEventViewModel,
    uiState: CreateEventUiState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("pointsAwardedRow")
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "This event awards",
            modifier = Modifier
                .testTag("pointsAwardedText")
                .padding(8.dp)
        )
        OutlinedTextField(
            value = uiState.pointsAwarded,
            onValueChange = { createActivityViewModel.updatePointsAwarded(it) },
            singleLine = true,
            modifier = Modifier
                .padding(vertical = 2.dp)
                .testTag("pointsAwardedField")
                .weight(1f)
        )
        Text(
            text = " points.",
            modifier = Modifier
                .testTag("pointsAwardedValueText")
                .padding(8.dp)
        )
    }

}