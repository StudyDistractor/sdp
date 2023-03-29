package com.github.studydistractor.sdp.account

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.Launcher
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

/**
 * This activity class is responsible for creating a new account. It uses Hilt dependency injection
 * to inject the [CreateAccInterface] instance, which is used to post data to the server. The class
 * also uses Jetpack Compose to build the UI components of the screen, including text fields for
 * entering user information, such as first name, last name, phone number, and birthday, and a
 * button for validating the entered data and creating a new account.
 *
 * This class is annotated with @AndroidEntryPoint (AppCompatActivity::class) to provide Hilt
 * dependency injection support for the activity.
 * @property createAcc The [CreateAccInterface] instance used to post data to the server
 */
@AndroidEntryPoint (AppCompatActivity::class)
class CreateAccountActivity : Hilt_CreateAccountActivity() {

    @Inject
    lateinit var createAcc: CreateAccInterface // Hilt Injection

    object Constants {
        const val TAG = "CreateAccountActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CreateAccScreen() }
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
    @Preview
    @Composable
    fun CreateAccScreen() {
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

        var firstname by remember { mutableStateOf("") }
        var lastname by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = if (selectedDateText.isNotEmpty()) {
                    "Your birthday is $selectedDateText"
                } else {
                    "Please pick your birthday"
                }
            )

            // Birthday button
            Button(
                onClick = {
                    datePicker.show()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .testTag("birthday"),

            ) {
                Icon(Icons.Filled.CardGiftcard, contentDescription = null)
                Text(text = "Select your birthday")
            }

            // Firstname Text Field
            OutlinedTextField(
                value = firstname,
                onValueChange = { firstname = it },
                label = { Text("Firstname") },
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
                label = { Text("Lastname") },
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
                label = { Text("Phone") },
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

            // Validate
            Button( onClick = {
                if (selectedDateText.isEmpty()){
                    displayError("Date")
                } else if (!createAcc.checkNameFormat(firstname)){
                    displayError("Firstname")
                } else if (!createAcc.checkNameFormat(lastname)){
                    displayError("Lastname")
                } else if (!createAcc.checkPhoneFormat(phone)){
                    displayError("Phone")
                } else {
                    createAcc.postData(year, month, dayOfMonth, firstname, lastname, phone)
                    val intent = Intent(context, Launcher::class.java)
                    startActivity(intent)
                }
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .testTag("validbutton"),

                ) {
                Text(text = "Valid data")
            }
        }

    }


    private fun displayError(data: String){
        Log.d(Constants.TAG, "check failed on $data")
        Toast.makeText(this, "$data is invalid", Toast.LENGTH_SHORT).show()
    }
}
