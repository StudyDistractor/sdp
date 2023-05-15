package com.github.studydistractor.sdp.eventChat

import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.ui.state.EventChatUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EventChatViewModel constructor(eventChatModel : EventChatModel) : ViewModel(){
    private val _eventChatModel: EventChatModel = eventChatModel
    private val _uiState = MutableStateFlow(EventChatUiState())
    val uiState: StateFlow<EventChatUiState> = _uiState.asStateFlow()

    init {
        _eventChatModel.messagesObserver { t ->
            _uiState.update {
                it.copy(messages = t)
            }
        }
    }

    fun changeEventChat(eventId : String){
        _eventChatModel.changeCurrentChat(eventId)
        _eventChatModel.messagesObserver { t ->
            _uiState.update { it.copy(messages = t) }
        }
    }

    fun updateMessage(message : String){
        _uiState.update {
            it.copy(message = message)
        }
    }

    fun postMessage(){
        _eventChatModel.postMessage(_uiState.value.message)
            .continueWith{
                _uiState.update {
                    it.copy(message = "")
                }
            }
    }
}