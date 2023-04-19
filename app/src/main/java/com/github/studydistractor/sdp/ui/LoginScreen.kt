package com.github.studydistractor.sdp.ui

import android.content.ContentValues
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.login.LoginAuthInterface

/**
 * Author: Bluedrack and AdrienBouquet
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onRegisterButtonClicked: () -> Unit,
    onLoggedIn: () -> Unit,
    loginAuth : LoginAuthInterface
) {
    val context = LocalContext.current
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("login-screen__main-container"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val email = remember { mutableStateOf(TextFieldValue("")) }
        val password = remember { mutableStateOf(TextFieldValue("")) }
        Text(
            text = "Log In",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        OutlinedTextField(
            value = email.value,
            label = { Text(text = "Email") },
            onValueChange = {
                email.value = it
            },
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

            value = password.value,
            onValueChange = {
                password.value = it
            },
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

        var emailstr = email.value.text.trim()
        var passwordstr = password.value.text.trim()
        Button(
            onClick = {
                if (TextUtils.isEmpty(emailstr) || TextUtils.isEmpty(passwordstr)) {
                    Toast.makeText(context, "Please fill the blanks", Toast.LENGTH_SHORT).show()
                } else {
                    loginAuth.loginWithEmail(emailstr, passwordstr)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Login Succeed", Toast.LENGTH_SHORT)
                                    .show()
                                Log.d(ContentValues.TAG, "signInWithEmail:success")
                                onLoggedIn()
                            } else {
                                Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT)
                                    .show()
                                Log.d(ContentValues.TAG, "signInWithEmail:failed")
                                Log.d(
                                    ContentValues.TAG,
                                    "email: " + emailstr + ", password: " + passwordstr
                                )
                            }
                        }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("login")
        ) {
            Text(text = "Log in")
        }
        Button(
            onClick = onRegisterButtonClicked,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("register")
        ) {
            Text(text = "Register")
        }
    }
}