package com.github.studydistractor.sdp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContent{
            Column (

                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                val email = remember { mutableStateOf(TextFieldValue("")) }
                val password = remember{mutableStateOf(TextFieldValue("")) }
                Text(
                    text = "Log In",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(vertical = 32.dp)
                )

                OutlinedTextField(
                    value = email.value,
                    label = { Text(text = "Email" ) },
                    onValueChange = { email.value = it
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
                    onValueChange = { password.value = it
                    },
                    label = { Text(text = "Password" ) },
                    leadingIcon = {
                        Icon(Icons.Filled.Lock, contentDescription = null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .testTag("password"),
                    visualTransformation = PasswordVisualTransformation()
                )


                var emailstr= email.value.text
                var passwordstr = password.value.text
                Button(onClick = {
                    if (TextUtils.isEmpty(emailstr) || TextUtils.isEmpty(passwordstr)) {
                        Toast.makeText(this@LoginActivity, "Please fill the blanks", Toast.LENGTH_SHORT).show()
                    } else {
                        auth.signInWithEmailAndPassword(emailstr, passwordstr).addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                Toast.makeText(this@LoginActivity, "Login Succeed", Toast.LENGTH_SHORT).show()
                                Log.d(TAG, "signInWithEmail:success")
                                val user = auth.currentUser
                                val intent = Intent(this@LoginActivity, Launcher::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                                Log.d(TAG, "signInWithEmail:failed")
                                Log.d(TAG, "email: " + emailstr + ", password: " + passwordstr)
                            }
                        }
                    }
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .testTag("Log In")
                ) {
                    Text(text= "Log in")
                }
                Button(onClick = { goToRegister()}) {
                    Text(text = "Register")
                }
            }

        }
    }

    // create an action bar button
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        menuInflater.inflate(com.github.studydistractor.sdp.R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // handle button activities
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == com.github.studydistractor.sdp.R.id.futureWelcomeScreen) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}
