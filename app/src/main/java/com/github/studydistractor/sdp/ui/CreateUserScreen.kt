package com.github.studydistractor.sdp.ui

import android.app.DatePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.createUser.CreateUserViewModel
import java.util.*



/**
 * A composable function that creates a screen for creating a new account. The function
 * includes several fields for entering user information, such as first name, last name,
 * phone number, and birthday. The function also includes a button for validating the
 * entered data and creating a new account.
 * The function uses Jetpack Compose for building the UI components, and it relies on the
 * DatePickerDialog to allow the user to select their birthday. The function also uses
 * the LocalContext.current property to access the current context of the application.
 * @return A composable UI screen for creating a new account
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUserScreen(
    onUserCreated: () -> Unit,
    createUserViewModel: CreateUserViewModel
){
    fun showFailureToast(context: Context, message: String) {
        Toast.makeText(context, "Failure: $message", Toast.LENGTH_SHORT)
            .show()
    }

    val uiState by createUserViewModel.uiState.collectAsState()
    val context = LocalContext.current

    val datePicker = DatePickerDialog(
        context,
        { _, y, m, d -> createUserViewModel.setDate(y, m, d) },
        uiState.year,
        uiState.month,
        uiState.dayOfMonth
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("create-account-screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

       // Firstname Text Field
        OutlinedTextField(
            value = uiState.firstname,
            onValueChange = { createUserViewModel.updateFirstname(it) },
            label = { Text("First name") },
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
                .testTag("create-account-screen__firstname")
        )

        // LastName Text Field
        OutlinedTextField(
            value = uiState.lastname,
            onValueChange = { createUserViewModel.updateLastname(it) },
            label = { Text("Last name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            leadingIcon = {
                Icon(Icons.Filled.Person2, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .testTag("create-account-screen__lastname")
        )

        // Phone Text Field
        OutlinedTextField(
            value = uiState.phoneNumber,
            onValueChange = { createUserViewModel.updatePhoneNumber(it) },
            label = { Text("Phone (ex: +41123456789)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            leadingIcon = {
                Icon(Icons.Filled.Phone, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .testTag("create-account-screen__phone")
        )

        OutlinedTextField(
            value = uiState.dateText,
            onValueChange = { createUserViewModel.updateDateText(it) },
            leadingIcon = {
                Icon(Icons.Filled.CardGiftcard, contentDescription = null)
            },
            label = { Text("Birthday") },
            trailingIcon = { Button(
                    onClick = { datePicker.show()},
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White),
                    modifier = Modifier.testTag("create-account-screen__selectBirthdayButton").padding(8.dp),
                    shape = MaterialTheme.shapes.small,
            ) {
                Text("Select")
            }},
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .testTag("create-account-screen__birthday")
        )

        // Validate
        Button(
            onClick = {
                createUserViewModel
                    .createUser()
                    .addOnSuccessListener { onUserCreated() }
                    .addOnFailureListener { showFailureToast(context, it.message.orEmpty()) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .testTag("create-account-screen__validbutton"),
        ) {
            Text(text = "Validate")
        }
    }
}