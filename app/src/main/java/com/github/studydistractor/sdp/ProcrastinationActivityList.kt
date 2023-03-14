package com.github.studydistractor.sdp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.Text
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


class ProcrastinationActivityList : AppCompatActivity() {
    private val service = FireBaseProcrastinationActivityService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProcrastinationActivityListList()
        }
    }

    @Composable
    private fun ProcrastinationActivityListList() {
        val activities = service.fetchProcrastinationActivities()
        LazyColumn {
            items(activities) { activity ->
                MiniDisplayActivity(activity)
            }
        }
    }

    @Composable
    private fun MiniDisplayActivity(activity: ProcrastinationActivity) {
        Row {
            activity.name?.let { Text(text = it) }
            Spacer(modifier = Modifier.width(4.dp))
            activity.description?.let { Text(text = it) }
            Spacer(modifier = Modifier.width(4.dp))
            Button(onClick = { openProcrastinationActivity(activity) }) {
                Text("Select")
            }
        }
    }

    private fun openProcrastinationActivity(activity: ProcrastinationActivity) {
        val intent = Intent(this, ProcrastinationActivityActivity::class.java)
        intent.putExtra("activity", activity)
        startActivity(intent)
    }
}


