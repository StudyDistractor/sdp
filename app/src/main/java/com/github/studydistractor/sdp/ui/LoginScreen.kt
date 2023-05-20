package com.github.studydistractor.sdp.ui

import android.widget.Toast
import androidx.compose.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.login.LoginViewModel

/**
 * Author: Bluedrack and AdrienBouquet
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onRegisterButtonClicked: () -> Unit,
    onLoggedIn: () -> Unit,
    loginViewModel: LoginViewModel
) {
    fun showFailureToast(context: Context, message: String) {
        Toast.makeText(context, "Failure: $message", Toast.LENGTH_SHORT)
            .show()
    }

    val uiState by loginViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 32.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("login-screen__main-container"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Log In",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = uiState.email,
                label = { Text(text = "Email") },
                onValueChange = {loginViewModel.updateEmail(it) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                leadingIcon = {
                    Icon(Icons.Filled.Email, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .testTag("email")
            )
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { loginViewModel.updatePassword(it) },
                label = { Text(text = "Password") },
                leadingIcon = {
                    Icon(Icons.Filled.Lock, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .testTag("password"),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    loginViewModel.loginWithEmailAndPassword()
                        .addOnSuccessListener { onLoggedIn() }
                        .addOnFailureListener { showFailureToast(context, it.message.orEmpty()) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("login"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Log in",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Button(
                onClick = onRegisterButtonClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("register"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "Register",
                    color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}