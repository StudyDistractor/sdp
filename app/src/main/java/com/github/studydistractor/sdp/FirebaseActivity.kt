package com.github.studydistractor.sdp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.TextFieldValue
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.concurrent.CompletableFuture

class FirebaseActivity : AppCompatActivity() {
    val db : DatabaseReference = Firebase.database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Column {
                var phone by remember { mutableStateOf(TextFieldValue("")) }
                var email by remember { mutableStateOf(TextFieldValue("")) }
                TextField(
                    value = phone,
                    modifier = Modifier.testTag("textFieldName"),
                    label = { Text(text = "Enter Your Phone") },
                    onValueChange = {
                        phone = it
                    }
                )
                TextField(
                    value = email,
                    modifier = Modifier.testTag("textFieldName"),
                    label = { Text(text = "Enter Your Email") },
                    onValueChange = {
                        email = it
                    }
                )
                Row{
                    Button(onClick = {setValue(email.text, phone.text)}) {
                        Text("set")
                    }
                    Button(onClick = {
                        val future = CompletableFuture<String>()
                        db.child(phone.text.toString()).get().addOnSuccessListener {
                            if(it.value == null) future.completeExceptionally(NoSuchFieldException())
                            else future.complete(it.value as String)
                        }.addOnFailureListener{
                            future.completeExceptionally(it)
                        }
                        future.thenAccept{
                            email.let { it }
                        }

                    }) {
                        Text("get")
                    }
                }
            }
        }
    }
    private fun setValue(email : String, phone : String){
        db.child(email).setValue(phone);
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun createSignInContent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)

    }
}