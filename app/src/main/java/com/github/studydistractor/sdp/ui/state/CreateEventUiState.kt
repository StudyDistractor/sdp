package com.github.studydistractor.sdp.ui.state

data class CreateEventUiState (
    override val name: String = "",
    override val description: String = "",
    override val supportingTextName: String = "",
    override val supportingTextDescription: String = "",
    val startDateTime: String = "",
    val endDateTime: String = "",
    val startYear: Int = 2023,
    val startMonth: Int = 4,
    val startDayOfMonth: Int = 1,
    val startHour: Int = 0,
    val startMinute: Int = 0,
    val endYear: Int = 2023,
    val endMonth: Int = 4,
    val endDayOfMonth: Int = 1,
    val endHour: Int = 0,
    val endMinute: Int = 0,
    val lateParticipationAllowed: Boolean = false,
    override val latitude: String? = null,
    override val longitude: String? = null,
    val pointsAwarded: String = "",
) : CreateActivityUiState