package com.github.studydistractor.sdp

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
//            center column horizontally
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                center text horizontally


                Text("Hello human !")
                Button(onClick = { launchMapsActivity() }) {
                    Text("Take me to Sat !")
                }
            }


        }
    }

//    launch maps activity when pressing the button
private fun launchMapsActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
}