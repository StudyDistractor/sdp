package com.github.studydistractor.sdp.distractionStat

import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.ui.state.DistractionStatUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DistractionStatViewModel(distractionStatModel: DistractionStatModel) : ViewModel() {
    private val _distractionStatModel: DistractionStatModel = distractionStatModel
    private val _uiState = MutableStateFlow(DistractionStatUiState())
    val uiState: StateFlow<DistractionStatUiState> = _uiState.asStateFlow()

    fun updateDid(did : String){
        _uiState.update {
            it.copy(did = did)
        }
        _distractionStatModel.updateCurrentDistraction(did)
    }

    fun updateFeedback(feedback : String){
        _uiState.update {
            it.copy(feedback = feedback)
        }
    }

    fun updateTag(tag : String){
        _uiState.update {
            it.copy(tag = tag)
        }
    }
    fun like(){
        _distractionStatModel.postLike(_uiState.value.did).continueWith({refreshModel()})
    }

    fun dislike(){
        _distractionStatModel.postLike(_uiState.value.did).continueWith({refreshModel()})
    }

    fun postFeedback(){
        _distractionStatModel.postNewFeedback(_uiState.value.did, _uiState.value.feedback).continueWith({refreshModel()})
    }

    fun postTag(){
        _distractionStatModel.addTag(_uiState.value.did, _uiState.value.feedback).continueWith({refreshModel()})
    }
    fun refreshModel(){
        _distractionStatModel.fetchDistractionTags(_uiState.value.did).continueWith{
                t-> _uiState.update { it.copy(feedbacks = t.result) }
        }
        _distractionStatModel.fetchDistractionFeedback(_uiState.value.did).continueWith { t ->
            _uiState.update { it.copy(tags = t.result) }
        }
        _distractionStatModel.fetchLikeCount(_uiState.value.did).continueWith{ t ->
            _uiState.update {
                it.copy(likes = t.result)
            }
        }
        _distractionStatModel.fetchDislikeCount(_uiState.value.did).continueWith{
            t ->
            _uiState.update { it.copy(dislikes = t.result) }
        }
    }
}