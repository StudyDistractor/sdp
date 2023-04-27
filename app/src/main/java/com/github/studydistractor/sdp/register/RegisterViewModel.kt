package com.github.studydistractor.sdp.register

import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.ui.state.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel(
    registerModel: RegisterModel
) : ViewModel() {
    private val _registerModel: RegisterModel = registerModel
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun updatePseudo(pseudo: String) {
        _uiState.update { it.copy(pseudo = pseudo) }
    }

    fun newUser(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (uiState.value.email.isEmpty()) {
            onFailure("Email is empty")
            return
        }
        if (uiState.value.password.isEmpty()) {
            onFailure("Password is empty")
            return
        }
        if (uiState.value.pseudo.isEmpty()) {
            onFailure("Pseudo is empty")
            return
        }
        if (uiState.value.password.length < 6) {
            onFailure("Password must be at least 6 characters") // TODO: refactor: hardcoded strings should not be in the viewmlodel
            return
        }

        _registerModel.createUserWithEmailAndPassword(
            uiState.value.email.trim(),
            uiState.value.password.trim()
        ).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it.message.orEmpty())
        }
    }
}