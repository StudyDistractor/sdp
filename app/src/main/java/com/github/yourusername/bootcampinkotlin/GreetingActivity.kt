package com.github.yourusername.bootcampinkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

class GreetingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greets(intent.getStringExtra("name").toString())
        }
    }

    @Composable
    private fun Greets(name: String) {
        Text(
            text = "Hi $name!",
            modifier = Modifier.testTag("greetingText")
            )
    }
}