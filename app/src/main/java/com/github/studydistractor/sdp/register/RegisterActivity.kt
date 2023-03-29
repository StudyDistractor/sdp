package com.github.studydistractor.sdp.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint(AppCompatActivity::class)
class RegisterActivity : Hilt_RegisterActivity() {

    @Inject
    lateinit var auth : RegisterAuthInterface

//    @Inject
//    lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            RegisterScreen()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun RegisterScreen() {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var pseudo by remember { mutableStateOf("") }
        val context = this
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create an Account",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 32.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
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
                value = pseudo,
                onValueChange = { pseudo = it },
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
                value = password,
                onValueChange = { password = it },
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
                onClick = {
                          newUser(email, password, pseudo)
                              .addOnCompleteListener{f ->
                                  if(f.isSuccessful){
                                      Toast.makeText(context, "User Added",Toast.LENGTH_SHORT).show()
                                  } else {
                                      Toast.makeText(context, f.exception?.message,Toast.LENGTH_SHORT).show()

                                  }
                          }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .testTag("register")
            ) {
                Text("Register")
            }
        }

    }
    fun newUser(email: String, password: String, pseudo: String): Task<AuthResult> {
        if (email.isEmpty()) {
            val t = TaskCompletionSource<AuthResult>();
            t.setException(Exception("Email Not valid"))
            return t.task
        }
        if (password.isEmpty()) {
            val t = TaskCompletionSource<AuthResult>();
            t.setException(Exception("Password not valid"))
            return t.task
        }
        if (pseudo.isEmpty()) {
            val t = TaskCompletionSource<AuthResult>();
            t.setException(Exception("Pseudo not valid"))
            return t.task
        }
        return auth.createUserWithEmailAndPassword(email.trim(), password.trim())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registration successful, navigate to next activity
                    val userid = auth.getCurrentUserUid()
                    if (userid != null) {
                        addUserToTheDatabase(email, password, pseudo, userid)
                    }
                }
            }
    }
    fun addUserToTheDatabase(email : String, password : String, pseudo : String, userid : String) {
//        db.getReference("users").child(userid).setValue(hashMapOf(
//            "score" to 0,
//            "birthday" to hashMapOf("day" to 0, "mouth" to 0, "year" to 0),
//            "firstname" to "test",
//            "lastname" to "test",
//            "phone" to "0000000000"
//        ))

    }
}
