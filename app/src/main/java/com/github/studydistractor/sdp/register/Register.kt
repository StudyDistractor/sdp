package com.github.studydistractor.sdp.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.setValue

class Register {
    companion object {
        fun newUser(auth : RegisterAuthInterface, email: String, password: String, pseudo: String, onRegisterSuccess: () -> Unit, onRegisterFailure: (reason: String) -> Unit) {
            if (email.isEmpty()) {
                onRegisterFailure("Email is empty")
            } else if (password.isEmpty()) {
                onRegisterFailure("Password is empty")
            } else if ( pseudo.isEmpty()) {
                onRegisterFailure("Pseudo is empty")
            } else {

                auth.createUserWithEmailAndPassword(email.trim(), password.trim())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onRegisterSuccess()
                        } else {
                            onRegisterFailure("Failed to update database")
                        }
                    }
            }
        }
    }
}