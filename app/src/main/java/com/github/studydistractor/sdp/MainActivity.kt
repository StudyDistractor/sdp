package com.github.studydistractor.sdp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import com.github.studydistractor.sdp.ui.UIBottomAppBar
import com.github.studydistractor.sdp.ui.Useless

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            UIBottomAppBar()
        }
    }
}