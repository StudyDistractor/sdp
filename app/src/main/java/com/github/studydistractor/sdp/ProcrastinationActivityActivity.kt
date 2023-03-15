package com.github.studydistractor.sdp

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


class ProcrastinationActivityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //https://stackoverflow.com/questions/73019160/android-getparcelableextra-deprecated
        val procrastinationData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("activity", ProcrastinationActivity::class.java)
        } else {
            intent.getParcelableExtra<ProcrastinationActivity>("activity")
        }

        val procrastinationActivity = ProcrastinationActivityActivityViewModel().processActivity(procrastinationData)
        setContent{
            ProcrastinationLayout(procrastinationActivity.name!!, procrastinationActivity.description!!)
        }
    }

    @Composable
    fun ProcrastinationLayout(name: String, description: String) {
        Row {
            Column {
                Text(
                    text = name ,
                    fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description
                )

                Spacer(modifier = Modifier.height(8.dp))

                var doneText by remember{ mutableStateOf("") }
                Button(onClick = {doneText = "activity done"}) {
                    Text("Done")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = doneText)
            }
        }
    }

}