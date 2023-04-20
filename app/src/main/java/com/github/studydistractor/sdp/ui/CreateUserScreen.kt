package com.github.studydistractor.sdp.ui

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.account.CreateAccountInterface
import com.github.studydistractor.sdp.account.CreateAccount.Companion.displayError
import com.github.studydistractor.sdp.ui.Constants.DATE_REGEX
import com.github.studydistractor.sdp.user.UserData
import com.github.studydistractor.sdp.user.UserService
import com.github.studydistractor.sdp.user.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.*


object Constants{
    const val TAG = "CreateAccountScreen"
    // TODO add more constants
}

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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUserScreen(
    onUserCreated: () -> Unit,
    userService : UserService
){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedDateText by remember { mutableStateOf("") }

    // Fetching current year, month and day
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, dayOfMonth
    )

    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("create_account_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

       // Firstname Text Field
        OutlinedTextField(
            value = firstname,
            onValueChange = { firstname = it },
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
                .testTag("firstname")
        )

        // LastName Text Field
        OutlinedTextField(
            value = lastname,
            onValueChange = { lastname = it },
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
                .testTag("lastname")
        )

        // Phone Text Field
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
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
                .testTag("phone")
        )

        OutlinedTextField(
            value = selectedDateText,
            onValueChange = { selectedDateText = it },
            leadingIcon = {
                Icon(Icons.Filled.CardGiftcard, contentDescription = null)
            },
            label = { Text("Birthday") },
            trailingIcon = { Button(
                    onClick = { datePicker.show()},
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White),
                    modifier = Modifier.testTag("selectBirthdayButton").padding(8.dp),
                    shape = MaterialTheme.shapes.small,
            ) {
                Text("Select")
            }},
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .testTag("birthday")
        )

        // Validate
        Button( onClick = {
            var isUserCreated = true
            val birthday = hashMapOf(
                "year" to year, "month" to month, "day" to dayOfMonth)
            val user = UserData(userId, firstname, lastname, phone, birthday, 0)
            try {
                UserViewModel().processUser(user, userService)
            } catch (e: java.lang.Exception){
                isUserCreated = false
                e.message?.let { displayError(context, it) }
            }

            if(isUserCreated){
                onUserCreated()
            }

        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .testTag("validbutton"),

            ) {
            Text(text = "Validate")
        }
    }
}

private fun displayError(context: Context, data: String){
    Log.d(Constants.TAG, "User has not been created $data")
    Toast.makeText(context, data, Toast.LENGTH_SHORT).show()
}
