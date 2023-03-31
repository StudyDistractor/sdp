package com.github.studydistractor.sdp.procrastinationActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

class DistractionList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FireBaseProcrastinationActivityService().fetchProcrastinationActivities { procrastinationActivities ->
            displayActivities(procrastinationActivities)
        }
    }

    private fun displayActivities(procrastinationActivities: List<ProcrastinationActivity>) {
        setContent {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (a in procrastinationActivities) {
                    Button (
                        onClick = { launchActivity(a) },
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(0.8f)
                            .testTag("button"),
//                        change color of button
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6650a4))
                    ) {
//                        make text white
                        Text(
                            text = a.name!!,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

    private fun launchActivity(activity: ProcrastinationActivity) {
        val intent = Intent(this, ProcrastinationActivityActivity::class.java)
        intent.putExtra(EXTRA_ACTIVITY, activity)
        startActivity(intent)
    }
}