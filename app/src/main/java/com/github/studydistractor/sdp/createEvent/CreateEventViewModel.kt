package com.github.studydistractor.sdp.createEvent

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.createActivity.CreateActivityViewModel
import com.github.studydistractor.sdp.ui.state.CreateEventUiState
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar

object EventViewModelConstants {
    const val MAX_NAME_LENGTH = 20
    const val MAX_DESCRIPTION_LENGTH = 200
    const val MAX_LATITUDE = 90.0
    const val MIN_LATITUDE = -90.0
    const val MAX_LONGITUDE = 180.0
    const val MIN_LONGITUDE = -180.0
    const val DATE_TIME_FORMAT_REGEX = "^(\\d{2})-(\\d{2})-(\\d{4}) (\\d{2}):(\\d{2})\$"
    const val MIN_POINTS = 0
    const val MAX_POINTS = 100
}

class CreateEventViewModel(
    createEventModel: CreateEventModel
) : CreateActivityViewModel, ViewModel() {
    private val _createEventModel: CreateEventModel = createEventModel
    private val _uiState = MutableStateFlow(CreateEventUiState())
    override val uiState: StateFlow<CreateEventUiState> = _uiState.asStateFlow()

    override fun updateName(name: String) {
        if (name.length > EventViewModelConstants.MAX_NAME_LENGTH) return

        _uiState.update {
            it.copy(
                name = name,
                supportingTextName = "${name.length}/${EventViewModelConstants.MAX_NAME_LENGTH}",
            )
        }
    }

    override fun updateDescription(description: String) {
        if (description.length > EventViewModelConstants.MAX_DESCRIPTION_LENGTH) return

        _uiState.update {
            it.copy(
                description = description,
                supportingTextDescription = "${description.length}/${EventViewModelConstants.MAX_DESCRIPTION_LENGTH}",
            )
        }
    }

    override fun updateLatitude(latitude: String) {
        _uiState.update {
            it.copy(latitude = latitude)
        }
    }


    override fun updateLongitude(longitude: String) {
        _uiState.update {
            it.copy(longitude = longitude)
        }
    }

    override fun createActivity(): Task<Void> {
        if (!validatePoints()) return Tasks.forException(Exception("Points should be a number between 0 and 100"))
        if (uiState.value.name.isEmpty()) return Tasks.forException(Exception("Name should not be empty"))
        if (uiState.value.description.isEmpty()) return Tasks.forException(Exception("Description should not be empty"))
        if (!validateLatitude()) return Tasks.forException(Exception("Invalid latitude"))
        if (!validateLongitude()) return Tasks.forException(Exception("Invalid longitude"))
        if (!validateDateTime(uiState.value.startDateTime)) return Tasks.forException(Exception("Invalid start date time"))
        if (!validateDateTime(uiState.value.endDateTime)) return Tasks.forException(Exception("Invalid end date time"))
        if (!startDateTimeIsBeforeEndDateTime()) return Tasks.forException(Exception("Start date and time must be before end date and time"))
        val eventInformation = mapOf(
            "name" to uiState.value.name,
            "description" to uiState.value.description,
            "latitude" to uiState.value.latitude!!.toDouble(),
            "longitude" to uiState.value.longitude!!.toDouble(),
            "startDateTime" to uiState.value.startDateTime,
            "endDateTime" to uiState.value.endDateTime,
            "points" to uiState.value.pointsAwarded.toInt(),
            "lateParticipationAllowed" to uiState.value.lateParticipationAllowed,
        )
        return _createEventModel.createEvent(eventInformation)
    }

    /**
     * Sets the start date and time of the activity.
     *
     * @param year The year of the start date.
     * @param month The month (0-11) of the start date.
     * @param dayOfMonth The day of the month (1-31) of the start date.
     * @param hour The hour (0-23) of the start time.
     * @param minute The minute (0-59) of the start time.
     */
    fun setStartDateTime(year: Int, month: Int, dayOfMonth: Int, hour: Int, minute: Int) {
        _uiState.update {
            it.copy(
                startYear = year,
                startMonth = month,
                startDayOfMonth = dayOfMonth,
                startHour = hour,
                startMinute = minute
            )
        }
        refreshStartDateTimeText()
    }

    /**
     * Sets the end date and time of the activity.
     *
     * @param year The year of the end date.
     * @param month The month (0-11) of the end date.
     * @param dayOfMonth The day of the month (1-31) of the end date.
     * @param hour The hour (0-23) of the end time.
     * @param minute The minute (0-59) of the end time.
     */
    fun setEndDateTime(year: Int, month: Int, dayOfMonth: Int, hour: Int, minute: Int) {
        _uiState.update {
            it.copy(
                endYear = year,
                endMonth = month,
                endDayOfMonth = dayOfMonth,
                endHour = hour,
                endMinute = minute
            )
        }
        refreshEndDateTimeText()
    }

    /**
     * Refreshes the start date and time text displayed in the UI based on the current UI state.
     * Uses the year, month, day of month, hour, and minute fields of the UI state to generate a formatted date string,
     * and updates the startDateTime field of the UI state with the generated text.
     */
    private fun refreshStartDateTimeText() {
        val y = uiState.value.startYear
        val m = uiState.value.startMonth
        val d = uiState.value.startDayOfMonth
        val h = uiState.value.startHour
        val min = uiState.value.startMinute

        val dateText = String.format("%02d-%02d-%d %02d:%02d", d, m + 1, y, h, min)

        _uiState.update {
            it.copy(
                startDateTime = dateText
            )
        }
    }

    /**
     * Refreshes the end date and time text displayed in the UI based on the current UI state.
     * Uses the year, month, day of month, hour, and minute fields of the UI state to generate a formatted date string,
     * and updates the endDateTime field of the UI state with the generated text.
     */
    private fun refreshEndDateTimeText() {
        val y = uiState.value.endYear
        val m = uiState.value.endMonth
        val d = uiState.value.endDayOfMonth
        val h = uiState.value.endHour
        val min = uiState.value.endMinute

        val dateText = String.format("%02d-%02d-%d %02d:%02d", d, m + 1, y, h, min)
        _uiState.update {
            it.copy(
                endDateTime = dateText
            )
        }
    }


    /**
     * Updates the start date and time text displayed in the UI with the provided text.
     *
     * @param dateText The new text to display for the start date and time.
     */
    fun updateStartDateTimeText(dateText: String) {
        _uiState.update { it.copy(startDateTime = dateText) }
    }

    /**
     * Updates the end date and time text displayed in the UI with the provided text.
     *
     * @param dateText The new text to display for the end date and time.
     */
    fun updateEndDateTimeText(dateText: String) {
        _uiState.update { it.copy(endDateTime = dateText) }
    }

    /**
     * Updates the late participation allowed field of the UI state with the provided value.
     * @param lateParticipationAllowed The new value for the late participation allowed field.
     */
    fun updateLateParticipationAllowed(lateParticipationAllowed: Boolean) {
        _uiState.update {
            it.copy(lateParticipationAllowed = lateParticipationAllowed)
        }
    }

    /**
     * Updates the points awarded field of the UI state with the provided value.
     * @param pointsAwarded The new value for the points awarded field.
     */
    fun updatePointsAwarded(pointsAwarded: String) {
        _uiState.update {
            it.copy(pointsAwarded = pointsAwarded)
        }
    }


    /**
     * Validates the latitude value stored in the current UI state.
     *
     * @return True if the latitude value is valid and false otherwise.
     */
    private fun validateLatitude(): Boolean {
        val latitude = uiState.value.latitude ?: return false

        val latitudeDouble = latitude.toDoubleOrNull() ?: return false

        if (latitudeDouble < EventViewModelConstants.MIN_LATITUDE || latitudeDouble > EventViewModelConstants.MAX_LATITUDE) return false

        return true
    }

    /**
     * Validates the longitude value stored in the current UI state.
     *
     * @return True if the longitude value is valid and false otherwise.
     */
    private fun validateLongitude(): Boolean {
        val longitude = uiState.value.longitude ?: return false

        val longitudeDouble = longitude.toDoubleOrNull() ?: return false

        if (longitudeDouble < EventViewModelConstants.MIN_LONGITUDE || longitudeDouble > EventViewModelConstants.MAX_LONGITUDE) return false

        return true
    }

    /**
     * Validates the date and time string provided using the format specified by `EventViewModelConstants.DATE_TIME_FORMAT_REGEX`.
     *
     * @param dateTime The date and time string to validate.
     *
     * @return True if the date and time string is valid and false otherwise.
     */
    private fun validateDateTime(dateTime: String): Boolean {
        val dateTimeRegex = Regex(EventViewModelConstants.DATE_TIME_FORMAT_REGEX)
        val matchResult = dateTimeRegex.matchEntire(dateTime) ?: return false
        val dateText = dateTime.slice(0..7)

        try {
            SimpleDateFormat("dd-mm-yyyy").parse(dateText)
        } catch (pe: ParseException) {
            return false
        }
        val year = matchResult.groupValues[3].toInt()
        val month = matchResult.groupValues[2].toInt() - 1
        val dayOfMonth = matchResult.groupValues[1].toInt()
        val hour = matchResult.groupValues[4].toInt()
        val minute = matchResult.groupValues[5].toInt()

        if (year < 0) return false
        if (month < 0 || month > 11) return false
        if (dayOfMonth < 1 || dayOfMonth > 31) return false
        if (hour < 0 || hour > 23) return false
        if (minute < 0 || minute > 59) return false

        return true
    }


    /**
     * Checks if the start date/time is before the end date/time.
     *
     * @return true if the start date/time is before the end date/time, false otherwise
     */
    private fun startDateTimeIsBeforeEndDateTime(): Boolean {
        val startMatchResult =
            Regex(EventViewModelConstants.DATE_TIME_FORMAT_REGEX).matchEntire(uiState.value.startDateTime)
                ?: return false
        val endMatchResult =
            Regex(EventViewModelConstants.DATE_TIME_FORMAT_REGEX).matchEntire(uiState.value.endDateTime)
                ?: return false

        val startYear = startMatchResult.groupValues[3].toInt()
        val startMonth = startMatchResult.groupValues[2].toInt()
        val startDayOfMonth = startMatchResult.groupValues[1].toInt()
        val startHour = startMatchResult.groupValues[4].toInt()
        val startMinute = startMatchResult.groupValues[5].toInt()

        val endYear = endMatchResult.groupValues[3].toInt()
        val endMonth = endMatchResult.groupValues[2].toInt()
        val endDayOfMonth = endMatchResult.groupValues[1].toInt()
        val endHour = endMatchResult.groupValues[4].toInt()
        val endMinute = endMatchResult.groupValues[5].toInt()

        val startCalendar = Calendar.getInstance()
        startCalendar.set(startYear, startMonth, startDayOfMonth, startHour, startMinute)

        val endCalendar = Calendar.getInstance()
        endCalendar.set(endYear, endMonth, endDayOfMonth, endHour, endMinute)

        return startCalendar.before(endCalendar)
    }

    /**
     * Validates the points awarded value stored in the current UI state.
     *
     * @return True if the points awarded value is valid and false otherwise.
     */
    private fun validatePoints(): Boolean {
        val pointsAwardedInt = uiState.value.pointsAwarded.toIntOrNull() ?: return false

        if (pointsAwardedInt < EventViewModelConstants.MIN_POINTS || pointsAwardedInt > EventViewModelConstants.MAX_POINTS) return false

        return true
    }
}