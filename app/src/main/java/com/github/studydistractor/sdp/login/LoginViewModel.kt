package com.github.studydistractor.sdp.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.github.studydistractor.sdp.ui.state.LoginUiState
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
    loginModel: LoginModel
): ViewModel() {
    private val _loginModel: LoginModel = loginModel
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun loginWithEmailAndPassword(): Task<String> {
        val email = uiState.value.email.trim()
        val password = uiState.value.password.trim()

        if(email.isEmpty() || password.isEmpty()) {
            return Tasks.forException(Exception("please fill the blanks"))
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Tasks.forException(Exception("please enter a valid email"))
        }

        return _loginModel.loginWithEmailAndPassword(
            email.trim(),
            password.trim(),
        )
    }
}