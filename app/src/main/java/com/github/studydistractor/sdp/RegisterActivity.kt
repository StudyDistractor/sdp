package com.github.studydistractor.sdp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.arch.core.executor.TaskExecutor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import com.google.android.gms.tasks.Tasks
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class RegisterActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth
        db = Firebase.firestore
        setContent {
            RegisterScreen()
        }
    }

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
                style = MaterialTheme.typography.h4,
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
                          registerNewUser(email, password, pseudo, auth, db)
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

}
fun registerNewUser(email: String, password: String, pseudo: String, auth : FirebaseAuth, db : FirebaseFirestore): Task<AuthResult> {
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
                val userid = auth.currentUser?.uid
                if (userid != null) {
                    val userDoc = db.collection("users").document(userid)
                    val data = hashMapOf(
                        "points" to 0,
                        "pseudo" to pseudo.trim()
                    )
                    userDoc.set(data)
                }
            }
        }
}
