package com.github.studydistractor.sdp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

const val EXTRA_ACTIVITY = "activity"

/**
 * This activity displays a procrastination activity
 */
class ProcrastinationActivityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //https://stackoverflow.com/questions/73019160/android-getparcelableextra-deprecated
        val procrastinationData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_ACTIVITY, ProcrastinationActivity::class.java)
        } else {
            intent.getParcelableExtra<ProcrastinationActivity>(EXTRA_ACTIVITY)
        }

        val procrastinationActivity = ProcrastinationActivityActivityViewModel().processActivity(procrastinationData)
        setContent{
            ProcrastinationLayout(procrastinationActivity.name!!, procrastinationActivity.description!!)
        }
    }

    /**
     * Layout for this activity
     */
    @Composable
    fun ProcrastinationLayout(name: String, description: String) {
        Row {
            Column (
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally ) {
                Text(
                    text = name ,
                    fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {launchMapsActivity()}) {
                    Text("Done")
                }

                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }

    /**
     * Launch the Map activity
     */
    private fun launchMapsActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

}