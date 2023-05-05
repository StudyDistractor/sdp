package com.github.studydistractor.sdp.dailyChallenge

import com.github.studydistractor.sdp.ui.state.DailyChallengeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar

class DailyChallengeViewModel(
    private val dailyChallengeModel: DailyChallengeModel
) {
    private val _dailyChallengeModel: DailyChallengeModel = dailyChallengeModel
    private val _uiState = MutableStateFlow(DailyChallengeUiState())
    val uiState: StateFlow<DailyChallengeUiState> = _uiState.asStateFlow()

    private val c: Calendar = Calendar.getInstance()

    init {
        updateDistractions()
    }

    fun updateDistractions() {
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1   // 0 indexed
        val day = c.get(Calendar.DAY_OF_MONTH)
        var dateString = "$year-$month-$day"
        val challenges = _dailyChallengeModel.fetchDailyChallenge(dateString)
        val checkedStates = challenges.map { false }
        val allChecked = false

        _uiState.update {
            it.copy(
                year = year,
                month = month,
                day = day,
                dateString = dateString,
                distractionsToDisplay = challenges,
                checkedStates = checkedStates,
                allChecked = allChecked
            )
        }
    }

    fun onCheckboxClicked(
        distractionIndex: Int,
        newState: Boolean
    ) {
        _uiState.update {
            val newCheckedStates = uiState.value.checkedStates.mapIndexed { i, b ->
                if(i == distractionIndex) newState else b
            }

            it.copy(
                checkedStates = newCheckedStates,
                allChecked = newCheckedStates.all { b -> b }
            )
        }
    }
}