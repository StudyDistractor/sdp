package com.github.studydistractor.sdp.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.github.studydistractor.sdp.distraction.DistractionViewModel

@Composable
fun DistractionScreen(
    distractionViewModel: DistractionViewModel
) {
    fun showFailureToast(context: Context, message: String) {
        Toast.makeText(context, "Failure: $message", Toast.LENGTH_SHORT)
            .show()
    }

    val uiState by distractionViewModel.uiState.collectAsState()
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
                    distractionViewModel.handleBookmark()
                        .addOnSuccessListener { distractionViewModel.onChangedBookmark() }
                        .addOnFailureListener{
                            showFailureToast(context, it.message.orEmpty())
                            distractionViewModel.reverseBookmarked()
                        }
                },
                modifier = Modifier.padding(8.dp).testTag("distraction-screen__bookmark-button")
            ) {
                if(uiState.isBookmarked) {
                    Icon(Icons.Filled.Favorite,
                        contentDescription = "Bookmark button",
                        tint = Color.Red
                    )
                } else {
                    Icon(Icons.Filled.Favorite,
                        contentDescription = "Bookmark button",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = uiState.distraction.name.orEmpty(),
                fontWeight = FontWeight.Thin,
                fontSize = 45.sp,
                modifier = Modifier.testTag("name"),
                lineHeight = 45.sp
            )
        }

        if (activityHasIcon(uiState.distraction.iconName)) {
            Icon(
                painter = painterResource(
                    LocalContext.current.resources.getIdentifier(
                        uiState.distraction.iconName,
                        "drawable",
                        "com.github.studydistractor.sdp"
                    )
                ),
                tint = Color(0xFF6650a4),
                modifier = Modifier
                    .size(350.dp)
                    .testTag("icon"),
                contentDescription = uiState.distraction.iconName,
            )
        }
        Text(
            text = uiState.distraction.description.orEmpty(),
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