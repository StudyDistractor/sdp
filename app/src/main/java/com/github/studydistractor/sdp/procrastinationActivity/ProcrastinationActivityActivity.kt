package com.github.studydistractor.sdp.procrastinationActivity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.maps.MapsActivity

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
                modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(26.dp),
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

                Button(
                    onClick = {launchMapsActivity()},
                    modifier = Modifier.testTag("done")
                ) {
                    Text("Take me back to the map!")
                }
                Button(
                    onClick = {launchDistractionList()},
                ) {
                    Text("I want more distractions!")
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

    private fun launchDistractionList() {
        val intent = Intent(this, DistractionList::class.java)
        startActivity(intent)
    }

}