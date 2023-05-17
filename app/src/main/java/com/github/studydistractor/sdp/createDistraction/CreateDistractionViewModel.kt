package com.github.studydistractor.sdp.createDistraction

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.createActivity.CreateActivityViewModel
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.ui.state.CreateDistractionUiState
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object CreateDistractionViewModelConstants {
    const val MAX_NAME_LENGTH = 20
    const val MAX_DESCRIPTION_LENGTH = 200
    const val MAX_LATITUDE = 90.0
    const val MIN_LATITUDE = -90.0
    const val MAX_LONGITUDE = 180.0
    const val MIN_LONGITUDE = -180.0
}
class CreateDistractionViewModel(
    createDistractionModel: CreateDistractionModel
) : CreateActivityViewModel, ViewModel() {
    private val _createDistractionModel: CreateDistractionModel = createDistractionModel
    private val _uiState = MutableStateFlow(CreateDistractionUiState())
    override val uiState: StateFlow<CreateDistractionUiState> = _uiState.asStateFlow()


    /**
     * Update the name
     *
     * @name(String): the new name
     */
    override fun updateName(name: String) {
        if (name.length > CreateDistractionViewModelConstants.MAX_NAME_LENGTH) return

        _uiState.update {
            it.copy(
                name = name,
                supportingTextName = "${name.length}/${CreateDistractionViewModelConstants.MAX_NAME_LENGTH}",
            )
        }
    }

    /**
     * Update the description
     *
     * @description(String): the new description
     */
    override fun updateDescription(description: String) {
        if (description.length > CreateDistractionViewModelConstants.MAX_DESCRIPTION_LENGTH) return

        _uiState.update {
            it.copy(
                description = description,
                supportingTextDescription = "${description.length}/${CreateDistractionViewModelConstants.MAX_DESCRIPTION_LENGTH}",
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

    /**
     * Create a distraction using the data from uiState
     */
    override fun createActivity(): Task<Void> {
        if (uiState.value.name == "" || uiState.value.description == "") {
            return Tasks.forException(Exception("Please fill in the blanks"))
        }
        if ((!uiState.value.latitude.isNullOrBlank()) && (!uiState.value.longitude.isNullOrBlank())) {
            Log.d("CreateDistractionViewModel", "Latitude: ${uiState.value.latitude}, Longitude: ${uiState.value.longitude}")
            if (!validateLatitude() || !validateLongitude()) {
                return Tasks.forException(Exception("Invalid latitude or longitude"))
            }
            return _createDistractionModel.createDistraction(
                Distraction(
                    name = uiState.value.name,
                    description = uiState.value.description,
                    lat = uiState.value.latitude?.toDouble(),
                    long = uiState.value.longitude?.toDouble()
                )
            )
        }
        if ((uiState.value.latitude.isNullOrBlank()) xor (uiState.value.longitude.isNullOrBlank())){
            return Tasks.forException(Exception("Please fill in both latitude and longitude"))
        }

        return _createDistractionModel.createDistraction(
            Distraction(
                name = uiState.value.name,
                description = uiState.value.description
            )
        )
    }

    /**
     * Update the visibility of the location fields.
     * @param isVisible: the new visibility
     */
    fun updateLocationFieldIsVisible(isVisible: Boolean) {
        _uiState.update {
            it.copy(
                locationFieldIsVisible = isVisible
            )
        }
    }

    /**
     * Validates the latitude value stored in the current UI state.
     *
     * @return True if the latitude value is valid and false otherwise.
     */
    private fun validateLatitude(): Boolean {
        val latitudeDouble = uiState.value.latitude?.toDoubleOrNull() ?: return false

        if (latitudeDouble < CreateDistractionViewModelConstants.MIN_LATITUDE || latitudeDouble > CreateDistractionViewModelConstants.MAX_LATITUDE) return false

        return true
    }

    /**
     * Validates the longitude value stored in the current UI state.
     *
     * @return True if the longitude value is valid and false otherwise.
     */
    private fun validateLongitude(): Boolean {
        val longitudeDouble = uiState.value.longitude?.toDoubleOrNull() ?: return false

        if (longitudeDouble < CreateDistractionViewModelConstants.MIN_LONGITUDE || longitudeDouble > CreateDistractionViewModelConstants.MAX_LONGITUDE) return false

        return true
    }
}