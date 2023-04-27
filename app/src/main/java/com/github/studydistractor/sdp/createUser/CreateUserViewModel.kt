package com.github.studydistractor.sdp.createUser

import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.data.UserData
import com.github.studydistractor.sdp.ui.state.CreateUserUiState
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class CreateUserViewModel(
    createUserModel: CreateUserModel
) : ViewModel() {
    private val _createUserModel: CreateUserModel = createUserModel
    private val _uiState = MutableStateFlow(CreateUserUiState())
    private val _calendar = Calendar.getInstance()
    val uiState: StateFlow<CreateUserUiState> = _uiState.asStateFlow()

    init {
        // current date
        val year = _calendar[Calendar.YEAR]
        val month = _calendar[Calendar.MONTH]
        val dayOfMonth = _calendar[Calendar.DAY_OF_MONTH]
        setDate(year, month, dayOfMonth)
    }

    private fun refreshDateText() {
        val dateText = LocalDate.of(
            uiState.value.year,
            uiState.value.month,
            uiState.value.dayOfMonth
        ).format(DateTimeFormatter.ISO_LOCAL_DATE)

        _uiState.update {
            it.copy(
                dateText = dateText
            )
        }
    }

    fun setDate(year: Int, month: Int, dayOfMonth: Int) {
        _uiState.update {
            it.copy(
                year = year,
                month = month,
                dayOfMonth = dayOfMonth
            )
        }
        refreshDateText()
    }

    fun updateFirstname(firstname: String) {
        _uiState.update { it.copy(firstname = firstname) }
    }

    fun updateLastname(lastname: String) {
        _uiState.update { it.copy(lastname = lastname) }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun updateDateText(dateText: String) {
        _uiState.update { it.copy(dateText = dateText) }
    }

    fun createUser(): Task<Void> {
        val birthday = hashMapOf(
            "year" to uiState.value.year,
            "month" to uiState.value.month,
            "day" to uiState.value.dayOfMonth
        )
        return _createUserModel.createUser(UserData(
            firstname = uiState.value.firstname,
            lastname = uiState.value.lastname,
            phone = uiState.value.phoneNumber,
            birthday = birthday,
            score = 0
        ))
    }
}