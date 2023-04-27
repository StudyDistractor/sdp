package com.github.studydistractor.sdp.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
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
import androidx.compose.ui.unit.sp
import com.github.studydistractor.sdp.R
import com.github.studydistractor.sdp.bookmark.BookmarksViewModel
import com.github.studydistractor.sdp.distraction.DistractionViewModel
import com.github.studydistractor.sdp.history.HistoryInterface
import com.google.firebase.auth.FirebaseAuth
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun DistractionScreen(
    distractionViewModel: DistractionViewModel,
    historyInterface: HistoryInterface,
    bookmarksViewModel: BookmarksViewModel
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    bookmarksViewModel.addToBookmark(
                        distractionViewModel.distraction!!,
                        {},
                        {}
                    )
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(Icons.Outlined.Favorite, contentDescription = null)
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = distractionViewModel.distraction!!.name!!,
                fontWeight = FontWeight.Thin,
                fontSize = 45.sp,
                modifier = Modifier.testTag("name"),
                lineHeight = 45.sp
            )
        }

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