package com.github.studydistractor.sdp.createUser

import android.os.Build
import android.telephony.PhoneNumberUtils
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.data.UserData
import com.github.studydistractor.sdp.ui.state.CreateUserUiState
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class CreateUserViewModel(
    createUserModel: CreateUserModel
) : ViewModel() {
    private val MAX_NAME_LENGTH = 255
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

    /**
     * Check if the given phone number is a global phone number (using PhoneNumberUtils library)
     * @param phoneNumber The phone number to check
     * @throws IllegalArgumentException if the given phone number is null or invalid
     */
    private fun checkPhoneFormat(phoneNumber: String): Boolean {
        return !(phoneNumber.isEmpty() || !PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber))
    }

    /**
     * Check if the given name is not empty and it does not exceed or is equal to 255 characters
     * @param name The name to check
     * @throws IllegalArgumentException if the name is not correctly formatted
     */
    private fun checkNameFormat(name: String): Boolean{
        return !(name.isEmpty() || name.length >= MAX_NAME_LENGTH)
    }

    private fun checkDateFormat(date: String): Boolean {
        try {
            SimpleDateFormat("yyyy-mm-dd").parse(uiState.value.dateText)
        } catch (pe: ParseException) {
            return false
        }
        return true
    }

    private fun refreshDateText() {
        val y =  uiState.value.year
        val m =  uiState.value.month
        val d =  uiState.value.dayOfMonth

        val dateText = "$y-$m-$d"

        //val dateText = LocalDate.of(
        //            uiState.value.year,
        //            uiState.value.month,
        //            uiState.value.dayOfMonth
        //        ).format(DateTimeFormatter.ISO_LOCAL_DATE)

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
                month = month + 1,
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

        if (!checkNameFormat(uiState.value.firstname)) {
            return Tasks.forException(Exception("firstname is invalid"))
        }

        if (!checkNameFormat(uiState.value.lastname)) {
            return Tasks.forException(Exception("lastname is invalid"))
        }

        if (!checkPhoneFormat(uiState.value.phoneNumber)) {
            return Tasks.forException(Exception("phoneNumber is invalid"))
        }

        val timestamp = if (checkDateFormat(uiState.value.dateText)){
            (SimpleDateFormat("yyyy-mm-dd").parse(uiState.value.dateText).time/1000L).toInt()
        } else {
            return Tasks.forException(Exception("Invalid Date format"))
        }


        return _createUserModel.createUser(UserData(
            firstname = uiState.value.firstname,
            lastname = uiState.value.lastname,
            phone = uiState.value.phoneNumber,
            birthday = timestamp,
            score = 0
        ))
    }
}