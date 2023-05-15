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
        _eventChatModel.observeMessages { t ->
            _uiState.update {
                it.copy(messages = t)
            }
        }
    }

    /**
     * Change to current event chat
     * @param eventId The event id to get messages and post message to.
     */
    fun changeEventChat(eventId : String){
        _eventChatModel.changeCurrentChat(eventId)
        _eventChatModel.observeMessages { t ->
            _uiState.update { it.copy(messages = t) }
        }
    }

    /**
     * Update the text field for the message uiState
     * @param message The message to update to
     */
    fun updateMessage(message : String){
        _uiState.update {
            it.copy(message = message)
        }
    }

    /**
     * Post the current message in the uiState
     */
    fun postMessage(){
        if(_uiState.value.message.isEmpty()) return
        _eventChatModel.postMessage(_uiState.value.message)
            .continueWith{
                _uiState.update {
                    it.copy(message = "")
                }
            }
    }
}