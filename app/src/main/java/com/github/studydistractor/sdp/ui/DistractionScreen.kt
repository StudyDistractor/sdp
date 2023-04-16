package com.github.studydistractor.sdp.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.studydistractor.sdp.distraction.DistractionViewModel

@Composable
fun DistractionScreen(distractionViewModel: DistractionViewModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(26.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
            Text(
                text = distractionViewModel.distraction!!.name!!,
                fontWeight = FontWeight.Thin,
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("name"),
            )


        if (activityHasIcon(distractionViewModel.distraction!!.iconName)) {
            Icon(
                painter = painterResource(
                    LocalContext.current.resources.getIdentifier(
                        distractionViewModel.distraction!!.iconName!!,
                        "drawable",
                        "com.github.studydistractor.sdp"
                    )
                ),
                tint = Color(0xFF6650a4),
                modifier = Modifier
                    .size(350.dp)
                    .testTag("icon"),
                contentDescription = distractionViewModel.distraction!!.iconName!!,
            )
        }

        Text(
            text = distractionViewModel.distraction!!.description!!,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .testTag("description")
                .padding(8.dp)
        )
        Button(onClick = {
            Toast.makeText(
                context,
                "Activity completed!",
                Toast.LENGTH_SHORT
            ).show()
        }, modifier = Modifier.testTag("completeButton")) {
            Text(text = "Activity completed!", color = Color.White)

        }
    }
}

private fun activityHasIcon(iconName: String?): Boolean {
    return iconName != null && iconName != ""
}

