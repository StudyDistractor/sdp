package com.github.studydistractor.sdp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.ui.core.Text
import androidx.ui.core.setContent
import com.github.studydistractor.sdp.ui.UIBottomAppBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            UIBottomAppBar()
        }
    }
}