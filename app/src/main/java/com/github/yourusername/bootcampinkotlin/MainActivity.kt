package com.github.yourusername.bootcampinkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.TextFieldValue

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                var text by remember { mutableStateOf(TextFieldValue("")) }
                OutlinedTextField(
                    value = text,
                    modifier = Modifier.testTag("textFieldName"),
                    label = { Text(text = "Enter Your Name") },
                    onValueChange = {
                        text = it
                    }
                )
                Button(onClick = {openGreetingsActivity(text.text)}) {
                    Text("Greets")
                }
            }
        }
    }

    private fun openGreetingsActivity(name: String) {
        val intent = Intent(this, GreetingActivity::class.java)
        startActivity(intent.apply{
            putExtra("name", name)
        })
    }

}



