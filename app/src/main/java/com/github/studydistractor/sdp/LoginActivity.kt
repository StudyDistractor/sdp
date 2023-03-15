package com.github.studydistractor.sdp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContent{
            Column {
                val email = remember { mutableStateOf(TextFieldValue("")) }
                val password = remember{mutableStateOf(TextFieldValue("")) }

                TextField(
                    value = email.value,
                    onValueChange = { email.value = it
                    },
                    label = { Text(text = "E-mail" ) },
                    //placeholder = { Text(text = "Your Placeholder/Hint") },
                )
                TextField(
                    value = password.value,
                    onValueChange = { password.value = it
                    },
                    label = { Text(text = "Password" ) },
                    //placeholder = { Text(text = "Your Placeholder/Hint") },
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
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                                Log.d(TAG, "signInWithEmail:failed")
                            }
                        }
                    }

                }) {
                    Text(text= "Log in")
                }
            }

        }
    }
}
