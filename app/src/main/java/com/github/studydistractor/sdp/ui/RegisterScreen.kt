package com.github.studydistractor.sdp.ui

import android.widget.Toast
import androidx.compose.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.register.RegisterViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onSuccess: () -> Unit,
    registerViewModel: RegisterViewModel
) {
    fun showFailureToast(context: Context, message: String) {
        Toast.makeText(context, "Failure: $message", Toast.LENGTH_SHORT)
            .show()
    }

    val uiState by registerViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("register-screen__main-container"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create an Account",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { registerViewModel.updateEmail(it) },
            label = { Text("Email") },
            singleLine = true,
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
            value = uiState.pseudo,
            onValueChange = { registerViewModel.updatePseudo(it) },
            label = { Text("Pseudo") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            leadingIcon = {
                Icon(Icons.Filled.Person, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .testTag("pseudo")
        )

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { registerViewModel.updatePassword(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            leadingIcon = {
                Icon(Icons.Filled.Lock, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .testTag("password")
        )

        Button(
            onClick = { registerViewModel.newUser(
                onSuccess = onSuccess,
                onFailure = {
                    showFailureToast(context, it)
                }
            ) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .testTag("register-screen__registered-button")
        ) {
            Text("Register")
        }
    }
}