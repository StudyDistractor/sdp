package com.github.studydistractor.sdp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.Text
import androidx.activity.compose.setContent

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Text("Yaaaaas") }
    }
}