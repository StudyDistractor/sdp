package com.github.studydistractor.sdp.distractionList

import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.ui.state.DistractionListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Collections


class DistractionListViewModel(
    distractionListModel: DistractionListModel
) : ViewModel(){
    private val _distractionListModel: DistractionListModel = distractionListModel
    private val _uiState = MutableStateFlow(DistractionListUiState())
    val uiState: StateFlow<DistractionListUiState> = _uiState.asStateFlow()

    init {
        showAllDistractions()
        _uiState.update {
            it.copy(filtersAvailableTags = _distractionListModel.getAvailableTags())
        }
    }

    fun showFilteredDistractions() {
        _distractionListModel.getFilteredDistractions(
            length = uiState.value.filtersSelectedLength,
            tags = uiState.value.filtersSelectedTags
        ).addOnSuccessListener { distractions ->
            _uiState.update {
                it.copy(distractionList = distractions)
            }
        }

    }

    private fun showAllDistractions() {
        _distractionListModel.getAllDistractions()
            .addOnSuccessListener { distractions ->
                _uiState.update {
                    it.copy(distractionList = distractions)
                }
            }
    }

    fun updateFiltersSelectedLength(length: Distraction.Length) {
        _uiState.update { it.copy(
            filtersSelectedLength = if(it.filtersSelectedLength == length) null else length
        ) }
        showFilteredDistractions()
    }

    fun addFiltersSelectedTag(tag: String) {
        _uiState.update {
            it.copy(filtersSelectedTags = uiState.value.filtersSelectedTags.plus(tag))
        }
        showFilteredDistractions()
    }

    fun removeFiltersSelectedTag(tag: String) {
        _uiState.update {
            it.copy(filtersSelectedTags = uiState.value.filtersSelectedTags.minus(tag))
        }
        showFilteredDistractions()
    }

    fun clearFiltersSelectedTags() {
        _uiState.update { it.copy(filtersSelectedTags = Collections.emptySet()) }
        showFilteredDistractions()
    }

    fun updateFiltersExpanded(expanded: Boolean) {
        _uiState.update {
            it.copy(filtersExpanded = expanded)
        }
    }
}