package com.github.studydistractor.sdp.createDistraction

import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.createActivity.CreateActivityViewModel
import com.github.studydistractor.sdp.data.Distraction
import com.github.studydistractor.sdp.ui.DistractionScreenConstants
import com.github.studydistractor.sdp.ui.state.CreateActivityUiState
import com.github.studydistractor.sdp.ui.state.CreateDistractionUiState
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
        if (name.length > DistractionScreenConstants.MAX_NAME_LENGTH) return

        _uiState.update {
            it.copy(
                name = name,
                supportingTextName = "${name.length}/${DistractionScreenConstants.MAX_NAME_LENGTH}",
            )
        }
    }

    /**
     * Update the description
     *
     * @description(String): the new description
     */
    override fun updateDescription(description: String) {
        if (description.length > DistractionScreenConstants.MAX_DESCRIPTION_LENGTH) return

        _uiState.update {
            it.copy(
                description = description,
                supportingTextDescription = "${description.length}/${DistractionScreenConstants.MAX_DESCRIPTION_LENGTH}",
            )
        }
    }

    override fun updateLatitude(latitude: String) {
        TODO("Not yet implemented")
    }

    override fun updateLongitude(longitude: String) {
        TODO("Not yet implemented")
    }

    /**
     * Create a distraction using the data from uiState
     */
    override fun createActivity(): Task<Void> {
        if (uiState.value.name == "" || uiState.value.description == "") {
            return Tasks.forException(Exception("Please fill in the blanks"))
        }

        return _createDistractionModel.createDistraction(
            Distraction(
                name = uiState.value.name,
                description = uiState.value.description
            )
        )
    }
}