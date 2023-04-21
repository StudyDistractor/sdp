package com.github.studydistractor.sdp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.github.studydistractor.sdp.procrastinationActivity.DistractionStatInterface

@Composable
fun DistractionStatScreen(uid : String, did : String, stat : DistractionStatInterface) {
    val feedback = remember {mutableStateOf("")}
    val tag = remember {mutableStateOf("")}
    Column(
    ) {
        /**
         * Like and dislike count
         */
        Row(){
            Text("Likes : ${stat.fetchLikeCount(did).value}")
            Text("Dislikes :${stat.fetchDislikeCount(did).value}")
        }
        /**
         * Like and dislike buttons
         */
        Row(
           horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = { stat.postLike(did, uid) },
                Modifier.testTag("distraction-stat-screen__like")

            ) {
                Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "Like")
            }

            IconButton(
                onClick = { stat.postDislike(did, uid) },
                Modifier.testTag("distraction-stat-screen__dislike")
            ) {
                Icon(imageVector = Icons.Default.ThumbDown, contentDescription = "Like")
            }
        }

        /**
         * Feedback text field and button
         */
        Row(){
            OutlinedTextField(
                value = feedback.value,
                onValueChange = {feedback.value = it},
                Modifier.testTag("distraction-stat-screen__feedback-text-field")

            )
            IconButton(
                onClick = {stat.postNewFeedback(did, uid, feedback.value)},
                Modifier.testTag("distraction-stat-screen__feedback-button")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add feedback",
                )
            }

        }

        /**
         * Tags text field and button
         */
        Row(){
            OutlinedTextField(
                value = tag.value,
                onValueChange = {tag.value = it},
                Modifier.testTag("distraction-stat-screen__tag-text-field")

            )

            IconButton(onClick = {},
                Modifier.testTag("distraction-stat-screen__tag-button")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add tag",
                )
            }

        }
        /**
         * List of tags
         */
        LazyColumn(){
            items(stat.fetchDistractionTags(did)) {i->
                Text(i, Modifier.testTag("distraction-stat-screen__tag-$i"))
            }
        }
        /**
         * List of feedbacks
         */
        LazyColumn(){
            items(stat.fetchDistractionFeedback(did)) {i->
                Text(i, Modifier.testTag("distraction-stat-screen__feedback-$i"))
            }
        }


    }
}